#include "types.h"

char yyLine[255];
Instruction yyInstruction;
InstructionBis yyInstructionBis;

#define NB_INSTRUCTIONS 13

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


char instructionModeles[NB_INSTRUCTIONS] = {
    "<> Change Switch: [_] = _",
    "<> Change Variable: [_] _ _",
    "<> Change Items: Add _ piece of item #_",
    "<> Label: _",
    "<> Jump To Label: _",
    "<> Fork Condition: If _ [_] _ _ _ ¤",
    "<> Loop",
    "<> Break Loop",
    "<> Show Picture: _, _, ¤", // ¤ = peu importe le reste
    ": End of fork",
    ": End of loop",
    ": Else ...",
    "<>"
};

Instruction instructionsCorrespondantes[NB_INSTRUCTIONS] = {
    ChgSwitch,
    ChgVariable,
    ChangeItem,
    Label,
    JumpToLabel,
    ForkIf,
    Loop,
    LoopEnd,
    ForkElse,
    ShowPicture,
    ForkEnd,
    LoopBreak,
    Void
}

typedef struct {
    Instruction instruction,
    char chaine[5*50]
} InstructionsEnCoursDeLecture;



/**
 *  Lit une ligne dans le fichier
 * 
 * renvoie 1 si fin de fichier
 */
int readLine() {
    return 0;
}



InstructionsEnCoursDeLecture lireLigne() {
    InstructionsEnCoursDeLecture resultat;
    int i_schema, i_chaineEnCours, i_ligneActuelle;
    int chaineActuelle;
    int schemaActuel;
    
    // Avancer les espaces
    for (i_ligneActuelle = 0
        ; !yyLine[i_ligneActuelle] || yyLine[i_ligneActuelle] == ' '
        ; i_ligneActuelle ++) {
    }
    
    if (!yyLigne[i_ligneActuelle]) {
        resultat.instruction = Instr_Erreur;
        return resultat;
    }
    
    // Parcourir la liste des modèles
    for (schemaActuel = 0 ; schemaActuel != NB_INSTRUCTIONS ; schemaActuel ++) {
        i_schema = 0;
        i_chaineEnCours = 0;
        chaineActuelle = 0;
        
        while (1) {
            if (instructionModeles[schemaActuel][i_schema] == '¤'
                || (instructionModeles[schemaActuel][i_schema] == 0 && yyLigne[i_ligneActuelle] == 0) ) {
                resultat.instruction = instructionsCorrespondantes[schemaActuel];
                return resultat;
            }
            
            if (instructionModeles[schemaActuel][i_schema] == 0 || (yyLigne[i_ligneActuelle] == 0 && instructionModeles[schemaActuel][i_schema] != '_')) {
                break;
            }
            
            if (instructionModeles[schemaActuel][i_schema] != yyLigne[i_ligneActuelle]) {
                break;
            }
            
            if (instructionModeles[schemaActuel][i_schema] == '_') {
                // Fin d'analyse de variable ?
                if (instructionModeles[schemaActuel][i_schema+1] == yyLigne[i_ligneActuelle]) {
                    resultat.chaine[(chaineActuelle++) * 50 + i_chaineEnCours] = 0;
                    
                    i_chaineEnCours = 0;
                    i_schema ++;
                    continue;
                }
                
                // Non
                resultat.chaine[chaineActuelle * 50 + (i_chaineEnCours++)] = yyLigne[i_ligneActuelle++];
            } else {
                i_schema++;
                i_ligneActuelle++;
            }
            
        }
        
    }
    
    // Pas de résultat trouvé
    resultat.instruction = Instr_Erreur;
    return resultat;
}


InstructionEnsemble * analyserLigne() {
    InstructionEnsemble * instructionEnsemble = malloc(sizeof(InstructionEnsemble));
    if (instructionEnsemble == NULL)
        return NULL;
    
    
    
    
    
}



InstructionEnsemble * initialiserLaNouvelleLigne() {
    if (readLine())
        return 0;
    
    InstructionsEnCoursDeLecture iECDL;
    InstructionEnsemble * instructionEnsemble;
    
    iECDL = lireLigne();
    instructionEnsemble = analyserLigne(iECDL);
    
    return instructionEnsemble;
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
            // : End of fork
            // : End of loop
            // : Else ...
            // Instruction : <>
        
        
        
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



