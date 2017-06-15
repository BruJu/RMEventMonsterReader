# RMEventMonsterReader

Suivant de prêt un RPG étant fait sur RPG Maker, il m'interessait de pouvoir repertorier de manière simple la liste des monstres de ce jeu.
Les données des monstres sont néanmoins d'une part nombreuses, et eparpillées. Les instructions pour paramétrer les monstres étant des lignes du code du type « Mettre les points de vie du monstre 1 à 500 » « Mettre la force du monstre 1 à 50 ».

De manière plus formelle, RPG Maker propose 5000 variables (et 5000 booléens) utilisables par l'utilisateur à loisir. Dans certains jeux RPG Maker avancés, le système de combat se repose sur le fait de modifier ses variables, au lieu d'utiliser le système de combat pré établi.

Le but de l'interpréteur est donc de pouvoir lui donner les lignes de code du jeu, et extraire sous forme de tableau la liste de tous les monstres.

J'avais fait une première version de ce programme, mais elle était peu maintenable et peu malléable. Le but de cette nouvelle version est donc de penser à faire quelque chose de correct.


Caractéristiques :
L'idée serait de réutiliser les notions vues en cours de Compilation en établissant un analyseur lexical et un analyseur syntaxique.
L'analyseur lexical lirait les lignes, les reconnaitrait et en extrairait les données interessantes.
L'analyseur syntaxique lui constiturait petit à petit le tableau.

Pour gérer la dispersion de fichiers, l'analyse des différents fichiers pourrait se faire en deux mode.
Le premier serait le mode insertion, où on insère den ouvelles lignes dans le tableau
Le second est le mode modification, qui met à jour selon les clés et les conditions lues. La notion de clé n'est néanmoins pas obligatoire.
