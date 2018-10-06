package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre.Etage.EtageBuilder;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Permet de monter un arbre
 * 
 * @author Bruju
 *
 */
public class MonteurDArbre {
	/** Association Personnage - Attaque - Liste des résultats */
	private Map<String, Map<String, List<Resultat>>> carte = new HashMap<>();

	/** Base de variables */
	private BaseDeVariables base;

	/**
	 * Construit un monteur d'arbre pour décrypter les attaques en lien avec la base donnée
	 * @param base La base de variables
	 */
	public MonteurDArbre(BaseDeVariables base) {
		this.base = base;
	}

	/**
	 * Ajoute une branche concernant l'attaque donnée
	 * @param nomLanceur Le nom du lanceur
	 * @param nomAttaque Le nom de l'attaque
	 * @param resultat La carte associant numéro de variable touchée et algorithme appliqué
	 * @return this
	 */
	public MonteurDArbre add(String nomLanceur, String nomAttaque, Map<Integer, Algorithme> resultat) {
		Map<String, List<Resultat>> attPerso = Utilitaire.Maps.getX(carte, nomLanceur, () -> new HashMap<>());
		attPerso.put(nomAttaque,
				resultat.entrySet().stream()
						.map(entry -> new Resultat(base.getStatistiqueById(entry.getKey()), entry.getValue()))
						.collect(Collectors.toList()));
		
		return this;
	}

	/**
	 * Construit la base de l'arbre pour les attaques qui ont été ajoutées au monteur
	 * @return Un arbre gérant toutes les attaques indiquées au monteur
	 */
	public Arbre creerArbre() {
		// Creation du contenant racine
		Contenant c = new Contenant();
		EtageBuilder sommet = new EtageBuilder(c);

		carte.forEach((nomPerso, algorithmes) -> {
			Contenant contenant = new Contenant();
			EtageBuilder attaques = new EtageBuilder(contenant);

			algorithmes.forEach((nomAttaque, algos) -> attaques
					.ajouter(new GroupeDeConditions(new CondChaine(nomAttaque)), new Contenant(algos)));

			sommet.ajouter(new GroupeDeConditions(new CondChaine(nomPerso)), contenant);
		});
		
		// Nom des étages
		List<String> etages = new ArrayList<>();
		etages.add("Lanceur");
		etages.add("Attaque");
		
		return new Arbre(etages, c, base);
	}
}
