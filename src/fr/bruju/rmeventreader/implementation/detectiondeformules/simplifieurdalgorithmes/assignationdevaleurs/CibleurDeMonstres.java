package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Separateur;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CibleurDeMonstres implements Separateur {
	@Override
	public void separer(Consumer<AlgorithmeEtiquete> fonctionDAjout, AlgorithmeEtiquete elementASeparer) {
		fonctionDAjout.accept(creerAttaqueCiblee(elementASeparer, 0));
		fonctionDAjout.accept(creerAttaqueCiblee(elementASeparer, 1));
		fonctionDAjout.accept(creerAttaqueCiblee(elementASeparer, 2));
	}

	private AlgorithmeEtiquete creerAttaqueCiblee(AlgorithmeEtiquete elementASeparer, int idMonstre) {
		Map<Integer, Integer> variables = new HashMap<>();

		variables.put(436, 5 + idMonstre);
		variables.put(42, 70 + idMonstre);

		AssignationDeValeurs assignateur = new AssignationDeValeurs();
		Algorithme nouvelAlgorithme = assignateur.assigner(elementASeparer.getAlgorithme(), variables);

		Classificateur classificateur = new Classificateur.ClassificateurChaine("" +(1 + idMonstre));
		return new AlgorithmeEtiquete(elementASeparer, classificateur, nouvelAlgorithme);
	}
}
