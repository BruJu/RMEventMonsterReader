#include "grammaireSousGrp.h"
#include "configReader.h"
#include "analyseurLexical.h"
#include "testAnalyseurLexical.h"
#include "tools.h"
#include <stdlib.h>
#include <string.h>

extern FILE * filedescriptor;
extern InstructionEnsemble * instr;
ConditionAutre * cond;
extern Grille * grid;
Param_completerSsGrp complem;

/*
 * Cette grammaire permet d'affecter des valeurs à des sous groupes en utilisant
 * des variables générales qui ne les désigne pas directement.
 * 
 * Il faut utiliser la grammaire principale si on souhaite que ça marche sur des
 * variables désignées directement.
 */

/**
 * Fonction d'entrée pour l'analyse de la grammaire.
 * La grille passée en paramètre sera rempli en fonction du fichier
 * qui est à la ligne courante du BufferizedFile avec certains paramètres
 * 
 * Paramètres dans le Bufferized File :
 * La première ligne est de la forme a b c
 * où a est le nombre de ligne suivant la première, b la colonne où mettre les informations des ShowPicture
 * et où c est la colonne où sont mises les informations des ChangeItem (non géré actuellement)
 * 
 * Les lignes suivantes sont de la forme d e f où 
 * d désigne le numéro de la coulonne dans le sous groupe
 * e désigne son nom
 * f désigne le numéro de variable dans le groupe
 * 
 * exemple : si on a une ligne 0 id 50
 * A chaque fois que le script mentionne la variable 50, la première variable de chaque sous groupe sera testée / affectée
 * Le nom id sera utilisé si jamais la colonne désignée est un champ texte. Dans ce cas, un Changement de Variable inscrira
 * le nom précédé du préfixe proposé ici id)
 */
int gramSG_init(BufferizedFile * file, Grille * grille) {
    instr = NULL;
    cond = NULL;
    int resultat;
    resultat = 1;
    
    grid = grille;
    
    string_retirerSauts(&file->buffer[1]);
    
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
        if(BufferizedFile_nextString(file))
            goto desallocredir;
        
        if (sscanf(file->buffer, "%d %s %d", &(complem.redir[i].colonne), complem.redir[i].prefixe, &(complem.redir[i].variable)) != 3) {
            goto desallocredir;
        }
        
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




/**
 * S -> Condition S | Instruction S | Epsilon
 */
int gramSG_S() {
    if (instr == NULL ||instr->instruction == ForkElse
        || instr->instruction == ForkEnd) {
        return 0;
    }
    
    if (instr->instruction == ForkIf) {
        if (gramSG_Condition())
            return 1;
    } else {
        if (gramSG_Instruction())
            return 1;
    }
    
    return gramSG_S();
}

/**
 * Instruction -> ShowPic | ChgVariable | ChgSwitch | ChangeItem
 * (note : la grammaire telle qu'implémentée est ambigue car elle peut engendrer Condition)
 */
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
                fprintf(stderr, "ERR006bis %d\n", instr->instruction);
                break;
        }
        
        return gramSG_avancer();
    }
}

/**
 * Condition -> ForkIf S Condition'
 */
int gramSG_Condition() {
    if (instr == NULL || instr->instruction != ForkIf) {
        return 1;
    }
    
    int nouvelleCondition = gramSG_ConditionAjouter();
    
    // Condition à ignorer
    if (nouvelleCondition) {
        gramSG_ConditionSqueezer();
        return 0;
    }
    
    // Suite de la grammaire
    if (gramSG_avancer())
        return 1;
    
    if (gramSG_S())
        return 1;
    
    return gramSG_ConditionPrime();
}

/**
 * Condition' -> (ForkElse S) ForkEnd
 */
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

/**
 * Renvoie un StatutInstrDansGrammaire correspondant à l'instruction actuelle
 */
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

/**
 * Lit l'instruction suivante
 */
