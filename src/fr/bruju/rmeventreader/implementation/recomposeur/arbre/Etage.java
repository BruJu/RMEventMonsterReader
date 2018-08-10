package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

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
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation) {
		fils.forEach((g, enfant) -> enfant.ajouterUnNiveau(transformation));
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return fils.entrySet().stream().flatMap(entry -> 
				entry.getValue().recupererAlgo().map(ancienTriplet ->
						new Triplet<>(
								construireListe(entry.getKey(), ancienTriplet.a),
								ancienTriplet.b, ancienTriplet.c))
				);
	}

	private List<GroupeDeConditions> construireListe(GroupeDeConditions key, List<GroupeDeConditions> a) {
		ArrayList<GroupeDeConditions> liste = new ArrayList<>(a.size() + 1);
		liste.add(key);
		liste.addAll(a);
		return liste;
	}

	/**
	 * Permet de construire un étage "par le bas" en lui ajoutant des contenants fils
	 * 
	 * @author Bruju
	 *
	 */
	public static class EtageBuilder {
		private Etage etage;

		public EtageBuilder(Contenant contenant) {
			etage = new Etage(contenant);
			contenant.transformerContenu(etage);
		}

		public EtageBuilder ajouter(GroupeDeConditions groupe, Contenant contenant) {
			etage.fils.put(groupe, contenant);
			return this;
		}

		public Etage build() {
			return etage;
		}
	}

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		fils.forEach((g, enfant) -> enfant.transformerListes(classifier, unifieur));
	}
}
