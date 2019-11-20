
# RMEventReader

## Qu'est-ce que c'est ?

RPG Maker 2003 est un logiciel de création de jeux vidéos pour des personnes
qui n'ont pas de connaissance de la programmation.

Les jeux sont organisés par carte, contenant des évènements qui sont composés
d'une liste d'instruction. Ces cartes sont stockées en format binaire.

On souhaite pouvoir en extraire des connaissances. Ce module se repose sur
d'autres sous modules qui ont été développés en parallèle pour lire les fichiers
binaires et faire en sorte que la liste d'instruction déclenche des appels
à des fonctions (via un pseudo design pattern visiteur implémenté par
l'interface ExecuteurDInstructions). Par exemple on peut facilement créer une
classe qui va déterminer la liste de toutes les musiques utilisées, et sur
quelles cartes elles le sont.

Ce projet a donc pour but d'implémenter des `ExecuteurDInstructions` dans des
cas pratiques qui m'ont intéressés, comme extraire la liste des monstres
ainsi que leurs statistiques, objets *droppés* ... d'un jeu précis.



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

Le module de reconnaisance d'image de https://github.com/BruJu/BJUtils/ est
utilisé pour traiter ce problème.

 

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







