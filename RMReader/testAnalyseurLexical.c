#include "testAnalyseurLexical.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>       // open
#include <fcntl.h>          // open
#include "analyseurLexical.h"


int nbDespaces = 0;


char * obtenirRepresentationDunSigne(Signe signe) {
    switch(signe) {
    case Egal:
        return "=";
    case Different:
        return "!=";
    case Plus:
        return "+";
    case Moins:
        return "-";
    case Fois:
        return "*";
    case Divise:
        return "/";
    case Modulo:
        return "%";
    case Inferieur:
        return "<";
    case Superieur:
        return ">";
    case InfEgal:
        return "<=";
    case SupEgal:
        return ">=";
    default:
        return "err";
    }
}

char * obtenirRepresentationDunBooleen(int nombre) {
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
    if (instructionEnsemble->instruction == Ignore)
        return;
    
    
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
                    obtenirRepresentationDunBooleen(instructionEnsemble->complement.affectation.nouvelleValeur));
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

extern FILE * filedescriptor;

void testerFichier(char * nom) {
    filedescriptor = fopen(nom, "r");
    if (filedescriptor == NULL) {
        perror("fopen");
        return;
    }
    
    InstructionEnsemble * instruct;
    
    while (1) {
        instruct = initialiserLaNouvelleLigne();
        
        if (instruct == NULL)
            break;
        
        if (instruct->instruction == Ignore)
            continue;
        
        afficherUnElement(instruct);
    }
    
    
    fclose(filedescriptor);
}