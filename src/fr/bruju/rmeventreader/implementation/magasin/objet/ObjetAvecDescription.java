package fr.bruju.rmeventreader.implementation.magasin.objet;

/**
 * Représente un objet contenant une description affichée entre parenthèses
 */
public class ObjetAvecDescription extends Objet {
    /** Complément à afficher */
    private final String complement;

    /**
     * Crée un objet
     * @param id ID de l'objet
     * @param nom Nom de l'objet
     * @param complement Complément de texte pour mieux identifier l'objet
     */
    public ObjetAvecDescription(int id, String nom, String complement) {
        super(id, nom);
        this.complement = complement;
    }

    @Override
    public String getString() {
        return super.getString() + " (" + complement + ")";
    }
}
