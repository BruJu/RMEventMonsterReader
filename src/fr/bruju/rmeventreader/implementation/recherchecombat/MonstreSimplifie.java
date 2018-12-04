package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Contexte;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonstreSimplifie {
	public final int id;
	public final String nom;
	public final int hp;
	public final List<Integer> combatsApparition;
	public final boolean fossilisable;

	public MonstreSimplifie(Monstre monstre) {
		this.id = monstre.getId();
		this.nom = monstre.nom;
		this.hp = monstre.accessInt("HP");
		this.combatsApparition = new ArrayList<>();
		combatsApparition.add(monstre.getBattleId());
		fossilisable = !monstre.accessBool("Fossile");
	}

	public void recevoir(MonstreSimplifie monstre) {
		combatsApparition.addAll(monstre.combatsApparition);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MonstreSimplifie that = (MonstreSimplifie) o;
		return id == that.id &&
				hp == that.hp &&
				fossilisable == that.fossilisable &&
				Objects.equals(nom, that.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom, hp, fossilisable);
	}

	@Override
	public String toString() {
		// TODO : Utiliser StringBuilder
		String description = "[" + id + "] " + nom + " : " + hp + " HP ";

		if (fossilisable) {
			description += "(Fossilisable) ";
		}

		description += " [Combats ";

		for (Integer integer : combatsApparition) {
			description += integer + " ";
		}

		description += "]";

		return description;
	}
}
