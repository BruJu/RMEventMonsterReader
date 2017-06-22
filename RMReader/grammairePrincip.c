#include "grammairePrincip.h"
#include "analyseurLexical.h"
#include "grille.h"
#include "testAnalyseurLexical.h"
#include <stdlib.h>
#include <string.h>
#include "configReader.h"

extern FILE * filedescriptor;

Grille * grid;
InstructionEnsemble * instr = NULL;
ConditionWorker conditionWorker;


/**
 * Echange a et b
 */
void echanger(int * a, int * b) {
    int temp = *a;    *a = *b;    *b = temp;
}

/**
 * Renvoie l'enregistrement après actualLine qui respecte toutes les conditions mises en place.
 * 
 * Renvoie -1 si il n'y a plus de ligne
 */
int getNextLine(int actualLine) {
    if (actualLine < conditionWorker.minID) {
        actualLine = conditionWorker.minID;
    } else {
        actualLine ++;
    }
    
    ConditionID * condId;
    ConditionAutre * condAu;
    
    do {
        condId = conditionWorker.valeursInterdites;
        
        while (condId != NULL) {
            if (actualLine == condId->v) {
                break;
            }
            
            condId = condId->s;
        }
        
        if (condId != NULL) {
noGood:
            actualLine ++;
            continue;
        }
        
        
        condAu = conditionWorker.autresConditions;
        
        int valeurCible;
        
        while (condAu != NULL) {
            valeurCible = grid->enregistrements[actualLine * grid->nbDeChamps + condAu->num].val;
            
            switch(condAu->type) {
                case COND_DIFF:
                    if (valeurCible == condAu->val) {
                        goto noGood;
                    }
                    break;
                case COND_EGAL:
                    if (valeurCible != condAu->val) {
                        goto noGood;
                    }
                    break;
                case COND_INF:
                    if (valeurCible > condAu->val) {
                        goto noGood;
                    }
                    break;
                case COND_SUP:
                    if (valeurCible < condAu->val) {
                        goto noGood;
                    }
                    break;
            }
            
            condAu = condAu->s;
        }
        
        
    } while (condId != NULL && condAu != NULL);
    
    if (actualLine >= conditionWorker.maxID )
        return -1;
    else
        return actualLine;
}



/**
 * Applique le changement d'interrupteur à tous les enregistrements concernables
 */
void remplirTableur_ChgSwitch() {
    int newLine = -1;
    int colonne;
    
    colonne = grille_chercherVariableSurveillee(grid, instr->complement.affectation.numero, VS_SWITCH);

    if (colonne == -1)  // Variable non surveillée
        return;
    
    int * enreg;
    
    while ((newLine = getNextLine(newLine)) != -1) {
        grid->enregistrements[newLine * grid->nbDeChamps].val = newLine;
        
        enreg = &(grid->enregistrements[newLine * grid->nbDeChamps + colonne].val);
        
        switch (instr->complement.affectation.signe) {
            case Egal:
                *enreg = instr->complement.affectation.nouvelleValeur;
                break;
            case Different:
                *enreg = 1-instr->complement.affectation.nouvelleValeur;
                break;
            default:
                fprintf(stderr, "Incoherent operator a %d\n", instr->complement.affectation.signe);
                exit(0);
                break;
        }
    }
}

/**
 * Applique le changement de variables à tous les enregistrements concernables
 */
void remplirTableur_ChgVariable() {
    int newLine = -1;
    int colonne;
    
    colonne = grille_chercherVariableSurveillee(grid, instr->complement.affectation.numero, VS_VARIABLE);

    if (colonne == -1)  // Variable non surveillée
        return;
    
    int * enreg;
    
    while ((newLine = getNextLine(newLine)) != -1) {
        enreg = &(grid->enregistrements[newLine * grid->nbDeChamps + colonne].val);
        
        switch (instr->complement.affectation.signe) {
            case Egal:
                grid->enregistrements[newLine * grid->nbDeChamps].val = newLine;
                *enreg = instr->complement.affectation.nouvelleValeur;
                break;
            case Plus:
                *enreg += instr->complement.affectation.nouvelleValeur;
                break;
            case Moins:
                *enreg -= instr->complement.affectation.nouvelleValeur;
                break;
            case Fois:
                *enreg *= instr->complement.affectation.nouvelleValeur;
                break;
            case Divise:
                *enreg /= instr->complement.affectation.nouvelleValeur;
                break;
            case Modulo:
                *enreg %= instr->complement.affectation.nouvelleValeur;
                break;
            default:
                fprintf(stderr, "Incoherent operator b\n");
                exit (0);
                break;
        }
    }
}






