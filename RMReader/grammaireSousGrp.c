#include "grammaireSousGrp.h"
#include "configReader.h"
#include "analyseurLexical.h"
#include "testAnalyseurLexical.h"
#include <stdlib.h>
#include <string.h>

extern FILE * filedescriptor;
extern InstructionEnsemble * instr;
ConditionAutre * cond;
extern Grille * grid;
Param_completerSsGrp complem;


int gramSG_init(BufferizedFile * file, Grille * grille) {
    instr = NULL;
    cond = NULL;
    int resultat;
    resultat = 1;
    
    grid = grille;
    
    fopen(&(file->buffer[1]), "r");
    if (filedescriptor == NULL) {
        perror("file open SG");
        goto fichierouvert;
    }
    
    if(BufferizedFile_nextString(file))
        goto fichierouvert;
    
    if (sscanf(file->buffer, "%d %d %d", &(complem.nbRedir), &(complem.colonnePicture), &(complem.colonneObjets) ) != 3)
        goto fichierouvert;
    
    if (complem.nbRedir != 0) {
        complem.redir = malloc(sizeof(RedirectionsSurGroupe) * complem.nbRedir);
        if (complem.redir == NULL) {
            goto fichierouvert;
        }
    }
    
    
    for (int i = 0 ; i != complem.nbRedir ; i++) {
        if (sscanf(file->buffer, "%d %s %d", &(complem.redir[i].colonne), complem.redir[i].prefixe, &(complem.redir[i].variable)) != 3) {
            goto desallocredir;
        }
        
        if(BufferizedFile_nextString(file))
            goto desallocredir;
    }
    
    if (gramSG_avancer()) {
        goto desallocredir;
    }
    
    resultat = gramSG_S();
    
desallocredir:
    
    if (complem.nbRedir != 0)
        free(complem.redir);
    
fichierouvert:
    fclose(filedescriptor);
    return resultat;
}






int gramSG_S() {
    if (instr == NULL
        || instr->instruction == ForkElse
        || instr->instruction == ForkEnd)
        return 0;
    
    if (gramSG_Condition())
        return 1;
    
    return gramSG_S();
}



int gramSG_Instruction() {
    if (instr->instruction == ForkIf) {
        return gramSG_Condition();
    } else {
        switch (instr->instruction) {
            case ShowPicture:
                gramSG_ShowPicture();
                break;
            case ChgVariable:
                gramSG_ChgVariable();
                break;
            case ChangeItem:
                fprintf(stderr, "Non implémenté\n");
                break;
            case ChgSwitch:
                fprintf(stderr, "Non géré\n");
                break;
            default:
                fprintf(stderr, "ERR006bis\n");
                break;
            
        }
        
        return gramSG_avancer();
    }
}


int gramSG_Condition() {
    if (instr == NULL || instr->instruction != ForkIf) {
        return 1;
    }
    
    int nouvelleCondition = gramSG_ConditionAjouter();
    
    if (gramSG_avancer())
        return 1;
    
    // Condition à ignorer
    if (nouvelleCondition == 0) {
        gramSG_ConditionSqueezer();
        return 0;
    }
    
    // Suite de la grammaire
    if (gramSG_S())
        return 1;
    
    return gramSG_ConditionPrime();
}


int gramSG_ConditionPrime() {
    if (instr == NULL)
        return 1;

    if (instr->instruction == ForkElse) {
        inverser_condautre(cond);
        
        if (gramSG_avancer())
            return 1;
            
        gramSG_S();
    }
    
    if (instr->instruction != ForkEnd)
        return 1;
        
    ConditionAutre * a;
    
    a = cond;
    cond = cond->s;
    free(a);
    
    
    if (gramSG_avancer())
        return 1;
    
    return 0;
}

StatutInstrDansGrammaire gramSG_etatInstruction() {
    if (instr == NULL)
        return SIDG_Geree;
    
    switch (instr->instruction) {
        case Label:
        case JumpToLabel:
        case Loop:
        case LoopEnd:
        case LoopBreak:
        case Instr_Erreur:
            return SIDG_Erreur;
        case Ignore:
        case Void:
            return SIDG_Ignoree;
        default:
            return SIDG_Geree;
    }
}

int gramSG_avancer() {
    int k;
    
    do {
        if (instr != NULL)
            free(instr);
        
        instr = initialiserLaNouvelleLigne();
        k = gramSG_etatInstruction();
        
        if (instr != NULL)
            afficherUnElement(instr);
        
    } while (k == 1);
    
    return k;
}


void gramSG_ConditionSqueezer() {
    int etage;
    
    etage = 1;
    
    
    while (etage != 0) {
        if (gramSG_avancer())
            return;
        
        if (instr->instruction == ForkIf)
            etage++;
        else if (instr->instruction == ForkEnd)
            etage--;
    }
}

void gramSG_ShowPicture() {
    if (complem.colonnePicture == -1) {
        printf("non\n");
        return;
    }
    
    printf("%d\n", complem.colonnePicture);
    
    Modification modif;
    strcpy(modif.texte, instr->complement.showPicture);
    
    gramSG_apply(complem.colonnePicture, &modif);
}


