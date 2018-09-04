package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.PaireIDString;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.BoucleTraitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.TraitementObjet;

public class Evenement implements ElementComposite<Page> {
	public final int id;
	public final String nom;
	public final int x;
	public final int y;
	public final List<Page> pages;

	public Evenement(int id, String nom, int x, int y) {
		this.id = id;
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.pages = new ArrayList<>();
	}

	
	public void append(StringBuilder sb) {
		sb.append("-- EVENT --\n")
		  .append("ID ").append(id).append("\n")
		  .append("Nom ").append(nom).append("\n")
		  .append("Position ").append(x).append(" ").append(y).append("\n")
		  .append("\n");
	}

	@Override
	public void ajouter(Page t) {
		this.pages.add(t);
	}
	
	/**
	 * Renvoie vrai si cet évènement n'est pas une page simple qui n'a aucune instruction et aucune condition
	 */
	public boolean estInteressant() {
		return !(pages.size() == 1 && pages.get(0).conditions.size() == 0 && pages.get(0).instructions.size() == 0);
	}
	


	@SuppressWarnings("unchecked")
	public static ConvertisseurLigneVersObjet<Evenement, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
				new TraitementObjet<Builder, MapRM>(MapRM.sousObjet(), ConvertisseurLigneVersObjet::throwAway),
				new LigneAttendue<>("-- EVENT --"),
				new TableauInt<Builder>("ID", (m, t) -> m.setId(t[0])),
				new PaireIDString<Builder>("Nom", (m, s) -> m.setNom(s)),
				new TableauInt<Builder>("Position", (m, t) -> m.setX(t[0]).setY(t[1])),
				new BoucleTraitement<Builder>(() -> 
				 new TraitementObjet<Builder, Page>(Page.sousObjet(), (m, p) -> m.ajouterPage(p))
				)
		});
	}
	
	public static class Builder implements Monteur<Evenement> {
		public int id;
		public String nom;
		public int x;
		public int y;
		
		private Evenement evenement;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}

		public Builder setNom(String nom) {
			this.nom = nom;
			return this;
		}
		
		public Builder setX(int x) {
			this.x = x;
			return this;
		}
		
		public Builder setY(int y) {
			this.y = y;
			return this;
		}
		
		public Builder ajouterPage(Page page) {
			if (evenement == null) {
				evenement = new Evenement(id, nom, x, y);
			}
			
			evenement.ajouter(page);
			
			return this;
		}

		@Override
		public Evenement build() {
			return evenement;
		}
	}

	public static Evenement creerEvenementSimple(int idEvent, String nom, int x, int y) {
		Evenement evenementSimple = new Evenement(idEvent, nom, x, y);
		evenementSimple.pages.add(Page.creerPageSimple());
		return evenementSimple;
	}

	
	
}