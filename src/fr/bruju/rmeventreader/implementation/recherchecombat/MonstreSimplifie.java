package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Une version simplifiée des monstres ne contenant que leur ID, leur nom, leur nombre de points de vie, si ils sont
 * fossilisables et la liste des combats où ils apparaissent.
 * <br>Cette classe redéfini equals et hashCode pour ignorer la liste des combats d'apparitions. (Donc si deux monstres
 * ont des statistiques identiques mais apparaissent dans des combats différents, ils sont considérés comme égaux).
 */
public class MonstreSimplifie {
	/** ID du monstre */
	public final int id;
	/** Nom du monstre */
	public final String nom;
	/** HP du monstre */
	public final int hp;
	/** Vrai si le monstre n'est pas immunisé à la fossilisation et autres statuts du jeu */
	public final boolean fossilisable;
	/** La liste des id de combats où le monstre apparait (non utilisé pour définir hashCode et equals) */
	public final List<Integer> combatsApparition;

	/**
	 * Crée une représentation simplifiée du monstre donné
	 * @param monstre Le monstre
	 */
	public MonstreSimplifie(Monstre monstre) {
		this.id = monstre.getId();
		this.nom = monstre.nom;
		this.hp = monstre.accessInt("HP");
		this.combatsApparition = new ArrayList<>();
		combatsApparition.add(monstre.getBattleId());
		fossilisable = !monstre.accessBool("Fossile");
	}

	/**
	 * Ajoute tous les ID de combats du monstre donné à ce monstre
	 * @param monstre Le monstre dont on reprend les ID de combats
	 */
	public void copierIDDeCombats(MonstreSimplifie monstre) {
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
		StringBuilder sb = new StringBuilder();

		sb.append("[")
			.append(id)
			.append("]")
			.append(nom)
			.append(" : " )
			.append(hp)
			.append(" HP ");

		if (fossilisable) {
			sb.append("(Fossilisable) ");
		}

		sb.append(" [Combats");

		for (Integer integer : combatsApparition) {
			sb.append(" ").append(integer);
		}

		sb.append("]");

		return sb.toString();
	}
}
