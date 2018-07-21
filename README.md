
# Objectif du programme

Il s'agit d'une base de code permettant d'interpréter des instructions RPG Maker 2003.

## Usage général

Dans la mesure où le jeu de données sur lequel se repose ce programme ne m'appartiennent pas, seul le programme de traitement est fourni.


Les instructions sont à mettre dans des fichiers texte sous la forme généré par le logiciel [RMEventFactory de Cherry](http://cherrytree.at/cms/lang/en/download/?did=11)

Pour ce faire il faut :
* Copier sous RPG Maker 2003 les instructions à reconnaître
* Lire les évènements avec RMEventFactory
* Copier la représentation des évènements sous format texte qui est donnée et l'enregistrer dans un fichier

On peut ensuite afficher les instructions telles qu'elles sont comprises par ce programme avec les instructions

~~~~
ActionMaker printer = new Printer();
Interpreter interpreter = new Interpreter(printer);
interpreter.inputFile(new File(cheminVersLeFichier));
~~~~


## Dépendances

### CollectorsSimilaire

Ce projet a une dépendance à ![CollectorsSimilaire](https://github.com/BruJu/CollectorsSimilaire/releases/tag/Chaton) afin de gérer plus facilement le regroupement d'éléments similaires.


## Reconnaissance de lettres

### Problème


Les noms des ennemis sont écris sur des images en orange sur fond transparent. Ils ne sont pas disponibles autrement.

Le package imagereader traite ce problème en appliquant une pseudo reconnaissance automatique de lettres.

### imagereader

Le principe est le suivant :
* On analyse l'image qui contient le nom et on extrait une matrice avec vrai à l'endroit où on estime qu'il y a du texte et faux ailleurs.
* On identifie les motifs qui composent l'image. On dit qu'il y a un motif quand on a une suite de colonnes dans la matrice avec au moins un vrai. On suppose donc que les lettres sont séparées par une colonne vide. (Comme ce n'est pas toujours vrai, on permet qu'un motif corresponde à une suite de lettres)
* On estime qu'il y a un espace si deux motifs sont séparés de 4 colonnes vides.
* Si le motif n'est pas dans la base de motif, on invite l'utilisateur a ajouté le motif reconnu dans la base de motifs. Sa seule tâche sera de remplacer le ? de la ligne par ce qu'il voit.
* Une fois tous les motifs de l'image vus, le programme restitue une chaîne qui est l'enchaînement des lettres représentées par le motif.

### Exemple

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


 

## Calcul de formules de dégâts

### Objectif

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


### Solution

Implémentation en cours
 
 


