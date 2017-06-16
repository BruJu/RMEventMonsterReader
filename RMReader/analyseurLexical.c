#include "types.h"

#define NBCHARyyLine 255

char yyLine[NBCHARyyLine];
Instruction yyInstruction;
InstructionBis yyInstructionBis;

#define NB_INSTRUCTIONS 13

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
 * Non implémenté
 */


char instructionModeles[NB_INSTRUCTIONS] = {
    "<> Change Switch: [_] = _",
    "<> Change Variable: [_] _ _",
    "<> Change Items: _ _ piece of item #_",
    "<> Label: _",
    "<> Jump To Label: _",
    "<> Fork Condition: If _ [_] _ _ _ ¤",
    "<> Loop",
    "<> Break Loop",
    "<> Show Picture: #_, _, ¤", // ¤ = peu importe le reste
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


int filedescriptor = -1;


/**
 *  Lit une ligne dans le fichier
 * 
 * renvoie 1 si fin de fichier
 * -1 si pas de fichier ouvert ou erreur sur read
 */
int readLine() {
    if (filedescriptor == -1) {
        return -1;
    }
    
    int r;
    r = _read(filedescriptor, yyLine, NBCHARyyLine-1);
    
    if (r < 0)
        return r;
        
    yyLine[r] = 0;
    
    if (r == 0)
        return 1;
    else
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


InstructionEnsemble * analyserLigne(InstructionsEnCoursDeLecture iECDL) {
    InstructionEnsemble * instructionEnsemble = malloc(sizeof(InstructionEnsemble));
    if (instructionEnsemble == NULL)
        return NULL;
        
        
    instructionEnsemble.instruction = iECDL.instruction;
    
    if (iECDL.instruction == ChgSwitch) {
        // "<> Change Switch: [_] = _" [ChgSwitch] Numéro du switch, nouvelle position
        instructionEnsemble.complement.affectation.numero = atoi(iECDL.chaine);
        
        // ON ou OFF ?
        if (iECDL.chaine[1*50 + 1] == 'N') {
            instructionEnsemble.complement.affectation.nouvelleValeur = 0;
        } else {
            instructionEnsemble.complement.affectation.nouvelleValeur = 1;
        }
    } else if (iECDL.instruction == ChgVariable) {
        // "<> Change Variable: [_] _ _" [ChgVariable] Numéro de la variable, Opération, Valeur
        instructionEnsemble.complement.affectation.numero = atoi(iECDL.chaine);
        
        /*
        <> Change Variable: [1] = 0
        <> Change Variable: [1] -= 0
        <> Change Variable: [1] += 0
        <> Change Variable: [1] *= 0
        <> Change Variable: [1] /= 0
        <> Change Variable: [1] Mod= 0
        */
        
        switch (iECDL.chaine[1*50]) {
        case '=':
            instructionEnsemble.complement.affectation.signe = Egal;
            break;
        case '-':
            instructionEnsemble.complement.affectation.signe = Moins;
            break;
        case '+':
            instructionEnsemble.complement.affectation.signe = Plus;
            break;
        case '*':
            instructionEnsemble.complement.affectation.signe = Fois;
            break;
        case '/':
            instructionEnsemble.complement.affectation.signe = Divise;
            break;
        case 'M':
            instructionEnsemble.complement.affectation.signe = Modulo;
            break;
        default:
            instructionEnsemble.complement.affectation.signe = Egal;
            break;
        }
        
        /*
        Pour l'instant seul les valeurs brutes sont prises en compte
        TODO : Mettre en place les autres fonctionnalités proposées par RPG Maker
        */
        
        instructionEnsemble.complement.affectation.nouvelleValeur = atoi(iECDL.chaine + 2*50);
    } else if (iECDL.instruction == ChangeItem) {
        // "<> Change Items: _ _ piece of item #_" [ChangeItem] Add/Remove Quantité ID
        instructionEnsemble.complement.changeItem.numero = atoi(iECDL.chaine + 2*50);
        instructionEnsemble.complement.changeItem.quantite = atoi(iECDL.chaine + 1*50);
        
        if (iECDL.chaine[0] == 'R') {   // Remove
            instructionEnsemble.complement.changeItem.quantite *= -1;
        }
    } else if (iECDL.instruction == Label || iECDL.instruction == JumpToLabel) {
        instructionEnsemble.complement.numero = atoi(iECDL.chaine);
    } else if (iECDL.instruction == ShowPicture) {
        // "<> Show Picture: #_, _, ¤" [ShowPicture]
        strcpy(instructionEnsemble.complement.showPicture, iECDL.chaine + 1*50);
    } else if (IECDL.instruction == ForkIf) {
        // "<> Fork Condition: If _ [_] _ _ _ ¤" [ForkIf]
        
        /*
        Exemples :
        <> Fork Condition: If Switch [1] == ON then ...
        <> Fork Condition: If Variable [1] == 0 then ...
        <> Fork Condition: If Variable [1] <= 0 then ...
        <> Fork Condition: If Variable [1] < 0 then ...
        */
        
        
        
        
        
    }
    
    /*
    
       
     * 
     * */
    
    
    return instructionEnsemble;
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




