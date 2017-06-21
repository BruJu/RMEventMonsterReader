#include "listReader.h"
#include "tools.h"

#include <stdlib.h>
#include <string.h>

void remplirLeDico(FILE * file, Dico * dico) {
    char buffer[255];
    
    int d = 0;
    int resultatScanf;
    
    while(fgets(buffer, sizeof(buffer), file) != NULL) {
        resultatScanf = sscanf(buffer, "%49[^;]:%49[^ \n\r]", (*dico)[d].identifiant, (*dico)[d].chaine);
        
        if (resultatScanf == 2) {
            d++;
        }
    }
    
    (*dico)[d].identifiant[0] = 0;
}

void remplacer(char * valeur, char * prefixe, Dico * dico) {
    int k = 0;
    
    while (prefixe[k]) {
        if (valeur[k] != prefixe[k])
            return;
        
        k++;
    }
    
    int charGauche = k;
    int dico_c;
    
    for (int d = 0 ; (*dico)[d].identifiant[0] ; d++) {
        k = charGauche;
        dico_c = 0;
        
        while ((*dico)[d].identifiant[dico_c] != 0) {
            if (valeur[k] != (*dico)[d].identifiant[dico_c])
                break;
            
            k++;
            dico_c++;
        }
        
        if ((*dico)[d].identifiant[dico_c] == 0
            && valeur[k] == 0) {
            strcpy(valeur, (*dico)[d].chaine);
            return;
        }
    }
}

void remplacerLesOccurrences(Grille * grille, char * prefixe, Dico * dico) {
    
    for (int id_grid = 0 ; id_grid != grille->identifiantMax ; id_grid++) {
        if (grille->enregistrements[id_grid].val == 0)
            continue;
        
        for (int c = 1 ; c != grille->debutSousEnsembles ; c++) {
            if (grille->variables[c].type != VS_SWITCH)
                continue;
            
            remplacer(grille_getEnregistrementChar(grille, id_grid, VS_SWITCH, c), prefixe, dico);
        }
        
        for (int sg = 0 ; sg != grille->nbDeSousEnsembles ; sg++) {
            for (int sgc = 0 ; sgc != grille->nbDElementsParSousEnsembles ; sgc++) {
                int num_var = grille->debutSousEnsembles + grille->nbDElementsParSousEnsembles * sg + sgc;
                
                if (grille->variables[num_var].type != VS_SWITCH)
                    continue;
                
                remplacer(grille_getEnregistrementChar(grille, id_grid, VS_SWITCH, num_var), prefixe, dico);
            }
        }
    }
}

void remplacerLesChaines(char * nomFichier, Grille * grille, char * prefixe) {
    Dico * dico;
    FILE * file;
    
    dico = malloc(sizeof(Dico));
    if (dico == NULL)
        return;
    
    file = fopen(nomFichier, "w");
    if (file == NULL) {
        perror("fopen dico");
        printf("%s", nomFichier);
        free(dico);
        return;
    }
    
    remplirLeDico(file, dico);
    fclose(file);
    
    remplacerLesOccurrences(grille, prefixe, dico);
    
    free(dico);
}
