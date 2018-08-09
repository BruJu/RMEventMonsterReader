package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;

public class Etage implements Contenu {
	public final Contenant contenant;
	private Map<GroupeDeConditions, Contenant> fils;
	
	private Etage(Contenant contenant) {
		this.contenant = contenant;
		fils = new HashMap<>();
	}
	
	public Etage(Contenant contenant, Map<GroupeDeConditions, Contenant> fils) {
		this.contenant = contenant;
		this.fils = fils;
	}
	
	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		fils.forEach((g, enfant) -> enfant.transformerAlgorithmes(transformation));
	}

	@Override
	public void ajouterUnNiveau(Function<ListAlgo, Etage> transformation) {
		fils.forEach((g, enfant) -> enfant.ajouterUnNiveau(transformation));
	}
	
	
	
	public static class EtageBuilder {
		private Etage etage;
		
		public EtageBuilder(Contenant contenant) {
			etage = new Etage(contenant);
		}
		
		public EtageBuilder ajouter(GroupeDeConditions groupe, Contenant contenant) {
			etage.fils.put(groupe, contenant);
			return this;
		}
		
		public Etage build() {
			return etage;
		}
	}
}
