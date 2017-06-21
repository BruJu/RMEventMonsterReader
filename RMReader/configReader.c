#include "configReader.h"
#include "grille.h"
#include "grammairePrincip.h"
#include "grammaireSousGrp.h"
#include "listReader.h"
#include "tools.h"
#include <string.h>         // strcpy
#include <stdlib.h>         // atoi


/**
 * Lit la prochaine ligne du BufferizedFile;
 * 
 * Renvoie 0 si réussite
 * Renvoie -1 si pas de fichier à exploiter
 * Renvoie -2 si fin de fichier
 */
int BufferizedFile_nextString(BufferizedFile * file) {
    if (file == NULL || file->fichier == NULL)
        return -1;
    
    if (fgets(file->buffer, BUFFERIZEDFILESIZE, file->fichier) == NULL)
        return -2;
    
    return 0;
}

/**
 * Lit les premières lignes du fichier passé en paramètre (config.txt) et 
 * rempli la grille qui accueillera les données de la Grille.
 * 
 * La grille renvoyée contient la place suffisante pour acceuillir les données
 * et les noms des variables sont mis en places.
 * 
 * Le BufferizedFile aura son prochain readLine qui agira sur un nom de fichier.
 * 
 * Gestion de mémoire : Faire un libererGrille()
 */
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
        /* NOM DES VARIABLES POUR LE PREMIER GROUPE */
        
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

/**
 * Rempli la grille à partir du nom du fichier dans la prochaine ligne du BufferizedFile
 */
int lireUnFichier(BufferizedFile * file, Grille * grille) {
    ASSERT(BufferizedFile_nextString(file));
    
    char buff1[50], prefixe[50];
    
    
    strcpy(buff1, &(file->buffer[1]));
    string_retirerSauts(buff1);
    
    printf("Fichier %s :\n", buff1);
    
    switch(file->buffer[0]) {
        case '=':
            return !remplirTableur_init(grille, buff1);
            
        case '@':
            ASSERT(BufferizedFile_nextString(file));
            
            strcpy(prefixe, file->buffer);
            string_retirerSauts(file->buffer);
            
            remplacerLesChaines(buff1, grille, prefixe);
            break;
        case '}':
            return gramSG_init(file, grille);
        default:
            printf("Paramètre %c invalide\n", file->buffer[0]);
            printf("%s\n", file->buffer);
            return -1;
    }
    
    return 0;
}

/**
 * Renvoi un objet grille qui répond aux instructions du fichier config.txt
 * 
 * Gestion de mémoire : Faire un libererGrille()
 */
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
    
    grille_raz(grille);
    
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
    
    return grille;
}

