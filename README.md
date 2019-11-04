
# RMEventReader



## Qu'est-ce que c'est ?

Il s'agit d'une liste d'implémentations d'Exécuteurs d'instructions pour des cas pratiques qui m'interessent.

Le but étant de détourner les instructions pour en tirer des données. Par exemple on peut facilement créer une classe qui va déterminer la liste de toutes les musiques utilisées, et sur quelles cartes elles le sont.



## Librairies utilisées

Aucune librairie, sauf CollectorBySimilarity n'est présente dans le dossier lib.

* CollectorBySimilarity : Un collecteur dont le but est de grouper les éléments selon une fonction d'égalité
* JavaLCFReader : Une librairie qui décrypte les fichiers LCF pour les transformer en objet.
* RMDechiffreur : Une librairie qui déchiffre les instructions RPG Maker extraites des fichiers LCF.


## Modules développés

### Listeur de Monstres

#### Problème traité

Soit des évènements de la forme

~~~~
si IDCombat = 1
  IdMonstre1 = 1
  HPMonstre1 = 5
  Nom1 = Monstre Pas Beau
fin si

si IDCombat = 2
  IdMonstre1 = 2
  HPMonstre = 6
  IdMonstre2 = 1
  HPMonstre2 = 5
  Nom1 = Bandit
  Nom2 = Monstre Pas Beau
fin si
~~~~

On aimerait générer un tableau plus lisible pour un être humain (qui potentiellement pourrait être imprimé par exemple).

~~~~
IdMonstre | HP | Nom | Id Combats
1 | 5 | Monstre Pas Beau | 1, 2
2 | 6 | Bandit | 2
~~~~




#### Reconnaissance de lettres

Dans la réalité, le nom des ennemis ne sont pas stockés dans une variable mais affichés sous forme d'image.

Les noms des ennemis sont écris sur des images en orange sur fond transparent. Ils ne sont pas disponibles autrement.

Le package reconnaissancedimage traite ce problème en appliquant une pseudo reconnaissance automatique de lettres.

##### reconnaissancedimage

Le principe est le suivant :
* On analyse l'image qui contient le nom et on extrait une matrice avec vrai à l'endroit où on estime qu'il y a du texte et faux ailleurs.
* On identifie les motifs qui composent l'image. On dit qu'il y a un motif quand on a une suite de colonnes dans la matrice avec au moins un vrai. On suppose donc que les lettres sont séparées par une colonne vide. (Comme ce n'est pas toujours vrai, on permet qu'un motif corresponde à une suite de lettres)
* On estime qu'il y a un espace si deux motifs sont séparés de 4 colonnes vides.
* Si le motif n'est pas dans la base de motif, on invite l'utilisateur a ajouté le motif reconnu dans la base de motifs. Sa seule tâche sera de remplacer le ? de la ligne par ce qu'il voit.
* Une fois tous les motifs de l'image vus, le programme restitue une chaîne qui est l'enchaînement des lettres représentées par le motif.

##### Exemple

Le programme utilise une table de conversion en nom d'image et texte écrit sur l'image. (non fournie)

Si le texte ne lui est pas connu, il essaye de reconnaître les lettres décrites sur l'image via une table de correspondance entre motifs et lettre (ou suite de lettre) qu'il possède.

Si un motif n'est pas reconnu, un affichage est fait pour signaler que le motif n'existe pas. Le même motif n'est pas signalé deux fois.

Dans cet exemple, on suppose que le programme a besoin de la chaîne contenue dans l'image idmonstre76-2.

~~~~
== Des monstres n'ont pas été reconnus ==
idmonstre76-2, 76, 51, 160, 3, 140, 4550, 288, 237, 162, 206, 237, 262,  

                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                              X                                                                             
    XX                                                                                                                      
    XX                                                                                                                      
   X  X    XXXX   XXXX   XXXX    XXXX   XXXX  X  X XXX                                                                      
   X  X   X      X           X  X      X      X  XX   X                                                                     
  X    X  X      X           X  X      X      X  X    X                                                                     
  X    X   XXX    XXX    XXXXX   XXX    XXX   X  X    X                                                                     
  XXXXXX      X      X  X    X      X      X  X  X    X                                                                     
 X      X     X      X  X    X      X      X  X  X    X                                                                     
 X      X XXXX   XXXX    XXXXX  XXXX   XXXX   X  X    X                                                                     
                                                
                                                
                                                                                                                                                          
