@echo off
set chemin_jeu=A_REMPLIR
set lcf2xml=A_REMPLIR

cd ressources\xml

rem for %%X in (%chemin_jeu%\Map*.lmu) do (
rem %lcf2xml% %%X
rem )

rem for %%X in (%chemin_jeu%\Map*.xml) do (
rem move %%X ressources\xml /y
rem )

%lcf2xml% %chemin_jeu%\RPG_RT.ldb
move /Y RPG_RT.xml RPG_RT_DB.xml

%lcf2xml% %chemin_jeu%\RPG_RT.lmt
move /Y RPG_RT.xml RPG_RT_T.xml

cd ..\..\

java -classpath bin\ fr.bruju.rmeventreader.Principal 5
java -classpath bin\ fr.bruju.rmeventreader.Principal 6