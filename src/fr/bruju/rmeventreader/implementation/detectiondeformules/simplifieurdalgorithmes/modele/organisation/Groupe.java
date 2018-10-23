package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Groupe<Classifieur> implements Element {
	private final String nom;
	private final Map<Classifieur, Element> elements;

	public Groupe(String nom, Map<Classifieur, Element> elements) {
		this.nom = nom;
		this.elements = elements;
	}

	public void ajouter(Classifieur classifieur, Element element) {
		elements.put(classifieur, element);
	}

	@Override
	public String getString(Stack<String> categories) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<Classifieur, Element> classifieur : elements.entrySet()) {
			System.out.println("CLASSIFIEUR : " + classifieur.toString());
			categories.push(classifieur.getKey().toString());
			sb.append(classifieur.getValue().getString(categories));
			categories.pop();
		}

		return sb.toString();
	}

	@Override
	public void simplifier(Simplification simplification) {
		for (Element element : elements.values()) {
			element.simplifier(simplification);
		}
	}
}
