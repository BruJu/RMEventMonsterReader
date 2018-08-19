@echo off
set chemin_jeu=A_REMPLIR
set lcf2xml=A_REMPLIR

cd ressources\xml

for %%X in (%chemin_jeu%\Map*.lmu) do (
%lcf2xml% %%X
)

%lcf2xml% %chemin_jeu%\RPG_RT.ldb
move /Y RPG_RT.xml RPG_RT_DB.xml

%lcf2xml% %chemin_jeu%\RPG_RT.lmt
move /Y RPG_RT.xml RPG_RT_T.xml

cd ..\..\

java -Dfile.encoding=UTF8 -classpath bin\ fr.bruju.rmeventreader.Principal 5
REM java -Dfile.encoding=UTF8 -classpath lib\CollectorBySimilarity.jar;lib\commons-collections4-4.0.jar;bin\ fr.bruju.rmeventreader.Principal 1