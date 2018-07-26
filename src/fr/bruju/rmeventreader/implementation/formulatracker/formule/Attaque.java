package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Attaque {
	public final String nom;

	public Map<ModifStat, List<FormuleDeDegats>> resultat;
	private String chaineAAfficher;

	public Attaque(String nom, Map<ModifStat, List<FormuleDeDegats>> resultat) {
		this.nom = nom;
		this.resultat = resultat;
	}

	public String getChaineAAfficher() {
		return this.chaineAAfficher;
	}

	
	
	
	
	public void detChaine(Function<Attaque, String> detHeader, Function<FormuleDeDegats, String> detChaine) {
		StringBuilder sb = new StringBuilder();
		sb.append(detHeader.apply(this))
		  .append("\n");
		
		resultat.forEach((modifStat, listeDeFormules) ->
			listeDeFormules.stream()
						   .map(formule -> detChaine.apply(formule))
						   .forEach(chaine ->
						   			sb.append(getStatAffichage(modifStat.stat))
								      .append(" ")
								      .append(Utilitaire.getSymbole(modifStat.operateur))
								      .append(" ")
								      .append(chaine)
								      .append("\n"))
						); 
		
		this.chaineAAfficher = sb.toString();
	}
	

	private static String getStatAffichage(Statistique stat) {
		return stat.possesseur.getNom() + "." + stat.nom;
	}

}
