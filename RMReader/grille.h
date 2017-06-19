#ifndef __H_GRILLE__
#define __H_GRILLE__

#include "types.h"


int grille_decrypterParametres(char * ligneDeParametres, int debut, int * taille, int * fixe, int * groupes, int * parGroupe); 


Grille * initialiserGrille(char * ligneDeParametres, int debut);
void libererGrille(Grille * grille);

void grille_afficher(Grille * grille);
void grille_raz(Grille * grille);

int grille_chercherVariableSurveillee(Grille * grille, int variable, VS_Possibilitees type);

void grille_remplirVariable(Grille * grille, int id, int variable, int valeur);
void grille_remplirSwitch(Grille * grille, int id, int interrupteur, int valeur);
void grille_remplirChaine(Grille * grille, int id, int chaine, char * valeur);

Enregistrement * grille_getEnregistrement(Grille * grille, int id, VS_Possibilitees type, int variable);
int * grille_getEnregistrementInt(Grille * grille, int id, VS_Possibilitees type, int variable);
char * grille_getEnregistrementChar(Grille * grille, int id, VS_Possibilitees type, int variable);


#endif