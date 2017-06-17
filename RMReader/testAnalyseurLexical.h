#ifndef __H_TESTANALYSEURLEXICAL__
#define __H_TESTANALYSEURLEXICAL__

#include "types.h"


char * obtenirRepresentationDunSigne(Signe signe);

char * obtenirRepresentationDunBooleen(int nombre);

void printEspaces();

void afficherUnElement(InstructionEnsemble * instructionEnsemble);

void testerFichier(char * nom);

#endif