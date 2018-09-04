package fr.bruju.rmeventreader.dictionnaires.modele;

/**
 * Un objet composé d'un autre type d'objet
 * 
 * @author Bruju
 *
 * @param <T> Type de l'objet qui compose l'objet
 */
public interface ElementComposite<T> {
	public void ajouter(T t);
}