/**
 * Lit une nouvelle instruction dans le fichier.
 * 
 * Renvoie -1 si l'instruction n'est pas gérable dans la grammaire
 * Renvoie 0 si ok
 */
int avancer() {
    int k;
    
    do {
        if (instr != NULL)
            free(instr);
        
        instr = initialiserLaNouvelleLigne();
        k = remplirTableur_instructionsNonGerees();
    } while (k == 1);
    
    return !k;
}

/**
 * Initialise la variable globale qui permet de gérer les conditions
 */
void conditionWorkerInit() {
    conditionWorker.autresConditions = NULL;
    conditionWorker.valeursInterdites = NULL;
    conditionWorker.minID = 0;
    conditionWorker.maxID = grid->identifiantMax - 1;
}

/**
 * Retire la gestion de la condition
 */
void retirerCondForkee(ConditionForkee * condFork) {
    switch (condFork->type) {
        case CONDFORKEE_MIN:
            if (conditionWorker.minID > condFork->u.valeur) {
                echanger(&(conditionWorker.minID), &(condFork->u.valeur));
            }
            break;
        case CONDFORKEE_MAX:
            if (conditionWorker.maxID < condFork->u.valeur) {
                echanger(&(conditionWorker.maxID), &(condFork->u.valeur));
            }
            break;
        case CONDFORKEE_IMPOSER:
            echanger(&(conditionWorker.minID), &(condFork->u.valeurs.oldmin));
            echanger(&(conditionWorker.maxID), &(condFork->u.valeurs.oldmax));
            break;
        case CONDFORKEE_IDINTERDIT:
            {
                // assert (conditionID == &(condFork->u.valeursInterdites))
                ConditionID * conditionID = conditionWorker.valeursInterdites;
                conditionWorker.valeursInterdites = conditionID->s;
            }
            break;
        case CONDFORKEE_AUTRE:
            {
                // assert(conditionAutre == &(condFork->u.autresConditions))
                ConditionAutre * conditionAutre = conditionWorker.autresConditions;
                conditionWorker.autresConditions = conditionAutre->s;
                
            }
            break;
        default:
            fprintf(stderr, "Retirer un type de condition impossible\n");
            exit(1);
    }
    
}

/**
 * Ajoute la gestion de la condition
 */
int ajouterCondForkee(ConditionForkee * condFork) {
    switch (condFork->type) {
        case CONDFORKEE_MIN:
            if (conditionWorker.minID < condFork->u.valeur) {
                echanger(&(conditionWorker.minID), &(condFork->u.valeur));
            }
            break;
        case CONDFORKEE_MAX:
            if (conditionWorker.maxID > condFork->u.valeur) {
                echanger(&(conditionWorker.maxID), &(condFork->u.valeur));
            }
            break;
        case CONDFORKEE_IMPOSER:
            if (conditionWorker.minID <= condFork->u.valeurs.oldmin && conditionWorker.maxID >= condFork->u.valeurs.oldmax) {
                echanger(&(conditionWorker.minID), &(condFork->u.valeurs.oldmin));
                echanger(&(conditionWorker.maxID), &(condFork->u.valeurs.oldmax));
            } else {
                return 1;
            }
            break;
        case CONDFORKEE_IDINTERDIT:
            condFork->u.valeursInterdites.s = conditionWorker.valeursInterdites;
            conditionWorker.valeursInterdites = &(condFork->u.valeursInterdites);
            break;
        case CONDFORKEE_AUTRE:
            condFork->u.autresConditions.s = conditionWorker.autresConditions;
            conditionWorker.autresConditions = &(condFork->u.autresConditions);
            
            break;
        default:
            fprintf(stderr, "ERRTAB004 //");
            exit(0);
    }
    
    return 0;
}

/**
 * Inverse la condition
 */
void inverserCondForkee(ConditionForkee * condFork) {
    switch (condFork->type) {
        case CONDFORKEE_MIN:
            condFork->type = CONDFORKEE_MAX;
            condFork->u.valeur -= 1;
            break;
        case CONDFORKEE_MAX:
            condFork->type = CONDFORKEE_MIN;
            condFork->u.valeur += 1;
            break;
        case CONDFORKEE_IMPOSER:
            condFork->type = CONDFORKEE_IDINTERDIT;
            condFork->u.valeursInterdites.v = condFork->u.valeur;
            break;
        case CONDFORKEE_IDINTERDIT:
            condFork->type = CONDFORKEE_IMPOSER;
            condFork->u.valeur = condFork->u.valeursInterdites.v;
            break;
        case CONDFORKEE_AUTRE:
            inverser_condautre(&(condFork->u.autresConditions));
            break;
        default:
            fprintf(stderr, "ERRTAB005");
            break;
    }
}

