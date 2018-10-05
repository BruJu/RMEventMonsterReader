package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.IdentiteAttaque;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;


/**
 * Maillon produisant un affichage exploitable dans un tableur, avec séparation par des ♦.
 * 
 * @author Bruju
 *
 */
public class MaillonSetAffichageCSV implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.determinerAffichageAttaques(
				this::affichageHeader,
				(nom) -> "",
				this::determinerAffichage,
				nom -> "");
	}
	
	private String affichageHeader(List<String> groupes) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Personnage♦Attaque♦Cible♦Statistique♦Opérateur♦");
		
		groupes.forEach(s -> sb.append(s).append("♦"));
		
		sb.append("Condition♦Formule\n");
		
		return sb.toString();
	}
	
	private String determinerAffichage(IdentiteAttaque nomAttaque, ModifStat modifStat, FormuleDeDegats formule) {
		StringBuilder ligneCSV = new StringBuilder();
		
		// NomPerso ♦ Nom Attaque ♦
		ligneCSV.append(nomAttaque.nomLanceur)
				.append("♦")
				.append(nomAttaque.nomAttaque)
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
					.map(condition -> condition.appliquerAffichage())
					.collect(Collectors.joining(",")));
			
			ligneCSV.append("♦");
		});
		
		ligneCSV.append(
		formule.conditions.stream().map(condition -> condition.getString()).collect(Collectors.joining(","))
		).append("♦")
		.append(formule.formule.getString());
		
		
		ligneCSV.append("\n");
		
		return ligneCSV.toString();
	}

	private String getAffichage(OpMathematique operateur) {
		switch (operateur) {
		case AFFECTATION:
			return "FIXER";
		case DIVISE:
			return "DIVISER";
		case MOINS:
			return "DEGATS";
		case MODULO:
			return "MODULO";
		case PLUS:
			return "SOIN";
		case FOIS:
			return "FOIS";
		default:
			return "";
		}
	}
}