void gramSG_ChgVariable() {
    int colonne;
    Modification modif;
    int colonneChamps;
    
    for (colonne = 0 ; colonne != complem.nbRedir ; colonne++) {
        if (complem.redir[colonne].variable == instr->complement.affectation.numero) {
            goto continuer;
        }
    }
    
    return;
    
continuer:
    colonneChamps = complem.redir[colonne].colonne;
    
    if (grid->variables[colonneChamps].type == VS_CHAINE) {
        sprintf(modif.texte, "%s%d", complem.redir[colonne].prefixe, instr->complement.affectation.nouvelleValeur);
    } else {
        modif.arith.signe = instr->complement.affectation.signe;
        modif.arith.valeur = instr->complement.affectation.nouvelleValeur;
    }
    
    gramSG_apply(colonneChamps, &modif);
}

int gramSG_ConditionAjouter() {
    ConditionAutre * ca = malloc(sizeof(ConditionAutre));
    if (ca == NULL)
        return 1;
    
    
    //ca->num = instr->complement.forkIf.comparatif.numero;
    int k;
    for (k = 0 ; k != grid->debutSousEnsembles ; k++) {
        if (grid->variables[k].position == instr->complement.forkIf.valeur) {
            ca->num = k;
            break;
        }
    }
    
    if (k == grid->debutSousEnsembles) {
        for (k = 0 ; k != complem.nbRedir ; k++) {
            if (complem.redir[k].variable == instr->complement.forkIf.valeur) {
                ca->num = -1 - k;
                break;
            }
        }
        
        if (k != complem.nbRedir) {
            free(ca);
            return 1;
        }
    }
    
    ca->s = cond;
    ca->val = instr->complement.forkIf.valeur;
    cond = ca;
    
    switch(instr->complement.forkIf.comparatif) {
        case InfEgal:
            ca->val++;
        case Inferieur:
            ca->type = COND_INF;
            break;
        case SupEgal:
            ca->val--;
        case Superieur:
            ca->type = COND_SUP;
            break;
        case Different:
            ca->type = COND_DIFF;
            break;
        default:
            ca->type = COND_EGAL;
            break;
    }
    
    return 0;
}


int avancer_GramSG_avanceur(GramSG_avanceur * av) {
    ConditionAutre * ca;
    int vari;
    
recommencer:
    if (av->ligne == -1) {
        av->ligne = 0;
        av->groupe = 0;
    } else {
        av->groupe ++;
        
        if (av->groupe == grid->nbDeSousEnsembles) {
            av->ligne++;
            av->groupe = 0;
            
            if (av->ligne >= grid->identifiantMax) {
                return 0;
            }
        }
    }
    
    ca = cond;
    
    while (ca != NULL) {
        vari = ca->num;
        
        if (vari >= 0) {
            if (testerCA(grid->enregistrements[av->ligne * grid->nbDeChamps + vari].val, ca)) {
                goto recommencer;
            }
        } else {
            vari = - vari - 1;
            
            if (testerCA(grid->enregistrements[av->ligne * grid->nbDeChamps
                                                + grid->debutSousEnsembles + av->groupe * grid->nbDeSousEnsembles
                                                + vari].val, ca)) {
                goto recommencer;
            }
        }
        
        ca = ca->s;
    }
    
    return 1;
}

int testerCA (int valeur, ConditionAutre * condi) {
    switch (condi->type) {
        case COND_DIFF:
            return valeur != condi->val;
        case COND_EGAL:
            return valeur == condi->val;
        case COND_INF:
            return valeur < condi->val;
        case COND_SUP:
            return valeur > condi->val;
    }
    
    return 0;
}


void gramSG_apply(int slot, Modification * modif) {
    GramSG_avanceur avanceur;
    avanceur.ligne = -1;
    avanceur.groupe = 0;
    
    
    int surChaine;
    surChaine = grid->variables[grid->debutSousEnsembles + slot].type == VS_CHAINE;
    
    int numeroCase;
    
    while (avancer_GramSG_avanceur(&avanceur)) {
        numeroCase = avanceur.ligne * grid->nbDeChamps + grid->debutSousEnsembles + avanceur.groupe * grid->nbDElementsParSousEnsembles;
        
        if (surChaine) {
            strcpy(grid->enregistrements[numeroCase].txt, modif->texte);
        } else {
            switch (modif->arith.signe) {
                case Egal:
                    grid->enregistrements[numeroCase].val = modif->arith.valeur;
                    break;
                case Plus:
                    grid->enregistrements[numeroCase].val += modif->arith.valeur;
                    break;
                case Moins:
                    grid->enregistrements[numeroCase].val -= modif->arith.valeur;
                    break;
                case Fois:
                    grid->enregistrements[numeroCase].val *= modif->arith.valeur;
                    break;
                case Divise:
                    grid->enregistrements[numeroCase].val /= modif->arith.valeur;
                    break;
                case Modulo:
                    grid->enregistrements[numeroCase].val %= modif->arith.valeur;
                    break;
                default:
                    fprintf(stderr, "Incoherent operator ds\n");
                    exit (0);
                    break;
                
            }
        }
    }
}