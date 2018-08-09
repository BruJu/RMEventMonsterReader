package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;

public class Etage implements Contenu {
	public final Contenant contenant;
	public final GroupeDeConditions groupe;
	private List<Contenant> fils;

	public Etage(Contenant contenant, GroupeDeConditions groupe, List<Contenant> fils) {
		this.contenant = contenant;
		this.groupe = groupe;
	}
	
	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		fils.forEach(enfant -> enfant.transformerAlgorithmes(transformation));
	}

	@Override
	public void ajouterUnNiveau(Function<ListAlgo, Etage> transformation) {
		fils.forEach(enfant -> enfant.ajouterUnNiveau(transformation));
	}
}
