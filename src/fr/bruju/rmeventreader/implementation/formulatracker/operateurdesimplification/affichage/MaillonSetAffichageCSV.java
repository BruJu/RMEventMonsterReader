package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.IntegreurGeneral;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.ExtracteurDeConditions;
import fr.bruju.rmeventreader.utilitaire.Pair;


// TODO : cette classe a beaucoup trop de responsabilités (cumulant les rôles de filteurs / séparateur) et de
// déterimnation de l'affichage

/**
 * Maillon produisant un affichage exploitable dans un tableur, avec séparation par des ♦.
 * 
 * @author Bruju
 *
 */
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
		
		
		modifStat.getConditions().stream().map(condition -> condition == null ? "null" : condition.getString())
		.forEach(condition -> ligneCSV.append(condition).append("♦"));
		
		
		ligneCSV.append(formule.getString());
		
		ligneCSV.append("\n");
		
		
		/*
		String header = ligneCSV.toString();
		
		ligneCSV.setLength(0);
		
		remplirStringBuilder(ligneCSV, header, formule);
		*/
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
					.map(paire -> paire.getLeft() + convertirEnChaine(paire.getRight()))
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
		traitements.add(new Variable360());
		traitements.add(new Periode());
		
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
			List<Composant> var360 = new ArrayList<>();
			var360.add(new VBase(360));
			
			List<CVariable> conditions = new ExtracteurDeConditions()
										.extraireSousConditions(paire.getRight(), var360)
										.stream()
										.filter(c -> c instanceof CVariable)
										.map(c -> (CVariable) c)
										.filter(c -> c.operateur == Operator.IDENTIQUE)
										.collect(Collectors.toList());

			

			List<Pair<String, FormuleDeDegats>> resultat;
			if (conditions.isEmpty()) {
				resultat = new ArrayList<>();
				resultat.add(new Pair<>(paire.getLeft()+"♦", paire.getRight()));
			} else {
				resultat = conditions.stream().map(condition -> {
					FormuleDeDegats nouvelleFormule = inclusionGenerale(condition, paire.getRight());
					
					return new Pair<>(paire.getLeft()+condition.droite.getString()+"♦",
							nouvelleFormule);
					
				}).collect(Collectors.toList());				
			}
			
			return resultat;
		}
	}

	private static class Periode implements FonctionDeTransformation {
		@Override
		public List<Pair<String, FormuleDeDegats>> apply(Pair<String, FormuleDeDegats> paire) {
			List<Composant> periodes = new ArrayList<>();
			periodes.add(new BBase(8));
			periodes.add(new BBase(9));
			periodes.add(new BBase(10));
			periodes.add(new BBase(11));
			
			List<CSwitch> conditions = new ExtracteurDeConditions()
										.extraireSousConditions(paire.getRight(), periodes)
										.stream()
										.filter(c -> c instanceof CSwitch)
										.map(c -> (CSwitch) c)
										.filter(c -> c.valeur)
										.collect(Collectors.toList());

			List<Pair<String, FormuleDeDegats>> resultat = new ArrayList<>();
			if (conditions.isEmpty()) {
				resultat = new ArrayList<>();
				resultat.add(new Pair<>(paire.getLeft()+"♦", paire.getRight()));
			} else {
				resultat = new ArrayList<>();
				
				String[] moments = {"Jour", "Soir", "Nuit", "Matin"};
				
				
				for (int i = 8 ; i <= 11 ; i++) {
					IntegreurGeneral ig = new IntegreurGeneral();
					for (int j = 8 ; j <= 11 ; j++)
						ig.ajouterCondition(new CSwitch(new BBase(j), i == j));
					
					
					FormuleDeDegats nouvelleformule = ig.integrer(paire.getRight());
					
					resultat.add(new Pair<>(paire.getLeft()+moments[i - 8]+"♦", nouvelleformule));
				}	
			}
			
			return resultat;
		}
	}
	

	public static FormuleDeDegats inclusionGenerale(Condition condition, FormuleDeDegats right) {
		IntegreurGeneral ig = new IntegreurGeneral();
		ig.ajouterCondition(condition);
		return ig.integrer(right);
	}
	
	
}
