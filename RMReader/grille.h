#ifndef __H_GRILLE__
#define __H_GRILLE__

#include "types.h"

/**
 * Converti une chaîne du type "300:2+3x11" pour mettre les valeurs dans les variables taille, fixe, groupes et parGroupe.
 * La chaîne est prise dans ligneDeParametres à partir du debut-ieme caractère.
 * 
 * Le premier nombre correspond à l'id maximal des entrées.
 * Le second au nombre d'attributs (en comptant l'identifiant) de chaque entrées.
 * groupes désigne le nombre de groupes.
 * parGroupes désigne le nombre de variables dans chaque groupe
 * 
 * Exemple pour une course de chevaux où chaque cheval à un numéro un nom et une couleur, il y a 5 chevaux par courses et la course a un id et un nom.
 * Le numéro maximum des courses est 50 : 50:2+5x3
 * 
 * Renvoie 0 ssi réussite.
 */
int grille_decrypterParametres(char * ligneDeParametres, int debut, int * taille, int * fixe, int * groupes, int * parGroupe); 

/**
 * Renvoie une structure Grille ayant la bonne taille pour acceuillir les variables qui seront lues et enregistrées.
 *
 * Pour ligneDeParametres, cf grille_decrypterParametres.
 */
Grille * initialiserGrille(char * ligneDeParametres, int debut);

/**
 * Libère la mémoire allouée à la grille
 */
void libererGrille(Grille * grille);

/**
 * Renvoie le numéro de la variable surveillée de la grille qui est à la position mémoire variable et de type type.
 * Renvoie -1 si inexistant
 */
int grille_chercherVariableSurveillee(Grille * grille, int variable, VS_Possibilitees type);


void grille_remplirVariable(Grille * grille, int id, int variable, int valeur);
void grille_remplirSwitch(Grille * grille, int id, int interrupteur, int valeur);
void grille_remplirChaine(Grille * grille, int id, int chaine, char * valeur);


#endif