package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import java.util.List;

public class Groupe implements Element {
	private final Classifieur classifieur;
	private List<Element> fils;

	public Groupe(Classifieur classifieur, List<Element> fils) {
		this.classifieur = classifieur;
		this.fils = fils;
	}



}
