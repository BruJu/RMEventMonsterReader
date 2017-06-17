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