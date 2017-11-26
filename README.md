Cette application est destinée à pouvoir décrypter des instructions du logiciel RPG Maker 2003 pour tirer des données depuis les suites d'évènements.


RPG Maker permet basiquement d'avoir 5000 variables, 5000 booléens, d'afficher des images et d'ajouter des objets dans l'inventaire du joueur. (le tout avec des conditions possibles).

Exemple d'instructions (simplifiées) :

si Variable49 = 0

Variable50 = 5;

Variable51 = 6;

fin si

si variable 49 = 1

Variable 50 = 6

Variable 51 = 6

fin si


L'idée serait de pouvoir extraire un tableau récapitulant les données. Ici :

variable 49 | variable 50 | variable 51

0 | 5 | 6

0 | 6 | 6

 
 

L'utilisateur doit pouvoir spécifier quelles valeurs servent d'id, quelles valeurs servent de sous id, et quelles valeurs sont liées à ces id ou sous id.


Confiugration :

L'utilisateur doit pouvoir s'en sortir en mettant simplement dans un dossier tous les fichiers de script qu'il souhaite analyser, dans un autre la liste des variables à surveiller, dans un autre la correspondance entre id d'objet et valeur, dans un autre la correspodnant entre nom d'image et valeur.


Contraintes :

L'utilitaire doit être simple d'utilisation, simple à configurer, et le code doit être lisible

Il n'y a pas de contraintes d'efficacité. (il n'est pas génant que le programme tourne pendant 2 minutes pour analyser 5000 lignes).