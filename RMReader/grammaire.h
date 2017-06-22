#ifndef __H_GRAMMAIRE__
#define __H_GRAMMAIRE__

#include "types.h"

/**
 * Types d'opérateurs possibles pour les conditions sur les variables
 */
typedef enum {
    COND_INF, COND_SUP, COND_EGAL, COND_DIFF
} CondAutreType;

/**
 * Structure permettant de garder les informations d'une condition sur
 * une variable quelconque.
 * 
 * Fonctionne sur le principe d'un maillon dans une liste chainée
 */
typedef struct _conditionAutre {
    int num;
    int val;
    CondAutreType type;
    struct _conditionAutre * s;
} ConditionAutre;

/**
 * Réponse d'une fonction qui permet de savoir si une instruction est gérée
 * ou non par la grammaire.
 */
typedef enum {
    SIDG_Geree = 0,
    SIDG_Ignoree = 1,
    SIDG_Erreur = -1
} StatutInstrDansGrammaire;

/**
 * Sauvegarde breve des informations à modifier dans un enregistrement
 */
typedef union {
    struct {
        Signe signe;
        int valeur;
    } arith;
    char texte[SIZEMAXSTOREDTEXT];
} Modification;

void inverser_condautre(ConditionAutre * conditionAutre);

#endif