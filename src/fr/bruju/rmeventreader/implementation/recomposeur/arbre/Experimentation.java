package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.arbre.Etage.EtageBuilder;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Experimentation {
	private Map<String, Map<String, List<Resultat>>> carte = new HashMap<>();

	private BaseDeVariables base;

	public Experimentation(BaseDeVariables base) {
		this.base = base;
	}

	public void add(String nomLanceur, String nomAttaque, Map<Integer, Algorithme> resultat) {
		Map<String, List<Resultat>> attPerso = Utilitaire.Maps.getX(carte, nomLanceur, () -> new HashMap<>());
		attPerso.put(nomAttaque,
				resultat.entrySet().stream()
						.map(entry -> new Resultat(base.getStatistiqueById(entry.getKey()), entry.getValue()))
						.collect(Collectors.toList()));
	}

	public Arbre creerArbre() {
		Contenant c = new Contenant();
		EtageBuilder sommet = new EtageBuilder(c);

		carte.forEach((nomPerso, algorithmes) -> {
			Contenant contenant = new Contenant();
			EtageBuilder attaques = new EtageBuilder(contenant);

			algorithmes.forEach((nomAttaque, algos) -> attaques
					.ajouter(new GroupeDeConditions(new CondChaine(nomAttaque)), new Contenant(algos)));

			sommet.ajouter(new GroupeDeConditions(new CondChaine(nomPerso)), contenant);
		});
		
		
		String[] etages = {"Lanceur", "Attaque"};
		
		return new Arbre(Arrays.asList(etages), c, base);
	}
}
