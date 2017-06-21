#ifndef __H_TABLEUR__
#define __H_TABLEUR__

#include "types.h"
#include "grammaire.h"

typedef struct _conditionID {
    int v;
    struct _conditionID * s;
} ConditionID;


typedef struct _conditionChaine {
    int num;
    char * chaine;
    struct _conditionChaine * s;
} ConditionChaine;


typedef struct {
    int minID;
    int maxID;
    ConditionID     * valeursInterdites;
    ConditionAutre  * autresConditions;
} ConditionWorker;


typedef struct {
    enum {
        CONDFORKEE_MIN,
        CONDFORKEE_MAX,
        CONDFORKEE_IMPOSER,
        CONDFORKEE_IDINTERDIT,
        CONDFORKEE_AUTRE,
        CONDFORKEE_CHAINE,
    } type;
    
    union {
        int valeur;     // MIN / MAX
        struct {
            int oldmin;
            int oldmax;
        } valeurs;         // IMPOSER
        ConditionID     valeursInterdites;
        ConditionAutre  autresConditions;
    } u;
} ConditionForkee;


int remplirTableur_init(Grille * grille, char * nomDuFichier);

//int remplirTableur_preparerGrille(BufferizedFile * file, Grille * grille);

/*
 * Grammaire :
 * S -> Instruction S       // Tout traiter par là sauf les suivants
 * S -> Epsilon             // Suivants : ForkEnd, ForkElse, fin de progrmame
 * Instruction -> Condition     // 1er = ForkIf
 * Instruction -> Affectation
 * Condition -> ForkIf S ForkElse S ForkEnd     } Condition  -> ForkIf S Condition'
 * Condition -> ForkIf S ForkEnd                } Condition' -> ForkElse S ForkEnd
 *                                              } Condition' -> ForkEnd
 */

int remplirTableur_S();
int remplirTableur_Instruction();
int remplirTableur_Condition();
int remplirTableur_ConditionPrime(ConditionForkee * conditionForkee);

int remplirTableur_instructionsNonGerees();



#endif