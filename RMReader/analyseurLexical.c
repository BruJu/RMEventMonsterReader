#include "types.h"

char yyLine[255];
Instruction yyInstruction;
InstructionBis yyInstructionBis;

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
    
    /* == Passer les espaces == */
    while (positionChar != 255 && yyLine[positionChar] == ' ') {
        positionChar++;
    }
    
    if (positionChar == 255)
        return 2;
    
    /*
     * TODO :
     * Dans la mesure où les performances ne sont pas une question vitale pour ce projet,
     * faire un dictionnaire avec la liste des modèles d'instructions
     * faire un lecteur qui renvoie
     * struct (instruction, chaine 1, chaine 2, chaine 3, chaine 4, ...)
     * et une autre fonction s'occupe de convertir les chaines en instructionbis
     * 
     * Avantages :
     * - Plus facilement maintenanble si on vuet ajouter des isntructions ou les modifier
     * Défaut :
     * - Redondance de traitements (négligeable car il n'y a qu'une vingtaine d'instruction
     * et les programmes à traiter ne devraient pas dépasser les 100000 lignes même en voyant large)
     */
    
        
    if (yyLine[positionChar] == '<') {
        /* == Instruction normale == */
        if (!(yyLine[positionChar+1] == '>')
            return 2;
        
        positionChar += 2;
        
        if (yyLine[positionChar] == 0) {
            // Instruction : <>
            yyInstruction = Void;
            return 0;
        }
        
        if (!yyLine[positionChar] == ' ')
            return 2;
        
        positionChar++;
        
        // <> Change Switch: [1] = ON
        // <> Change Variable: [1] = 0
        // <> Change Items: Add 1 piece of item #1
        // <> Label: 1
        // <> Jump To Label: 1
        // <> Fork Condition: If Switch [1] == ON then ...
        // <> Fork Condition: If Variable [10] != 0 then ...
        // <> Loop
        // <> Break Loop
        // <> Show Picture: #1, actionslibre2, (160, 120), Mgn 100%, Tsp 0%/0%
        
        
        
    } else if (yyLine[positionChar] == ':') {
        /* == End of something  == */
        if (!(yyLine[positionChar+1] == ' ' && yyLine[positionChar+2] == 'E'))
            return 2;
        
        positionChar += 3;
        
        if (yyLine[positionChar] == 'l') {
            // : Else ...
            yyInstruction = ForkElse;
            return 0;
        }
        
        // "nd of "  6 caractères
        positionChar += 6;
        
        if (yyLine[positionChar] == 'f') {
            // : End of fork
            yyInstruction = ForkEnd;
            return 0;
        } else if (yyLigne[positionChar == 'l') {
            // : End of loop
            yyInstruction == LoopEnd;
            return 0;
        }
        
        return 2;
    }
    
    return 2;
    
}