Motif non reconnu :
   xx
   xx
  x  x
  x  x
 x    x
 x    x
 xxxxxx
x      x
x      x
? 24 24 36 36 66 66 126 129 129
Motif non reconnu :
 xxxx
x
x
 xxx
    x
    x
xxxx
? 30 1 1 14 16 16 15
Motif non reconnu :
 xxxx
     x
     x
 xxxxx
x    x
x    x
 xxxxx
? 30 32 32 62 33 33 62
Motif non reconnu :
x


x
x
x
x
x
x
x
? 1 0 0 1 1 1 1 1 1 1
Motif non reconnu :
x xxx
xx   x
x    x
x    x
x    x
x    x
x    x
? 29 35 33 33 33 33 33
== Identification == 
idmonstre76-2 ????????

~~~~

Si on ajoute la ligne "s 30 1 1 14 16 16 15" aux motifsconnus.txt


~~~~
== Des monstres n'ont pas été reconnus ==
idmonstre76-2, 76, 51, 160, 3, 140, 4550, 288, 237, 162, 206, 237, 262,  
                                                  
                                                                                                                            
                                              X                                                                             
    XX                                                                                                                      
    XX                                                                                                                      
   X  X    XXXX   XXXX   XXXX    XXXX   XXXX  X  X XXX                                                                      
   X  X   X      X           X  X      X      X  XX   X                                                                     
  X    X  X      X           X  X      X      X  X    X                                                                     
  X    X   XXX    XXX    XXXXX   XXX    XXX   X  X    X                                                                     
  XXXXXX      X      X  X    X      X      X  X  X    X                                                                     
 X      X     X      X  X    X      X      X  X  X    X                                                                     
 X      X XXXX   XXXX    XXXXX  XXXX   XXXX   X  X    X                                                                     
                                                
                                                
                                                                                                                                                   
Motif non reconnu :
   xx
   xx
  x  x
  x  x
 x    x
 x    x
 xxxxxx
x      x
x      x
? 24 24 36 36 66 66 126 129 129
Motif non reconnu :
 xxxx
     x
     x
 xxxxx
x    x
x    x
 xxxxx
? 30 32 32 62 33 33 62
Motif non reconnu :
x


x
x
x
x
x
x
x
? 1 0 0 1 1 1 1 1 1 1
Motif non reconnu :
x xxx
xx   x
x    x
x    x
x    x
x    x
x    x
? 29 35 33 33 33 33 33
== Identification == 
idmonstre76-2 ?ss?ss??
~~~~

Une fois tous les motifs ajoutés au fichier motifsconnus en remplaçant les ? par le symbole correspondant on obtient.

~~~~
== Des monstres n'ont pas été reconnus ==
idmonstre76-2, 76, 51, 160, 3, 140, 4550, 288, 237, 162, 206, 237, 262,  

== Identification == 
idmonstre76-2 Assassin

~~~~


 

### Calcul de formules de dégâts

#### Objectif

Ce module a pour but de permettre de générer des formules de dégâts compréhensibles.

Par exemple si on a l'enchaînement

~~~~
Temp = 50
Temp *= Magie
Temp2 = 10
Temp2 *= Niveau
Temp += Temp2
HPMonstre -= Temp
~~~~


Et qu'on spécifie que Magie et Niveau sont des statistiques, ainsi que HPMonstre est la vie de l'ennemi, il faut pouvoir générer "50 x Magie + 10 x Niveau" qui est la formule de dégâts tel qu'un humain la comprend.


#### Solution

Les formules sont présentées sous forme d'algorithme présentés de manière plus digeste pour un être humain. Le but étant que l'algorithme soit plus simple à lire mais également rende compte de la réalité.

