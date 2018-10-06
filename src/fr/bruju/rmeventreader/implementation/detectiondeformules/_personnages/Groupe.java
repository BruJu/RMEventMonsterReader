package fr.bruju.rmeventreader.implementation.detectiondeformules._personnages;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Groupe<T extends VariablesAssociees> implements Personne<T> {
	
	private final String nom;
	protected final Set<Individu<T>> individusRepresentes;
	protected final T variablesAssociees;
	
	
	public Groupe(Set<Individu<T>> individus, Function<Groupe<T>, T> variablesAssociees) {
		individusRepresentes = individus;
		this.variablesAssociees = variablesAssociees.apply(this);
		this.nom = deduireNom(individus);
	}

	private String deduireNom(Set<Individu<T>> individus) {
		StringBuilder sb = new StringBuilder();

		if (commencentTousPar(individus, "Monstre")) {
			sb.append("Monstre[");

			individus.stream().map(p -> p.getNom().substring(7, p.getNom().length())).sorted()
					.forEach(numero -> sb.append(numero));

			sb.append("]");
		} else {
			sb.append(individus.stream().map(p -> p.getNom()).collect(Collectors.joining("/")));
		}
		
		if (sb.toString().equals("Monstre[123]")) {
			return "Monstre";
		}
		if (sb.toString().equals("Irzyka/Membre2/Membre3/Membre4")) {
			return "Allié";
		}
		if (sb.toString().equals("Membre2/Membre3/Membre4")) {
			return "Allié";
		}

		return sb.toString();
	}
	
	/**
	 * Renvoie vrai si tous les personnages de l'ensemble commencent par le mot début
	 */
	private static <T extends VariablesAssociees> boolean commencentTousPar(Set<Individu<T>> personnages,
			String debut) {
		for (Individu<T> personnage : personnages) {
			if (!personnage.getNom().startsWith(debut)) {
				return false;
			}
		}

		return true;
	}
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public T getVariablesAssociees() {
		return variablesAssociees;
	}

	@Override
	public Set<Individu<T>> getIndividus() {
		return individusRepresentes;
	}

}