int gramSG_avancer() {
    int k;
    
    do {
        if (instr != NULL)
            free(instr);
        
        instr = initialiserLaNouvelleLigne();
        k = gramSG_etatInstruction();
        
    } while (k == 1);
    
    return k;
}

/**
 * Passe tout le contenu de la condition
 */
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
    
    
    gramSG_avancer();
}

/**
 * Met le nom de l'image affichée dans les cases de la grille correspondantes
 */
void gramSG_ShowPicture() {
    if (complem.colonnePicture == -1) {
        return;
    }
    
    Modification modif;
    strcpy(modif.texte, instr->complement.showPicture);
    
    gramSG_apply(complem.colonnePicture, &modif);
}

/**
 * Rempli la grille selon l'affectation de variable de l'instruction actuelle
 */
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
    
    if (grid->variables[colonneChamps + grid->debutSousEnsembles].type == VS_CHAINE) {
        if (instr->complement.affectation.nouvelleValeur == 0)
            return;
            
        sprintf(modif.texte, "%s%d", complem.redir[colonne].prefixe, instr->complement.affectation.nouvelleValeur);
    } else {
        modif.arith.signe = instr->complement.affectation.signe;
        modif.arith.valeur = instr->complement.affectation.nouvelleValeur;
    }
    
    gramSG_apply(colonneChamps, &modif);
}

/**
 * Ajoute en tête une condition décrivant le ForkIf actuel
 */
int gramSG_ConditionAjouter() {
    ConditionAutre * ca = malloc(sizeof(ConditionAutre));
    if (ca == NULL)
        return 1;
    
    
    //ca->num = instr->complement.forkIf.comparatif.numero;
    int k;
    for (k = 0 ; k != grid->debutSousEnsembles ; k++) {
        if (grid->variables[k].position == instr->complement.forkIf.numero) {
            ca->num = k;
            break;
        }
    }
    
    if (k == grid->debutSousEnsembles) {
        for (k = 0 ; k != complem.nbRedir ; k++) {
            if (complem.redir[k].variable == instr->complement.forkIf.numero) {
                ca->num = -1 - complem.redir[k].colonne;
                break;
            }
        }
        
        if (k == complem.nbRedir) {
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

/**
 * Lit l'instruction suivante
 */
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
    
    if (grid->enregistrements[av->ligne * grid->nbDeChamps].val == 0) {
        goto recommencer;
    }
    
    if (grid->enregistrements[av->ligne * grid->nbDeChamps + grid->debutSousEnsembles + av->groupe * grid->nbDElementsParSousEnsembles].val == 0) {
        goto recommencer;
    }
    
    while (ca != NULL) {
        vari = ca->num;
        
        if (vari >= 0) {
            if (!testerCA(grid->enregistrements[av->ligne * grid->nbDeChamps + vari].val, ca)) {
                goto recommencer;
            }
        } else {
            vari = - vari - 1;
            
            if (!testerCA(grid->enregistrements[av->ligne * grid->nbDeChamps
                                                + grid->debutSousEnsembles + av->groupe * grid->nbDElementsParSousEnsembles
                                                + vari].val, ca)) {
                goto recommencer;
            }
        }
        
        ca = ca->s;
    }
    
    return 1;
}

/**
 * Renvoi vrai si la valeur vérifie la condition donnée
 */
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

/**
 * Modifie pour toutes les lignes de la grille ayant déjà des valeurs
 * la case selon la modification voulue en paramètre dans la colonne slot
 */
void gramSG_apply(int slot, Modification * modif) {
    GramSG_avanceur avanceur;
    avanceur.ligne = -1;
    avanceur.groupe = 0;
    
    
    int surChaine;
    surChaine = grid->variables[grid->debutSousEnsembles + slot].type == VS_CHAINE;
    
    int numeroCase;
    
    while (avancer_GramSG_avanceur(&avanceur)) {
        numeroCase = avanceur.ligne * grid->nbDeChamps + grid->debutSousEnsembles + avanceur.groupe * grid->nbDElementsParSousEnsembles + slot;
        
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