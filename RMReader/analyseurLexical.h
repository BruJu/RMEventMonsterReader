#ifndef __H_ANALYSEURLEXICAL__
#define __H_ANALYSEURLEXICAL__

#include "types.h"

#define CHAINESIZE 50

typedef struct {
    Instruction instruction;
    char chaine[5*CHAINESIZE];
} InstructionsEnCoursDeLecture;

int readLine();
InstructionsEnCoursDeLecture lireLigne();
InstructionEnsemble * analyserLigne(InstructionsEnCoursDeLecture iECDL);
InstructionEnsemble * initialiserLaNouvelleLigne();


#endif