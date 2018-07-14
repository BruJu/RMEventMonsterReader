
# Objectif du programme

Il s'agit d'une base de code permettant d'interpr�ter des instructions RPG Maker 2003.

## Usage g�n�ral

Dans la mesure o� le jeu de donn�es sur lequel se repose ce programme ne m'appartiennent pas, seul le programme de traitement est fourni.


Les instructions sont � mettre dans des fichiers texte sous la forme g�n�r� par le logiciel [RMEventFactory de Cherry](http://cherrytree.at/cms/lang/en/download/?did=11)

Pour se faire il faut :
* Copier sous RPG Maker 2003 les instructions � reconna�tre
* Lire les �v�nements avec RMEventFactory
* Copier la repr�sentation des �v�nements sous format texte qui est donn�e et l'enregistrer dans un fichier

On peut ensuite afficher les instructions telles qu'elles sont comprises par ce programme avec les instructions

~~~~
ActionMaker printer = new Printer();
Interpreter interpreter = new Interpreter(printer);
interpreter.inputFile(new File(cheminVersLeFichier));
~~~~




## Reconnaissance de lettres

### Probl�me


Les noms des ennemis sont �cris sur des images en orange sur fond transparent. Ils ne sont pas disponibles autrement.

Le package imagereader traite ce probl�me en appliquant une pseudo reconnaissance automatique de lettres.

### imagereader

Le principe est le suivant :
* On analyse l'image qui contient le nom et on extrait une matrice avec vrai � l'endroit o� on estime qu'il y a du texte et faux ailleurs.
* On identifie les motifs qui composent l'image. On dit qu'il y a un motif quand on a une suite de colonnes dans la matrice avec au moins un vrai. On suppose donc que les lettres sont s�par�es par une colonne vide. (Comme ce n'est pas toujours vrai, on permet qu'un motif corresponde � une suite de lettres)
* On estime qu'il y a un espace si deux motifs sont s�par�s de 4 colonnes vides.
* Si le motif n'est pas dans la base de motif, on invite l'utilisateur a ajout� le motif reconnu dans la base de motifs. Sa seule t�che sera de remplacer le ? de la ligne par ce qu'il voit.
* Une fois tous les motifs de l'image vus, le programme restitue une cha�ne qui est l'encha�nement des lettres repr�sent�es par le motif.

### Exemple

Le programme utilise une table de conversion en nom d'image et texte �crit sur l'image. (non fournie)

Si le texte ne lui est pas connu, il essaye de reconna�tre les lettres d�crites sur l'image via une table de correspondance entre motifs et lettre (ou suite de lettre) qu'il poss�de.

Si un motif n'est pas reconnu, un affichage est fait pour signaler que le motif n'existe pas. Le m�me motif n'est pas signal� deux fois.

Dans cet exemple, on suppose que le programme a besoin de la cha�ne contenue dans l'image idmonstre76-2.

~~~~
== Des monstres n'ont pas �t� reconnus ==
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
== Des monstres n'ont pas �t� reconnus ==
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

Une fois tous les motifs ajout�s au fichier motifsconnus en rempla�ant les ? par le symbole correspondant on obtient.

~~~~
== Des monstres n'ont pas �t� reconnus ==
idmonstre76-2, 76, 51, 160, 3, 140, 4550, 288, 237, 162, 206, 237, 262,  

== Identification == 
idmonstre76-2 Assassin

~~~~


 


