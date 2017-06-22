#include "listReader.h"
#include "tools.h"

#include <stdlib.h>
#include <string.h>

/**
 * Rempli le dictionnaire avec les données dans le fichier donné en paramère.
 * 
 * Les données doivent être sous la forme
 * id chaine de remplacement
 */
void remplirLeDico(FILE * file, Dico * dico) {
    char buffer[255];
    
    int d = 0;
    int resultatScanf;

    while(fgets(buffer, sizeof(buffer), file) != NULL) {
        resultatScanf = sscanf(buffer, "%s %[^\n\r]", dico[d].identifiant, dico[d].chaine);
        
        if (resultatScanf == 2) {
            d++;
        }
    }
    
    dico[d].identifiant[0] = 0;
}

/**
 * Si il existe x tq valeur = prefixe . dico[x].id
 * alors remplace valeur par dico[x].chaine
 */
void remplacer(char * valeur, char * prefixe, Dico * dico) {
    int k = 0;
    
    while (prefixe[k]) {
        if (valeur[k] != prefixe[k])
            return;
        
        k++;
    }
    
    int charGauche = k;
    int dico_c;
    
    for (int d = 0 ; dico[d].identifiant[0] ; d++) {
        k = charGauche;
        dico_c = 0;
        
        while (dico[d].identifiant[dico_c] != 0) {
            if (valeur[k] != dico[d].identifiant[dico_c])
                break;
            
            k++;
            dico_c++;
        }
        
        if (dico[d].identifiant[dico_c] == 0
            && valeur[k] == 0) {
            strcpy(valeur, dico[d].chaine);
            return;
        }
    }
}

/**
 * Remplace toutes les chaines de la grille par les valeurs correspondantes dans dico
 * si elles existent
 */
void remplacerLesOccurrences(Grille * grille, char * prefixe, Dico * dico) {
    int avantGroupee;
    int idDuGroupe;
    int decGroupe;
    
    for (int c = 1 ; c != grille->nbDeChamps ; c++) {
        if (grille->variables[c].type != VS_CHAINE)
            continue;
            
        avantGroupee = c < grille->debutSousEnsembles;
        
        if (grille->nbDElementsParSousEnsembles != 0) {
            idDuGroupe = (c - grille->debutSousEnsembles)/grille->nbDElementsParSousEnsembles;
        } else {
            idDuGroupe = 0;
        }
        
        decGroupe = idDuGroupe * grille->nbDElementsParSousEnsembles + grille->debutSousEnsembles;
        
        for (int id_grid = 0 ; id_grid != grille->identifiantMax ; id_grid++) {
            if (grille->enregistrements[id_grid*grille->nbDeChamps].val == 0)
                continue;
                
            if (avantGroupee || grille->enregistrements[decGroupe + id_grid * grille->nbDeChamps].val) {
                remplacer(grille->enregistrements[id_grid * grille->nbDeChamps + c].txt, prefixe, dico);
            }
        }
        
        
    }
}

/**
 * Remplace toutes les valeurs de la grille par les valeurs dont il existe x tq
 * à la ligne x de la forme "a b" (où b désigne le reste de la ligne après le premier espace)
 * la valeur = prefixe . a
 * La valeur est alors remplacée par b
 */
void remplacerLesChaines(char * nomFichier, Grille * grille, char * prefixe) {
    Dico * dico;
    FILE * file;
    
    dico = malloc(sizeof(Dico) * DICTIONNARY_SIZE);
    if (dico == NULL)
        return;
    
    string_retirerSauts(nomFichier);
    
    file = fopen(nomFichier, "r");
    if (file == NULL) {
        perror("fopen dico");
        printf("%s", nomFichier);
        free(dico);
        return;
    }
    
    string_retirerSauts(prefixe);
    
    remplirLeDico(file, dico);
    fclose(file);
    
    remplacerLesOccurrences(grille, prefixe, dico);
    
    free(dico);
}
