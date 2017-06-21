#ifndef __H_GRAMMAIRE__
#define __H_GRAMMAIRE__

#include "types.h"

typedef enum {
    COND_INF, COND_SUP, COND_EGAL, COND_DIFF
} CondAutreType;

typedef struct _conditionAutre {
    int num;
    int val;
    CondAutreType type;
    struct _conditionAutre * s;
} ConditionAutre;

typedef enum {
    SIDG_Geree = 0,
    SIDG_Ignoree = 1,
    SIDG_Erreur = -1
} StatutInstrDansGrammaire;


typedef union {
    struct {
        Signe signe;
        int valeur;
    } arith;
    char texte[SIZEMAXSTOREDTEXT];
} Modification;

void inverser_condautre(ConditionAutre * conditionAutre);

#endif