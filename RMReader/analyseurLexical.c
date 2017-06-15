
char yyLine[255];


/*

- SCRIPT -
<> Change Switch: [1] = ON
<> Change Variable: [1] = 0
<> Label: 1
<> Jump To Label: 1
<> Fork Condition: If Switch [1] == ON then ...
 <>
: Else ...
 <>
: End of fork
<> Fork Condition: If Variable [10] != 0 then ...
 <>
: Else ...
 <>
: End of fork
<> Loop
 <>
: End of loop
<> Break Loop
<> Show Picture: #1, actionslibre2, (160, 120), Mgn 100%, Tsp 0%/0%
<> Change Items: Add 1 piece of item #1

*/


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
    Void
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
    /* TODO */ // ForkIf
    struct {
        int numero;
        int quantite;
    } changeItem;               // ChangeItem
    char showPicture[255];      // ShowPicture
    /* rien */ // ForkElse, ForkEnd, Loop, LoopEnd, LoopBreak, Void,
} InstructionBis;

/**
 *  Lit une ligne dans le fichier
 * 
 * renvoie 1 si fin de fichier
 */
int readLine() {
    return 0;
}



/**
 * renvoie 1 si pas de nouvelle ligne
 * renvoie 2 si erreur
 */
int lireNouvelleLigne() {
    if (readLine()) {
        return 1;
    }
    
    int positionChar = 0;
    
    while (positionChar != 255 && yyLine[positionChar] == ' ') {
        positionChar++;
    }
    
    if (positionChar == 255)
        return 2;
    
    if (yyLine[positionChar] == '<') {
        if (!(yyLine[positionChar+1] == '>' && yyLine[positionChar+2] == ' '))
            return 2;
        
        positionChar += 3;
        
        
        
    } else if (yyLine[positionChar] == ':') {
        if (!(yyLine[positionChar+1] == ' ' && yyLine[positionChar+2] == 'E'))
            return 2;
        
        positionChar += 3;
        
        
        
    } else {
        return 2;
    }
    
}



