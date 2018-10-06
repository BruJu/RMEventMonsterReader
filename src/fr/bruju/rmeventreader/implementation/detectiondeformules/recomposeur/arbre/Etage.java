package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Un étage est un élément pointant vers d'autres contenants. Chaque contenant est étiquetté par un groupe de conditions
 *  
 * @author Bruju
 *
 */
public class Etage implements Contenu {
	/** Le contenant */
	public final Contenant contenant;
	/** La liste des fils */
	private Map<GroupeDeConditions, Contenant> fils;

	/**
	 * Crée un étage
	 * @param contenant Le contenant
	 */
	private Etage(Contenant contenant) {
		this.contenant = contenant;
		fils = new HashMap<>();
	}

	/**
	 * Crée un étage
	 * @param contenant Le contenant
	 * @param fils L'association groupe de conditions - contenants fils
	 */
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

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		fils.forEach((g, enfant) -> enfant.transformerListes(classifier, unifieur));
	}
	
	/**
	 * Crée une liste de groupes de conditions avec la condition donnée et la liste donnée en mettant la condition en
	 * premier 
	 * @param key La première conditions
	 * @param a Les suivantes
	 * @return Une liste de groupes de conditions avec key en premier et a aprés.
	 */
	private List<GroupeDeConditions> construireListe(GroupeDeConditions key, List<GroupeDeConditions> a) {
		ArrayList<GroupeDeConditions> liste = new ArrayList<>(a.size() + 1);
		liste.add(key);
		liste.addAll(a);
		return liste;
	}

	/**
	 * Permet de construire un étage "par le bas" en lui ajoutant des contenants fils au fur et à mesure
	 * 
	 * @author Bruju
	 *
	 */
	public static class EtageBuilder {
		/** Etage construit */
		private Etage etage;

		/** Crée un constructeur d'étage pour le contenant donné */
		public EtageBuilder(Contenant contenant) {
			etage = new Etage(contenant);
			contenant.transformerContenu(etage);
		}
		
		/** Ajoute un fils */
		public EtageBuilder ajouter(GroupeDeConditions groupe, Contenant contenant) {
			etage.fils.put(groupe, contenant);
			return this;
		}

		/** Construit l'étage */
		public Etage build() {
			return etage;
		}
	}
}
