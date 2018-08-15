package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;

public interface Fonction<T, R> {
	public R apply(T t) throws ObjetNonSupporte;
}