/**
 * Crée une structure ConditionForkée qui va gérer une nouvelle condition
 */
ConditionForkee * ajouterCondition(VS_Possibilitees type, Signe signe, int valeur, int numVariable) {
    int positionCol;
    
    // Rechercher le type
    for (positionCol = 0 ; positionCol != grid->nbDeChamps ; positionCol++) {
        if (grid->variables[positionCol].type == type
            && grid->variables[positionCol].position == numVariable)
        break;
    }
    
    if (positionCol == grid->nbDeChamps)
        return NULL;
    
    // construire l'objet
    ConditionForkee * condFork = malloc(sizeof(ConditionForkee));
    if (condFork == NULL) {
        perror("malloc condFork");
        return NULL;
    }
    
    switch (type) {
        case VS_CHAINE:
            fprintf(stderr, "Comportement incohérent ou non géré (fork sur une chaine)\n");
            exit(1);
        case VS_SWITCH:
            {
                condFork->type = CONDFORKEE_IMPOSER;
                condFork->u.autresConditions.num = positionCol;
                condFork->u.autresConditions.type = COND_EGAL;
                condFork->u.autresConditions.val = valeur;
            }
            break;
        case VS_VARIABLE:
        {
            if (signe == InfEgal)   { valeur ++;     signe = Inferieur; }
            if (signe == SupEgal)   { valeur --;     signe = Superieur; }
            
            if (positionCol == 0) {
                if (signe == Inferieur) {
                    condFork->type = CONDFORKEE_MAX;
                    condFork->u.valeur = valeur;
                } else if (signe == Superieur) {
                    condFork->type = CONDFORKEE_MIN;
                    condFork->u.valeur = valeur;
                } else if (signe == Egal) {
                    condFork->type = CONDFORKEE_IMPOSER;
                    condFork->u.valeurs.oldmin = valeur;
                    condFork->u.valeurs.oldmax = valeur+1;
                } else if (signe == Different) {
                    condFork->type = CONDFORKEE_IDINTERDIT;
                    condFork->u.valeursInterdites.v = valeur;
                } else {
                    fprintf(stderr, "ERRTAB002 signe inconnu");
                    return NULL;
                }
            } else {
                condFork->type = CONDFORKEE_AUTRE;
                condFork->u.autresConditions.num = positionCol;
                    
                if (signe == Inferieur) {
                    condFork->u.autresConditions.type = COND_INF;
                } else if (signe == Superieur) {
                    condFork->u.autresConditions.type = COND_SUP;
                } else if (signe == Egal) {
                    condFork->u.autresConditions.type = COND_EGAL;
                } else if (signe == Different) {
                    condFork->u.autresConditions.type = COND_DIFF;
                } else {
                    fprintf(stderr, "ERRTAB003 signe inconnu");
                    return NULL;
                }
                
                condFork->u.autresConditions.val = valeur;
            }
        }
        break;
        default:
            fprintf(stderr, "ERRTAB001 type inconnu");
            return NULL;
    }
    
    // l'ajouter
    return condFork;
}

/**
 * Rempli la grille avec le nom du fichier donnée
 */
int remplirTableur_init(Grille * grille, char * nomDuFichier) {
    int k;
    
    filedescriptor = fopen(nomDuFichier, "r");
    if (filedescriptor == NULL) {
        perror("fopen remplirTableur_init");
        fprintf(stderr, "Fichier %s inexistant\n", nomDuFichier);
        return -1;
    }
    
    grid = grille;
    
    conditionWorkerInit();
    
    
    
    if (!avancer()) {
        k = 0;
    } else {
        k = remplirTableur_S();
    }
    
    fclose(filedescriptor);
    return k;
}



int remplirTableur_S() {
    if (instr == NULL
        || instr->instruction == ForkElse
        || instr->instruction == ForkEnd)
        return 0;
    
    if (remplirTableur_Instruction()) {
        fprintf(stderr, "S -> Instruction S renvoie 0\n");
        return 1;
    }
    
    return remplirTableur_S();
}

int remplirTableur_Instruction() {
    // Instructions impossibles : ForkElse et ForkEnd
    if (instr == NULL) {
        return 0;
    }
    
    if (instr->instruction == ForkIf) {
        // ForkIf
        return remplirTableur_Condition();
    } else {
        // ChgSwitch, ChgVariable, ShowPicture, ChangeItem
        switch (instr->instruction) {
            case ChgSwitch:
                remplirTableur_ChgSwitch();
                break;
            case ChgVariable:
                remplirTableur_ChgVariable();
                break;
            case ShowPicture:
            case ChangeItem:
                // Instructions sans action dans cette grammaire.
                break;
            case Ignore:
            case Void:
                return 0;
                break;
            default:
                fprintf(stderr, "ERR006 %d\n", instr->instruction);
                break;
        }
        
        return !avancer();
    }
}