Par exmeple
~~~~
Temp0 = 50
Temp = Temp0
Temp *= Magie
Temp2 = Temp
Temp2 += 2
Temp3 = Temp
Temp3 += 5
Temp4 = 7
HPMonstre -= Temp2
HPMonstre -= Temp3
~~~~

devient

~~~~
Temp = 50 * Magie
HPMonstre = HPMonstre - (Temp + 2) - (Temp + 5)
~~~~

Explication :

- HPMonstre est notre variable de sortie

- Temp0 n'est utilisé qu'une fois, on met sa valeur directement dans Temp qui l'utilise

- Temp est utilisé deux fois. On garde la ligne pour ne pas avoir des formules énormes à cause de répétitions

- Temp2 et Temp3 ne sont utilisés qu'une fois. On intégre directement leur valeur dans la modification de HPMonstre.

- Temp4 n'est jamais utilisé. On enlève la ligne (variable morte)





### Modules mineurs


#### Chercheur de références

RPG Maker 2003 offre un outil préintégré pour chercher les utilisations d'une variable, d'un interrupteur, ou les cartes sur lesquelles apparait un évènement portant un nom spécifique, et ce soit une carte définie, soit dans tous les évènements communs, soit dans tout le projet.

Cet outil très pratique a néanmoins plusieurs faiblesses :
- Parcourir avec toutes les cartes est assez lent. Par exemple sur un projet de 1200 cartes avec un processeur i7-4790K, chercher toutes les utilisations d'une variable prend 1 minute 22. A titre de comparaison, le module développé prend une dizaine de secondes et propose un affichage plus pratique (affichage des coordonnées de l'évènements ainsi que de l'arborescence complète au lieu de seulement le nom de la carte)
- En plus d'être lent, le parcours n'affiche aucune barre de progression et a tendance à freezer le logiciel jusqu'à que la recherche soit finie.
- Une pauvreté des outils disponibles (impossible de chercher une chaîne de texte dans un dialogue, impossible de chercher seulement les affectations à une variable …)


Une structure est proposée afin de pouvoir facilement créer de nouvelles opérations de recherche.

Le principe étant qu'un exécuteur d'instructions est crée pour chaque évènement commun / chaque page dans un évènement dans une carte, et chaque exécuteur peut noter ce qu'il a trouvé.

Les outils proposés par les bibliothèques `JavaLCFReader` et `RMDechiffreur` rendent trivial le parcours des cartes.


#### Chercheur de magasins

Dans le jeu ciblé par ce logiciel, les magasins ont été développés grâce au système d'évènements.

Il s'agit là de pouvoir chercher les différents personnages tenant des magasins dans toutes les cartes, et de lire dans la carte de gestion des magasins quels sont les objets disponibles.


#### Appel d'évènements

Il s'agit de détecter dans les évènements communs le nombre de fois où un appel à un autre évènement commun apparait.

Ce module a été utilisé pour trouver des appels à un évènement devenu obsolète.

#### Chercheur d'images

Ce module a pour but de lister toutes les images utilisées sur une carte. En effet, on peut afficher un nombre limité d'images (de base 50, pouvant aller jusqu'à 128 selon les modifications appliquées au logiciel). Chaque image ayant un id défini. Lorsque beaucoup d'images sont utilisées, le programmeur peut réutiliser des id d'images, provoquer des bugs d'affichages en jeu. Le but de ce module est d'exposer clairement quels id d'images sont utilisés plusieurs fois pour détecter plus facilement le problème sans vérifier chaque évènement à la main.

#### Détecteur de collisions d'interrupteurs

Dans RPG Maker 2003, la manière la plus simple de créer un coffre est la suivante :
- En Page 1, mettre une image de coffre fermée, et lorsque le joueur interagit avec, lui donner le contenu du coffre et activer un interrupteur
- En Page 2, conditionner l'utilisation de la page par l'interrupteur activé en page 1, et afficher un message du type "Ce coffre est vide." quand le joueur interagit avec.

Les interrupteurs étant globaux dans RPG Maker, chaque coffre a donc son interrupteur. Ce module a pour but de tenter de détecter les coffres qui utiliseraient le même interrupteur.







