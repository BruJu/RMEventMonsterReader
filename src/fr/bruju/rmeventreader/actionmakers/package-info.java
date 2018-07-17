/**
 * Ce package sert de librairie pour la lecture d'instructions RPG Maker 2003 sous la forme fournie par le logiciel
 * RMEventFactory de Cherry.
 * <p>
 * Les utilisateurs sont invit�s � impl�menter l'interface ActionMaker ou ActionMakerDefalse, puis � utiliser la classe
 * Interpreter pour faire le lien entre lignes lues et les actions d�clench�es par l'interface.
 * <p>
 * Requis : Ce package a besoin du package fr.bruju.rmeventreader.filereader pour fonctionner.
 * <p>
 * Usage :
 * <code>
 * ActionMaker monActionMaker = new MonActionMaker();
 * new Interpreter(monActionMaker).inputFile("monFichier.txt");
 * </code>
 * 
 * @author Bruju
 * 
 * 
 */
package fr.bruju.rmeventreader.actionmakers;