#include "analyseurLexical.h"

#include <stdlib.h>
#include <string.h>         // strcpy
#include <stdio.h>

/*
 * vérifier la cohérence lorsqu'on lit un symbole _ dans une variable
 * 
 * 
 * 
 */

#define NBCHARyyLine 255
#define NB_INSTRUCTIONS 18

char yyLine[NBCHARyyLine];
FILE * filedescriptor = NULL;


/*
 * Fonctionnalités non gérées :
 * • ChgSwitch
 * Il n'est pas possible de choisir une plage
 * Il n'est pas possible d'inverser l'état d'un switch
 * Il n'est pas possible d'utiliser un pointeur
 * • ChgVariable
 * Pas de plage / pointeur
 * On ne peut mettre autre chose qu'un nombre brut
 * • Change Item
 * Pas de pointage
 * • Fork If
 */



/* |||||||||||==========================================||||||||||
 * |||||||||||==========================================||||||||||
 * ||||||||||| SCHEMA DES INSTRUCTIONS PRISES EN COMPTE ||||||||||
 * |||||||||||==========================================||||||||||
 * |||||||||||==========================================|||||||||| */

char * instructionModeles[] = {
    "<> Change Switch: [_] = _",
    "<> Change Variable: [_] _ _",
    "<> Change Items: _ _ piece of item #_",
    "<> Label: _",
    "<> Jump To Label: _",
    "<> Fork Condition: If _ [_] _ _ _ |",
    "<> Loop",
    "<> Break Loop",
    "<> Show Picture: #_, _, |", // | = peu importe le reste
    ": End of fork",
    ": End of loop",
    ": Else ...",
    "<>",
    "<> Set Event Location|",
    "<> Change Panorama|",
    "<> Play BGM:|",
    "<> Erase Picture:|",
    "<> Set Screen Tone:|"
};


Instruction instructionsCorrespondantes[] = {
    ChgSwitch,
    ChgVariable,
    ChangeItem,
    Label,
    JumpToLabel,
    ForkIf,
    Loop,
    LoopBreak,
    ShowPicture,
    ForkEnd,
    LoopEnd,
    ForkElse,
    Void,
    Ignore,
    Ignore,
    Ignore,
    Ignore,
    Ignore
    
};



/**
 *  Lit une ligne dans le fichier
 * 
 * renvoie 1 si fin de fichier
 * -1 si pas de fichier ouvert ou erreur sur read
 */
int readLine() {
    if (filedescriptor == NULL) {
        return -1;
    }
    
    if (fgets(yyLine, NBCHARyyLine, filedescriptor) == NULL) {
        return 1;
    }
    
    for (int i = 0 ; yyLine[i] != 0 ; i++) {
        if (yyLine[i] == '\r' || yyLine[i] == '\n') {
            yyLine[i] = 0;
            return 0;
        }
    }
    
    return 0;
}

/**
 * Converti la ligne yyLine afin de reconnaitre quel est le type d'instruction
 * et isoler les différents paramètres
 */
