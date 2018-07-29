package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class MaillonSetAffichageCSV implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.determinerAffichageAttaques((nom) -> "", this::determinerAffichage, nom -> "");
	}

	
	// Format :
// NomPerso / NomAttaque / Perso touché / Stat touchée / Opérateur / Var 360 / Période / Armes / Préconditions / Formule
	
	private String determinerAffichage(String nomAttaque, ModifStat modifStat, FormuleDeDegats formule) {
		StringBuilder ligneCSV = new StringBuilder();
		
		// NomPerso ♦ Nom Attaque ♦
		String[] decompNomAttaque = nomAttaque.split("-");
		ligneCSV.append(decompNomAttaque[0])
				.append("♦")
				.append(decompNomAttaque[1])
				.append("♦");

		// Cible ♦ Statistique ♦ Opérateur ♦
		ligneCSV.append(modifStat.stat.possesseur.getNom())
				.append("♦")
				.append(modifStat.stat.nom)
				.append("♦")
				.append(getAffichage(modifStat.operateur))
				.append("♦");
		
		
		String header = ligneCSV.toString();
		
		ligneCSV.setLength(0);
		
		remplirStringBuilder(ligneCSV, header, formule);
		
		return ligneCSV.toString();
	}


	private void remplirStringBuilder(StringBuilder ligneCSV, String header, FormuleDeDegats formule) {
		List<String> affichagesIndividuelsFormules = getAffichagesIndividuels(formule);
		
		affichagesIndividuelsFormules.forEach(c -> ligneCSV.append(header).append(c).append("\n"));
	}


	private List<String> getAffichagesIndividuels(FormuleDeDegats formule) {
		List<Pair<String, FormuleDeDegats>> liste = new ArrayList<>();
		liste.add(new Pair<>("", formule));
		
		liste = prefixer(liste);
		
		return liste.stream()
					.map(paire -> paire.getLeft() + convertirEnChaine(formule))
					.collect(Collectors.toList());
	}
	
	


	private String convertirEnChaine(FormuleDeDegats formuleDG) {
		String conditions = formuleDG.conditions.stream().map(condition -> condition.getString())
					.collect(Collectors.joining(" "));
		
		String formule = formuleDG.formule.getString();
		
		return conditions + "♦" + formule;
	}


	private List<Pair<String, FormuleDeDegats>> prefixer(List<Pair<String, FormuleDeDegats>> liste) {
		List<FonctionDeTransformation> traitements = new ArrayList<>();
		
		List<Pair<String, FormuleDeDegats>> courant = liste;
		
		for (FonctionDeTransformation traitement : traitements) {
			courant = courant.stream().flatMap(paire -> traitement.apply(paire).stream())
					.collect(Collectors.toList());
		}
		
		return courant;
	}


	private String getAffichage(Operator operateur) {
		switch (operateur) {
		case AFFECTATION:
			return "FIXER";
		case DIVIDE:
			return "DIVISER";
		case MINUS:
			return "DEGATS";
		case MODULO:
			return "MODULO";
		case PLUS:
			return "SOIN";
		case TIMES:
			return "FOIS";
		default:
			return "";
		}
	}
	
	private static interface FonctionDeTransformation {
		List<Pair<String, FormuleDeDegats>> apply(Pair<String, FormuleDeDegats> paire);
	}
	
	
	private static class Variable360 implements FonctionDeTransformation {

		@Override
		public List<Pair<String, FormuleDeDegats>> apply(Pair<String, FormuleDeDegats> paire) {
			
			
			return null;
		}
		
		
	}
	
	
}
