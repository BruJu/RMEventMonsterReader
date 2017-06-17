#ifndef __H_TYPES__
#define __H_TYPES__

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
    Instr_Erreur
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


typedef struct {
    int id;
    char nom[100];
} Entree;

typedef struct {
    int nombredEntrees;
    int capacite;
    Entree * entree;
} Dictionnaire;


typedef struct {
    int id[50];
    char nom[100];
} EntreeChar;

typedef struct {
    int nombredEntrees;
    int capacite;
    EntreeChar * entree;
} DictionnaireChar;


#endif