InstructionsEnCoursDeLecture lireLigne() {
    InstructionsEnCoursDeLecture resultat;
    int i_schema, i_chaineEnCours, i_ligneActuelle, i_debutligneActuelle;
    int chaineActuelle;
    int schemaActuel;
    
    // Avancer les espaces
    for (i_ligneActuelle = 0
        ; yyLine[i_ligneActuelle] && yyLine[i_ligneActuelle] == ' '
        ; i_ligneActuelle ++) {
    }
    
    i_debutligneActuelle = i_ligneActuelle;
    
    if (!yyLine[i_ligneActuelle]) {
        resultat.instruction = Ignore;
        return resultat;
    }
    
    // Parcourir la liste des modèles
    for (schemaActuel = 0 ; schemaActuel != NB_INSTRUCTIONS ; schemaActuel ++) {
        i_schema = 0;
        i_chaineEnCours = 0;
        chaineActuelle = 0;
        i_ligneActuelle = i_debutligneActuelle;
        
        while (1) {
            if (instructionModeles[schemaActuel][i_schema] == '|'
                || (instructionModeles[schemaActuel][i_schema] == 0 && yyLine[i_ligneActuelle] == 0) ) {
                resultat.instruction = instructionsCorrespondantes[schemaActuel];
                return resultat;
            }
            
            if (instructionModeles[schemaActuel][i_schema] == 0 || (yyLine[i_ligneActuelle] == 0 && instructionModeles[schemaActuel][i_schema] != '_')) {
                break;
            }
            
            if (instructionModeles[schemaActuel][i_schema] == '_') {
                // Fin d'analyse de variable ?
                if (instructionModeles[schemaActuel][i_schema+1] == yyLine[i_ligneActuelle]) {
                    resultat.chaine[(chaineActuelle++) * CHAINESIZE + i_chaineEnCours] = 0;
                    
                    i_chaineEnCours = 0;
                    i_schema ++;
                    continue;
                }
                
                // Non
                resultat.chaine[chaineActuelle * CHAINESIZE + (i_chaineEnCours++)] = yyLine[i_ligneActuelle++];
            } else {

                if (instructionModeles[schemaActuel][i_schema] != yyLine[i_ligneActuelle]) {
                    
                    // Si on attend un < et qu'on voit un @, c'est qu'on a c/c directement de RPG Maker 2003
                    if (instructionModeles[schemaActuel][i_schema] != '<'
                        && yyLine[i_ligneActuelle] != '@')
                        break;
                }
                
                i_schema++;
                i_ligneActuelle++;
            }
            
        }
    }
    
    // Pas de résultat trouvé
    resultat.instruction = Instr_Erreur;
    return resultat;
}

/**
 * Decrypte les paramètres d'une instruction dont le type est connu et dont les paramètres
 * ont été isolés.
 */
