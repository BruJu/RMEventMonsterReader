package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.PersonnageReel;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public class Header {
	public final PersonnageReel lanceur;
	public final String nomAttaque;
	public final Statistique statistiqueTouchee;
	public final List<GroupeDeConditions> groupes;

	public Header(String nomLanceur, String nomAttaque, BaseDeVariables base) {
		this.lanceur = base.getPersonnage(nomLanceur);
		this.nomAttaque = nomAttaque;
		this.statistiqueTouchee = null;
		this.groupes = new ArrayList<>();
	}

	public Header(Header head, Statistique statistiqueTouchee) {
		this.lanceur = head.lanceur;
		this.nomAttaque = head.nomAttaque;
		this.statistiqueTouchee = statistiqueTouchee;
		this.groupes = head.groupes;
	}

	public Header(Header header, GroupeDeConditions nouveauGroupe) {
		this.lanceur = header.lanceur;
		this.nomAttaque = header.nomAttaque;
		this.statistiqueTouchee = header.statistiqueTouchee;
		
		this.groupes = new ArrayList<>();
		header.groupes.forEach(groupes::add);
		header.groupes.add(nouveauGroupe);
	}

	@Override
	public String toString() {
		return lanceur.getNom() + "." + nomAttaque + " • " + statistiqueTouchee.toString() + " • " +
	groupes.stream().map(groupe -> groupe.toString()).collect(Collectors.joining(" ; "));
	}
	public int hashUnifiable() {
		return Objects.hash(lanceur, nomAttaque, statistiqueTouchee.possesseur);
	}

	public boolean estUnifiable(Header left) {
		return Objects.equals(lanceur, left.lanceur)
				&& Objects.equals(statistiqueTouchee.possesseur, left.statistiqueTouchee.possesseur)
				&& Objects.equals(nomAttaque, left.nomAttaque);
	}

}
