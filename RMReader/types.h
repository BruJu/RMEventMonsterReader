#ifndef __H_TYPES__
#define __H_TYPES__

#define SIZEMAXSTOREDTEXT (50)
#define BUFFERIZEDFILESIZE (255)

#define ASSERT(x)   if(x) return 1

#include <stdio.h>

typedef enum {
    ChgSwitch,
    ChgVariable,
    Label,
    JumpToLabel,
    ForkIf,
    ForkElse,
    ForkEnd,
    Loop,
    LoopEnd,
    LoopBreak,
    ShowPicture,
    ChangeItem,
    Void,
    Instr_Erreur,
    Ignore
} Instruction;

typedef enum {
    Egal, Different,
    Plus, Moins, Fois, Divise, Modulo,
    Inferieur, Superieur,
    InfEgal, SupEgal
} Signe;

typedef union {
    struct {
        int numero;
        Signe signe;
        int nouvelleValeur;
    } affectation;              // ChgSwitch, ChgVariable
    int numero;                 // Label, JumpToLabel
    
    struct {
        int type;           // 0 = switch, 1 = variable
        int numero;
        Signe comparatif;
        int valeur;
        int pointeur;       // 1 = voir la variable [valeur]
    } forkIf;                   // ForkIf
    
    struct {
        int numero;
        int quantite;
    } changeItem;               // ChangeItem
    char showPicture[255];      // ShowPicture
    /* rien */ // ForkElse, ForkEnd, Loop, LoopEnd, LoopBreak, Void,
} InstructionBis;

typedef struct {
    Instruction    instruction;
    InstructionBis complement;
} InstructionEnsemble;


typedef enum { VS_VARIABLE, VS_SWITCH, VS_CHAINE } VS_Possibilitees;

typedef struct {
    char nom[20];
    VS_Possibilitees type;
    int position;
} VariableSurveillee;

typedef union {
    int val;
    char txt[SIZEMAXSTOREDTEXT];
} Enregistrement;

typedef struct {
    int numeroVariable;
    int colonne;
} VariableGroupee;

typedef struct {
    int identifiantMax;
    
    int nbDeChamps;
    int debutSousEnsembles;             // 1 <= debutSousEnsembles <= nbDeChamps
    int nbDElementsParSousEnsembles;
    int nbDeSousEnsembles;              // nbDeChamps = debutSousEnsembles + nbDeSousEnsmbles * nbDelementsParSousEnsembles
    
    VariableSurveillee * variables;     // taille nbDeChamps
    Enregistrement * enregistrements;   // taille nbDeChamps x identifiantMax
} Grille;


typedef struct {
    FILE * fichier;
    char buffer[BUFFERIZEDFILESIZE];
} BufferizedFile;

#endif