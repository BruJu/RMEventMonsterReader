#include "tableur.h"
#include "analyseurLexical.h"
#include <stdlib.h>
#include <string.h>

extern FILE * filedescriptor;

Grille * grid;

InstructionEnsemble * instr = NULL;
ConditionWorker conditionWorker;

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

void conditionWorkerInit() {
    conditionWorker.autresConditions = NULL;
    conditionWorker.conditionsChaines = NULL;
    conditionWorker.valeursInterdites = NULL;
    conditionWorker.minID = 0;
    conditionWorker.maxID = grid->identifiantMax - 1;
}

void retirerCondForkee(ConditionForkee * condFork) {
    switch (condFork->type) {
        case CONDFORKEE_MIN:
            conditionWorker.maxID = condFork->u.valeur;
        case CONDFORKEE_MAX:
            conditionWorker.minID = condFork->u.valeur;
            break;
        case CONDFORKEE_IMPOSER:
            conditionWorker.minID = condFork->u.valeurs.oldmin;
            conditionWorker.maxID = condFork->u.valeurs.oldmax;
            break;
        case CONDFORKEE_IDINTERDIT:
            {
                ConditionID * conditionID = conditionWorker.valeursInterdites;
                
                if (conditionID == &(condFork->u.valeursInterdites)) {
                    conditionWorker.valeursInterdites = conditionID->s;
                } else {
                    while (conditionID->s != &(condFork->u.valeursInterdites)) {
                        conditionID = conditionID->s;
                    }
                    
                    // TODO
                }
            }
            break;
        case CONDFORKEE_AUTRE:
            
            break;
        case CONDFORKEE_CHAINE:
            
            break;
        default:
            return;
    }
    
}

void ajouterCondForkee(ConditionForkee * condFork) {
    (void) condFork;
    
}


ConditionForkee * ajouterCondition(VS_Possibilitees type, Signe signe, int valeur, char * chaine, int numVariable) {
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
            {
                condFork->type = CONDFORKEE_CHAINE;
                condFork->u.conditionsChaines.num = positionCol;
                strcpy(condFork->u.conditionsChaines.chaine, chaine);
            }
            break;
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
                    condFork->u.valeur = conditionWorker.maxID;
                        
                    if (conditionWorker.maxID >= valeur) {
                        conditionWorker.maxID = valeur-1;
                    }
                } else if (signe == Superieur) {
                    condFork->type = CONDFORKEE_MAX;
                    condFork->u.valeur = conditionWorker.minID;
                        
                    if (conditionWorker.minID < valeur) {
                        conditionWorker.minID = valeur;
                    }
                } else if (signe == Egal) {
                    condFork->type = CONDFORKEE_IMPOSER;
                    condFork->u.valeurs.oldmin = conditionWorker.minID;
                    condFork->u.valeurs.oldmax = conditionWorker.minID;
                    
                    conditionWorker.minID = valeur;
                    conditionWorker.maxID = valeur;
                } else if (signe == Different) {
                    condFork->type = CONDFORKEE_IDINTERDIT;
                    condFork->u.valeursInterdites.v = valeur;
                } else {
                    fprintf(stderr, "ERRTAB002 signe inconnu");
                    return NULL;
                }
            } else {
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
    ajouterCondForkee(condFork);
    return condFork;
}


int remplirTableur_init(Grille * grille, char * nomDuFichier) {
    int k;
    
    filedescriptor = fopen(nomDuFichier, "r");
    if (filedescriptor == NULL) {
        fprintf(stderr, "Fichier %s inexistant\n", nomDuFichier);
        return -1;
    }
    
    conditionWorkerInit();
    
    grid = grille;
    
    
    if (!avancer()) {
        k = 0;
    } else {
        k = remplirTableur_S();
    }
    
    fclose(filedescriptor);
    return k;
}

/*
 *     ChgSwitch, ChgVariable, ShowPicture, ChangeItem
    ChgVariable,
    ForkIf,
    ForkElse,
    ForkEnd,
    ShowPicture,
    ChangeItem,
     * 
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
     * */

int remplirTableur_S() {
    if (instr == NULL
        || instr->instruction == ForkElse
        || instr->instruction == ForkEnd)
        return 0;
    
    if (remplirTableur_Instruction())
        return 1;
    
    return remplirTableur_S();
}


int remplirTableur_Instruction() {
    // Instructions impossibles : ForkElse et ForkEnd
    
    if (instr->instruction == ForkIf) {
        // ForkIf
        return remplirTableur_Condition();
    } else {
        // ChgSwitch, ChgVariable, ShowPicture, ChangeItem
        fprintf(stderr, "Not implemented\n");

        return !avancer();
    }
    
    return 0;
}



int remplirTableur_Condition() {
    if (instr == NULL || instr->instruction != ForkIf) {
        return 1;
    }
    
    // créer la condition forkée
    ConditionForkee * forkedCondition = NULL;
    
    // l'ajouter
    remplirTableur_S();
    remplirTableur_ConditionPrime(forkedCondition);
    
    return 0;
}

int remplirTableur_ConditionPrime(ConditionForkee * conditionForkee) {
    if (instr == NULL)
        return 1;
    
    (void) conditionForkee;
    
    if (instr->instruction == ForkElse) {
        // inverser la condition forkée
        
        remplirTableur_S();
        
        avancer();
    }
    
    if (instr->instruction == ForkEnd) {
        // enlever la condition forkée
        return !avancer();
    }
    
    return 1;
}

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

