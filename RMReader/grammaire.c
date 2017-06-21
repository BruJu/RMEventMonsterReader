#include "grammaire.h"


void inverser_condautre(ConditionAutre * conditionAutre) {
    switch (conditionAutre->type) {
        case COND_DIFF:
            conditionAutre->type = COND_EGAL;
            break;
        case COND_EGAL:
            conditionAutre->type = COND_DIFF;
            break;
        case COND_INF:
            conditionAutre->type = COND_SUP;
            conditionAutre->val += 1;
            break;
        case COND_SUP:
            conditionAutre->type = COND_INF;
            conditionAutre->val -= 1;
            break;
    }
}