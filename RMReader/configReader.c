#include "configReader.h"
#include "grille.h"
#include <string.h>         // strcpy
#include <stdlib.h>         // atoi

int BufferizedFile_nextString(BufferizedFile * file) {
    if (file == NULL || file->fichier == NULL)
        return -1;
    
    if (fgets(file->buffer, BUFFERIZEDFILESIZE, file->fichier) == NULL)
        return -2;
    
    return 0;
}


Grille * preparerTableur(BufferizedFile * file) {
    if (BufferizedFile_nextString(file)) {
        fprintf(stderr, "Le fichier config est vide\n");
        return NULL;
    }
    
    Grille * grille = initialiserGrille(file->buffer, 0);
    int loop, r;
    
    char nom[50], position[50];
    int debutPosition;
    VariableSurveillee * vs;
    
    for (loop = 0 ; loop != grille->debutSousEnsembles ; loop++) {
        r = BufferizedFile_nextString(file);
        
        if (r)
            goto erreurFinDeFichierInnatendue;
        
        if (sscanf(file->buffer, "%s %s", nom, position) != 2)
            goto erreurFinDeFichierInnatendue;
        
        
        vs = &(grille->variables[loop]);
        
        strcpy(vs->nom, nom);
        
        switch(position[0]) {
            case 'C':
                vs->type = VS_CHAINE;
                debutPosition = 1;
                break;
            case 'I':
                vs->type = VS_SWITCH;
                debutPosition = 1;
                break;
            default:
                vs->type = VS_VARIABLE;
                debutPosition = 0;
        }
        
        vs->position = atoi(position + debutPosition);
        
        if (vs->position == 0) {
            goto erreurFinDeFichierInnatendue;
        }
    }
    
    // Sous groupes
    if (grille->nbDeSousEnsembles != 0) {
        r = BufferizedFile_nextString(file);
        if (r || file->buffer[0] != '[')
            goto erreurFinDeFichierInnatendue;
        
        int pos_c = 1;
        int pos_r;
        /* NOM DES VARIABLES POOUR LE PREMIER GROUPE */
        
        // TODO : Corriger les dépassements de mémoire évidents
        for (loop = 0 ; loop != grille->nbDElementsParSousEnsembles ; loop++) {
            pos_r = 0;
            
            vs = &(grille->variables[grille->debutSousEnsembles + loop]);
            
            while (file->buffer[pos_c] != ',' && file->buffer[pos_c] != ']') {
                vs->nom[pos_r++] = file->buffer[pos_c++];
            }
            
            vs->nom[pos_r] = 0;
            pos_c++;
        }
        
        if (file->buffer[pos_c-1] != ']')
            goto erreurFinDeFichierInnatendue;
        
        /* RECOPIER POUR LES AUTRES GROUPES */
        for (loop = 0 ; loop != grille->nbDElementsParSousEnsembles ; loop++) {
            for (int j = 1 ; j != grille->nbDeSousEnsembles ; j++) {
                strcpy(grille->variables[grille->debutSousEnsembles + loop + j * grille->nbDElementsParSousEnsembles].nom,
                       grille->variables[grille->debutSousEnsembles + loop].nom);
            }
        }
        
        /* Mettre en place les numéros de variables */
        int curseur;    int valeurLue;  VS_Possibilitees vs_poss;
        for (int numGroupe = 0 ; numGroupe != grille->nbDeSousEnsembles ; numGroupe++) {
            r = BufferizedFile_nextString(file);
            
            if (r || file->buffer[0] != '>')  goto erreurFinDeFichierInnatendue;
            curseur = 1;
            
            for (int idVal = 0 ; idVal != grille->nbDElementsParSousEnsembles ; idVal++) {
                valeurLue = 0;
                
                if (file->buffer[curseur] == 'I') {
                    vs_poss = VS_SWITCH;
                    curseur++;
                } else if (file->buffer[curseur] == 'C') {
                    vs_poss = VS_CHAINE;
                    curseur++;
                } else {
                    vs_poss = VS_VARIABLE;
                }
                
                while (file->buffer[curseur] != 0    && file->buffer[curseur] != '\r'
                    && file->buffer[curseur] != '\n' && file->buffer[curseur] != ',') {
                    
                    if (!(file->buffer[curseur] >= '0' && file->buffer[curseur] <= '9'))
                        goto erreurFinDeFichierInnatendue;
                    
                    valeurLue = valeurLue * 10 + (file->buffer[curseur] - '0');
                    
                    curseur++;
                }
                
                if (valeurLue == 0)
                    goto erreurFinDeFichierInnatendue;
                
                curseur++;
                
                vs = &(grille->variables[grille->debutSousEnsembles + numGroupe * grille->nbDElementsParSousEnsembles + idVal]);
                vs->position = valeurLue;
                vs->type = vs_poss;
            }
        }
    }
    
    return grille;
    
erreurFinDeFichierInnatendue:
    libererGrille(grille);
    return NULL;
}

int lireUnFichier(BufferizedFile * file, Grille * grille) {
    (void) file;
    (void) grille;
    return 0;
}


Grille * configReader() {
    BufferizedFile file;
    Grille * grille;
    
    file.fichier = fopen("..\\config.txt", "r");
    if (file.fichier == NULL) {
        fprintf(stderr, "Le fichier config n'a pas été trouvé\n");
        return NULL;
    }
    
    grille = preparerTableur(&file);
    if (grille == NULL) {
        return NULL;
    }
    
    return grille;
    
    /*
    int k;
    while (1) {
        k = lireUnFichier(&file, grille);
        
        if (k == -1) {
            libererGrille(grille);
            return NULL;
        }
            
        if (k == 1)
            break;
    }
    */
    return grille;
}