int remplirTableur_Condition() {
    if (instr == NULL || instr->instruction != ForkIf) {
        return 1;
    }
    
    ConditionForkee * forkedCondition;

    if (instr->complement.forkIf.type == 0) {
        // Switch
        forkedCondition = ajouterCondition(VS_SWITCH,
                                           instr->complement.forkIf.comparatif,
                                           instr->complement.forkIf.valeur,
                                           instr->complement.forkIf.numero);
    } else {
        // Variable
        
        if (instr->complement.forkIf.pointeur) {
            fprintf(stderr, "Condition avec pointeur lue\n");
            return 1;
        }
        
        forkedCondition = ajouterCondition(VS_VARIABLE,
                                           instr->complement.forkIf.comparatif,
                                           instr->complement.forkIf.valeur,
                                           instr->complement.forkIf.numero);
    }
    // l'ajouter
    if (forkedCondition != NULL && !ajouterCondForkee(forkedCondition)) {
        if (!avancer())
            return 1;
        
        remplirTableur_S();
    } else {
        if (forkedCondition != NULL) {
            free (forkedCondition);
            forkedCondition = NULL;
        }
        
        // Squeezer tout le contenu
        int imbrications = 1;
        
        do {
            if (!avancer())
                return 1;
            
            switch (instr->instruction) {
                case ForkIf:
                    imbrications++;
                    break;
                case ForkElse:
                    break;
                case ForkEnd:
                    imbrications--;
                    break;
                default:
                    break;
            }
            
        } while(imbrications != 0);
    }
    
    remplirTableur_ConditionPrime(forkedCondition);
    
    return 0;
}

int remplirTableur_ConditionPrime(ConditionForkee * conditionForkee) {
    if (instr == NULL)
        return 1;

    
    if (instr->instruction == ForkElse) {
        retirerCondForkee(conditionForkee);
        inverserCondForkee(conditionForkee);
        ajouterCondForkee(conditionForkee);
        
        if (!avancer())
            return 1;
            
        remplirTableur_S();
    }
    
    if (instr->instruction == ForkEnd) {
        if (conditionForkee != NULL) {
            retirerCondForkee(conditionForkee);
            free(conditionForkee);
        }
        
        // enlever la condition forkée
        return !avancer();
    }
    
    return 1;
}

/**
 * Renvoie 0 si on est sur une instruction null ou gérée
 * Renvoie 1 si on est sur une instruction inutile
 * Renvoie -1 si on est sur une instruction non gérée par cette grammaire
 */
int remplirTableur_instructionsNonGerees() {
    if (instr == NULL)
        return 0;
    
    switch (instr->instruction) {
        case Label:
        case JumpToLabel:
        case Loop:
        case LoopEnd:
        case LoopBreak:
        case Instr_Erreur:
            return -1;
        case Ignore:
        case Void:
            return 1;
        default:
            return 0;
    }
}



/*
int remplirTableur_preparerGrille(BufferizedFile * file, Grille * grille) {
    int param1, param2;
    
    
    ASSERT (BufferizedFile_nextString(file));
    
    if (sscanf(file->buffer, "%d %d", &param1, &param2) != 2)
        return 1;
    
    
    for (int i = 0 ; i != param1 ; i++) {
        if (BufferizedFile_nextString(file))
            return 1;
        
        if(sscanf(file->buffer,
                  "%d = %d",
                  &(grille->variableGrp[i].colonne),
                  &(grille->variableGrp[i].numeroVariable)) != 2) {
            return 1;
        }
    }
    
    grille->variableGrp[param1].numeroVariable = -1;
    
    
    if (param2 == 0) {
        grille->caseObjet = 0;
        grille->casePicture = 0;
    } else if (param2 == 1) {
        if (BufferizedFile_nextString(file))
            return 1;
            
        sscanf(file->buffer, "%d", &(grille->casePicture));
        grille->caseObjet = 0;
    } else if (param2 == 2) {
        if (BufferizedFile_nextString(file))
            return 1;
        
        sscanf(file->buffer, "%d", &(grille->caseObjet));
        grille->casePicture = 0;
    } else {
        if (BufferizedFile_nextString(file))
            return 1;

        sscanf(file->buffer, "%d %d", &(grille->casePicture), &(grille->caseObjet));
    }
    
    return 0;
}
*/