package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;

import java.util.ArrayList;
import java.util.Stack;

public interface Element {

	public default String getString() {
		return getString(new Stack<>());
	}

	String getString(Stack<String> categories);

	void simplifier(Simplification simplification);
}