InstructionEnsemble * analyserLigne(InstructionsEnCoursDeLecture iECDL) {
    InstructionEnsemble * instructionEnsemble = malloc(sizeof(InstructionEnsemble));
    if (instructionEnsemble == NULL)
        return NULL;
    
    
    instructionEnsemble->instruction = iECDL.instruction;
    
    if (iECDL.instruction == Ignore)
        return instructionEnsemble;
    
    if (iECDL.instruction == ChgSwitch) {
        // "<> Change Switch: [_] = _" [ChgSwitch] Numéro du switch, nouvelle position
        instructionEnsemble->complement.affectation.numero = atoi(iECDL.chaine);
        instructionEnsemble->complement.affectation.signe = Egal;
        // ON ou OFF ?
        if (iECDL.chaine[1*CHAINESIZE + 1] == 'N') {
            instructionEnsemble->complement.affectation.nouvelleValeur = 1;
        } else {
            instructionEnsemble->complement.affectation.nouvelleValeur = 0;
        }
    } else if (iECDL.instruction == ChgVariable) {
        // "<> Change Variable: [_] _ _" [ChgVariable] Numéro de la variable, Opération, Valeur
        instructionEnsemble->complement.affectation.numero = atoi(iECDL.chaine);
        
        /*
        <> Change Variable: [1] = 0
        <> Change Variable: [1] -= 0
        <> Change Variable: [1] += 0
        <> Change Variable: [1] *= 0
        <> Change Variable: [1] /= 0
        <> Change Variable: [1] Mod= 0
        */
        
        switch (iECDL.chaine[1*CHAINESIZE]) {
        case '=':
            instructionEnsemble->complement.affectation.signe = Egal;
            break;
        case '-':
            instructionEnsemble->complement.affectation.signe = Moins;
            break;
        case '+':
            instructionEnsemble->complement.affectation.signe = Plus;
            break;
        case '*':
            instructionEnsemble->complement.affectation.signe = Fois;
            break;
        case '/':
            instructionEnsemble->complement.affectation.signe = Divise;
            break;
        case 'M':
            instructionEnsemble->complement.affectation.signe = Modulo;
            break;
        default:
            instructionEnsemble->complement.affectation.signe = Egal;
            break;
        }
        
        instructionEnsemble->complement.affectation.nouvelleValeur = atoi(iECDL.chaine + 2*CHAINESIZE);
    } else if (iECDL.instruction == ChangeItem) {
        // "<> Change Items: _ _ piece of item #_" [ChangeItem] Add/Remove Quantité ID
        instructionEnsemble->complement.changeItem.numero = atoi(iECDL.chaine + 2*CHAINESIZE);
        instructionEnsemble->complement.changeItem.quantite = atoi(iECDL.chaine + 1*CHAINESIZE);
        
        if (iECDL.chaine[0] == 'R') {   // Remove
            instructionEnsemble->complement.changeItem.quantite *= -1;
        }
    } else if (iECDL.instruction == Label || iECDL.instruction == JumpToLabel) {
        instructionEnsemble->complement.numero = atoi(iECDL.chaine);
    } else if (iECDL.instruction == ShowPicture) {
        // "<> Show Picture: #_, _, |" [ShowPicture]
        strcpy(instructionEnsemble->complement.showPicture, iECDL.chaine + 1*CHAINESIZE);
    } else if (iECDL.instruction == ForkIf) {
        // "<> Fork Condition: If _ [_] _ _ _ |" [ForkIf]
        
        /*
        Exemples :
        <> Fork Condition: If Switch [1] == ON then ...
        <> Fork Condition: If Variable [1] == 0 then ...
        <> Fork Condition: If Variable [1] <= 0 then ...
        <> Fork Condition: If Variable [1] < 0 then ...
        */
        
        if (iECDL.chaine[0] == 'S') {
            instructionEnsemble->complement.forkIf.type = 0;
        } else {
            instructionEnsemble->complement.forkIf.type = 1;
        }
        
        instructionEnsemble->complement.forkIf.numero = atoi(iECDL.chaine + 1*CHAINESIZE);
        
        /* Signe : == " !=" < <= >= > */
        char symboleEnCours;
        
        symboleEnCours = iECDL.chaine[2*CHAINESIZE];
        
        if (symboleEnCours == ' ') {
            // " !="
            instructionEnsemble->complement.forkIf.comparatif = Different;
        } else if (symboleEnCours == '=') {
            // "=="
            instructionEnsemble->complement.forkIf.comparatif = Egal;
        } else if (symboleEnCours == '<') {
            symboleEnCours = iECDL.chaine[2*CHAINESIZE + 1];
            
            if (symboleEnCours == '=') {
                instructionEnsemble->complement.forkIf.comparatif = InfEgal;
            } else {
                instructionEnsemble->complement.forkIf.comparatif = Inferieur;
            }
        } else { // >
            symboleEnCours = iECDL.chaine[2*CHAINESIZE + 1];
            
            if (symboleEnCours == '=') {
                instructionEnsemble->complement.forkIf.comparatif = SupEgal;
            } else {
                instructionEnsemble->complement.forkIf.comparatif = Superieur;
            }
        }
        
        // Objet auquel comparer
        if (iECDL.chaine[3*CHAINESIZE] == 'V') {
            instructionEnsemble->complement.forkIf.pointeur = 1;
            instructionEnsemble->complement.forkIf.valeur = atoi(iECDL.chaine + 4*CHAINESIZE + 1);
        } else {
            if (instructionEnsemble->complement.forkIf.type == 1) {
                instructionEnsemble->complement.forkIf.pointeur = 0;
                instructionEnsemble->complement.forkIf.valeur = atoi(iECDL.chaine + 3*CHAINESIZE);
            } else {
                if (iECDL.chaine[3*CHAINESIZE+1] == 'N') {
                    instructionEnsemble->complement.forkIf.valeur = 1;
                } else {
                    instructionEnsemble->complement.forkIf.valeur = 0;
                }
            }
        }
    }
    
    return instructionEnsemble;
}


/**
 * Converti la prochaine ligne du fichier filedescriptor en Instruction.
 *
 * Gestion de la mémoire : Il faut free() le résultat
 */
InstructionEnsemble * initialiserLaNouvelleLigne() {
    if (readLine())
        return NULL;
    
    InstructionsEnCoursDeLecture iECDL;
    InstructionEnsemble * instructionEnsemble;
    
    iECDL = lireLigne();
    instructionEnsemble = analyserLigne(iECDL);
    
    return instructionEnsemble;
}




