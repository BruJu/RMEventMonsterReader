#ifndef __H_LISTREADER__
#define __H_LISTREADER__

#include "grille.H"

#define     DICTIONNARY_SIZE        1000

typedef struct {
    char identifiant[50];
    char chaine[50];
} Dico;


void remplirLeDico(FILE * file, Dico * dico);
void remplacerLesOccurrences(Grille * grille, char * prefixe, Dico * dico);
void remplacerLesChaines(char * nomFichier, Grille * grille, char * prefixe);

#endif