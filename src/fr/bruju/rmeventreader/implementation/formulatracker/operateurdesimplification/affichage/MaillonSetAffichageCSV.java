package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
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
		
		
		modifStat.getConditions().forEach(groupe -> {
			ligneCSV.append(groupe.conditions.stream()
					.map(condition -> condition.getString())
					.collect(Collectors.joining(",")));
			
			ligneCSV.append("♦");
		});
		
		ligneCSV.append(formule.getString());
		
		ligneCSV.append("\n");
		
		return ligneCSV.toString();
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
	
	/*
	
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
	*/
}
