#include "grille.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

static inline int grille_outOfBound(Grille * grille, int id) {
    return id < 0 || id >= grille->identifiantMax;
}

int grille_decrypterParametres(char * ligneDeParametres, int debut, int * taille, int * fixe, int * groupes, int * parGroupe) { //300:2+3x11
    int k = sscanf(ligneDeParametres + debut, "%d:%d+%dx%d", taille, fixe, groupes, parGroupe);
    return k != 4;
}

void grille_raz(Grille * grille) {
    int nbDeCases;
    
    nbDeCases = grille->identifiantMax * grille->nbDeChamps;
    
    for (int i = 0 ; i != nbDeCases ; i++) {
        grille->enregistrements[i].val = 0;
        
        if (grille->enregistrements[i].txt[0] != 0) {
            fprintf(stderr, "bah non ça marche pas\n");
            exit(0);
        }
    }
}

Grille * initialiserGrille(char * ligneDeParametres, int debut) {
    Grille * grille;
    grille = malloc(sizeof(Grille));
    if (grille == NULL)
        return NULL;
    
    if (grille_decrypterParametres(ligneDeParametres, debut,
                            &(grille->identifiantMax),
                            &(grille->debutSousEnsembles),
                            &(grille->nbDeSousEnsembles),
                            &(grille->nbDElementsParSousEnsembles))) {
        free(grille);
        return NULL;
    }
    
    grille->nbDeChamps = grille->debutSousEnsembles + grille->nbDeSousEnsembles * grille->nbDElementsParSousEnsembles;
    
    grille->variables = malloc(sizeof(VariableSurveillee) * grille->nbDeChamps);
    
    if (grille->variables == NULL) {
        free(grille);
        return NULL;
    }
    
    grille->enregistrements = malloc(sizeof(Enregistrement) * grille->nbDeChamps * grille->identifiantMax);
    if (grille->enregistrements == NULL) {
        free(grille->enregistrements);
        return NULL;
    }
    
    return grille;
}

void libererGrille(Grille * grille) {
    free(grille->variables);
    free(grille->enregistrements);
    free(grille);
}


int grille_chercherVariableSurveillee(Grille * grille, int variable, VS_Possibilitees type) {
    int i = 0;
    
    while (i != grille->nbDeChamps) {
        if (grille->variables[i].type == type && grille->variables[i].position == variable)
            return i;
        
        i++;
    }
    
    return -1;
}

void grille_remplirVariable(Grille * grille, int id, int variable, int valeur) {
    if (grille_outOfBound(grille, id))
        return;
    
    int s = grille_chercherVariableSurveillee(grille, variable, VS_VARIABLE);
    
    if (s == -1)
        return;
    
    grille->enregistrements[id * grille->nbDeChamps + s].val = valeur;
}


void grille_remplirSwitch(Grille * grille, int id, int interrupteur, int valeur) {
    if (grille_outOfBound(grille, id))
        return;
    
    int s = grille_chercherVariableSurveillee(grille, interrupteur, VS_SWITCH);
    
    if (s == -1)
        return;
    
    grille->enregistrements[id * grille->nbDeChamps + s].val = valeur;
}

void grille_remplirChaine(Grille * grille, int id, int chaine, char * valeur) {
    if (grille_outOfBound(grille, id))
        return;
    
    int s = grille_chercherVariableSurveillee(grille, chaine, VS_CHAINE);
    
    if (s == -1)
        return;
    
    strncpy(grille->enregistrements[id * grille->nbDeChamps + s].txt, valeur, SIZEMAXSTOREDTEXT-1);
    grille->enregistrements[id * grille->nbDeChamps + s].txt[SIZEMAXSTOREDTEXT] = 0;
}

Enregistrement * grille_getEnregistrement(Grille * grille, int id, VS_Possibilitees type, int variable) {
    if (grille_outOfBound(grille, id))
        return NULL;
    
    int s = grille_chercherVariableSurveillee(grille, variable, type);
    
    if (s == -1)
        return NULL;
    
    return &(grille->enregistrements[id * grille->nbDeChamps + s]);
}

int * grille_getEnregistrementInt(Grille * grille, int id, VS_Possibilitees type, int variable) {
    Enregistrement * enregistrement = grille_getEnregistrement(grille, id, type, variable);
    
    if (enregistrement == NULL)
        return NULL;
    
    return &(enregistrement->val);
}

char * grille_getEnregistrementChar(Grille * grille, int id, VS_Possibilitees type, int variable) {
    Enregistrement * enregistrement = grille_getEnregistrement(grille, id, type, variable);
    
    if (enregistrement == NULL)
        return NULL;
    
    return enregistrement->txt;
}


void grille_displayEnregistrement(Grille * grille, int ligne, int colonne) {
    switch (grille->variables[colonne].type) {
        case VS_CHAINE:
            printf("%s,", grille->enregistrements[ligne * grille->nbDeChamps + colonne].txt);
            break;
        case VS_SWITCH:
            if (grille->enregistrements[ligne * grille->nbDeChamps + colonne].val) {
                printf("ON,");
            } else {
                printf("OFF,");
            }
            break;
        case VS_VARIABLE:
            printf("%d,", grille->enregistrements[ligne * grille->nbDeChamps + colonne].val);
            break;
    }
}


void grille_afficher(Grille * grille) {
    int ligne;
    int colonne, colonneMax;
    
    for (ligne = 0 ; ligne != grille->identifiantMax ; ligne++) {
        if (grille->enregistrements[ligne * grille->nbDeChamps].val == 0) {
            // Ne pas afficher les lignes en double
            continue;
        }
        
        // Afficher les éléments n'étant pas dans un sous groupe
        for (int colonne = 0 ; colonne != grille->debutSousEnsembles ; colonne ++) {
            grille_displayEnregistrement(grille, ligne, colonne);
        }
        printf("\n");
        
        for (int sousGroupe = 0 ; sousGroupe != grille->nbDeSousEnsembles ; sousGroupe++) {
            colonne =  grille->debutSousEnsembles + sousGroupe * grille->nbDElementsParSousEnsembles;
            if (grille->enregistrements[ligne * grille->nbDeChamps + colonne].val == 0)
                continue;
            
            colonneMax = colonne + grille->nbDElementsParSousEnsembles;
            
            printf(">");
            for (; colonne != colonneMax ; colonne ++) {
                grille_displayEnregistrement(grille, ligne, colonne);
            }
            printf("\n");
        }
    }
}

