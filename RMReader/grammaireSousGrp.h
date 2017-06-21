#ifndef __H_COMPLETERSOUSGROUPES__
#define __H_COMPLETERSOUSGROUPES__

#include "grille.h"
#include "grammaire.h"

typedef struct {
    int variable;
    int colonne;
    char prefixe[50];
} RedirectionsSurGroupe;

typedef struct {
    RedirectionsSurGroupe * redir;
    int nbRedir;
    
    int colonnePicture;
    int colonneObjets;
} Param_completerSsGrp;


typedef struct {
    int ligne;
    int groupe;
} GramSG_avanceur;



int gramSG_init(BufferizedFile * file, Grille * grille);

/*
 * On utilise ici une grammaire simplifiée :
 * 
 * S -> Instruction S
 * S -> Epsilon
 * Instruction -> Condition | ShowPicture
 * Condition -> ForkIf S Condition'
 * Condition' -> ForkElse S ForkEnd
 * Condition' -> ForkEnd
 */


int gramSG_S();
int gramSG_Instruction();
int gramSG_Condition();
int gramSG_ConditionPrime();

StatutInstrDansGrammaire gramSG_etatInstruction();

int gramSG_avancer();


void gramSG_ConditionSqueezer();

void gramSG_ShowPicture();

int gramSG_ConditionAjouter();

void gramSG_ChgVariable();


void gramSG_apply(int slot, Modification * modif);


int testerCA (int valeur, ConditionAutre * condi);

#endif