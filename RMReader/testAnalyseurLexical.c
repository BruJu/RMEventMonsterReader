#include "types.h"
#include <stdio.h>
#include <stdlib.h>

int nbDespaces = 0;


    Egal, Different,
    Plus, Moins, Fois, Divise, Modulo,
    Inferieur, Superieur,
    InfEgal, SupEgal

char * obtenirRepresentationDunSigne(Signe signe) {
    switch(signe) {
    case Egal :
        return "=";
        
        
        
        
    }
    
    
    // TODO
    return "";
}

char * obtenirRepresentationDunSigne(int nombre) {
    if (nombre)
        return "Vrai";
    else   
        return "Faux";
}


void printEspaces() {
    for (int i = 0 ; i != nbDespaces ; i++)
        printf("  ");
}

void afficherUnElement(InstructionEnsemble * instructionEnsemble) {
    if (   instructionEnsemble->instruction == ForkEnd
        || instructionEnsemble->instruction == LoopEnd
        || instructionEnsemble->instruction == ForkElse) {
            nbDespaces--;
    }
    
    if (instructionEnsemble->instruction != Label) {
        printEspaces();
    }
    
    switch (instructionEnsemble->instruction) {
    case ChgSwitch :
        {
            printf("$%d = %s\n",
                    instructionEnsemble->complement.affectation.numero,
                    obtenirRepresentationDunBooleen(InstructionEnsemble->complement.affectation.nouvelleValeur));
        }
        break;
    case ChgVariable :
        {
            printf("[%d] %s %d\n",
                    instructionEnsemble->complement.affectation.numero, 
                    obtenirRepresentationDunSigne(instructionEnsemble->complement.affectation.signe), 
                    instructionEnsemble->complement.affectation.nouvelleValeur);
        }
        break;
    case Label :
        {
            printf("Etiquette %d\n", instructionEnsemble->complement.numero);
        }
        break;
    case JumpToLabel :
        {
            printf("Aller à %d\n", instructionEnsemble->complement.numero);
        }
        break;
    case ForkIf :
        {
            /*        int type;           // 0 = switch, 1 = variable
        int numero;
        Signe comparatif;
        int valeur;
        int pointeur;       // 1 = voir la variable [valeur]
         * */
            
            printf("Si ");
            if (instructionEnsemble->complement.forkIf.type == 0) {
                // Switch
                printf("$%d = %s",
                    instructionEnsemble->complement.forkIf.numero,
                    obtenirRepresentationDunBooleen(instructionEnsemble->complement.forkIf.valeur));
            } else {
                // Variable
                printf("[%d] %s ",
                    instructionEnsemble->complement.forkIf.numero,
                    obtenirRepresentationDunSigne(instructionEnsemble->complement.forkIf.comparatif));
                
                if (instructionEnsemble->complement.forkIf.pointeur == 1) {
                    printf("[%d]", instructionEnsemble->complement.forkIf.valeur);
                } else {
                    printf("%d", instructionEnsemble->complement.forkIf.valeur);
                }
            }
            
            printf("\n");
            nbDespaces++;
        }
        break;
    case ForkElse :
        {
            printf("Sinon\n");
            nbDespaces++;
        }
        break;
    case ForkEnd :
        {
            printf("Fin Si\n");
        }
        break;
    case Loop :
        {
            printf("Boucler\n");
            nbDespaces++;
        }
        break;
    case LoopEnd :
        {
            printf("Fin Boucle\n");
        }
        break;
    case LoopBreak :
        {
            printf("Sortir de la boucle\n");
        }
        break;
    case ShowPicture :
        {
            printf("Afficher l'image %s\n", instructionEnsemble->complement.showPicture);
        }
        break;
    case ChangeItem :
        {
            printf("Quantité de %d modifiée de %d",
                instructionEnsemble->complement.changeItem.numero,
                instructionEnsemble->complement.changeItem.quantite);
        }
        break;
    case Void :
        {
            printf("\n");
        }
        break;
    default :
        printf("!! Instruction non reconnue\n");
        break;
    }
}
