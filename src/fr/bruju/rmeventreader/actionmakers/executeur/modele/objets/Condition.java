package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Vehicule;

public interface Condition {
	public static interface Visiteur<T> {
		public default T visit(Condition condition) {
			return condition.accept(this);
		}

		public default T visit(CondArgent condition) {return null;}
		public default T visit(CondChrono condition) {return null;}
		public default T visit(CondDirection condition) {return null;}
		public default T visit(CondEventDemarreParAppui condition) {return null;}
		public default T visit(CondHerosAPourNom condition) {return null;}
		public default T visit(CondHerosAStatut condition) {return null;}
		public default T visit(CondHerosDansLEquipe  condition) {return null;}
		public default T visit(CondHerosNiveauMin condition) {return null;}
		public default T visit(CondHerosPossedeObjet condition) {return null;}
		public default T visit(CondHerosPossedeSort condition) {return null;}
		public default T visit(CondInterrupteur condition) {return null;}
		public default T visit(CondMusiqueJoueePlusDUneFois condition) {return null;}
		public default T visit(CondObjet condition) {return null;}
		public default T visit(CondVariable condition) {return null;}
		public default T visit(CondVehiculeUtilise condition) {return null;}
	}

	public <T> T accept(Visiteur<T> visiteur);
	
	public static class CondInterrupteur implements Condition {
		public final int interrupteur;
		public final boolean etat;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondInterrupteur(int interrupteur, boolean etat) {
			this.interrupteur = interrupteur;
			this.etat = etat;
		}
	}
	
	public static class CondVariable implements Condition {
		public final int variable;
		public final Comparateur comparateur;
		public final FixeVariable valeurDroite;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondVariable(int variable, Comparateur comparateur, FixeVariable valeurDroite) {
			this.variable = variable;
			this.comparateur = comparateur;
			this.valeurDroite = valeurDroite;
		}
		
		
		
		
	}
	
	public static class CondChrono implements Condition {
		public final boolean surLePremierChrono;
		public final Comparateur comparateur;
		public final int nombreDeSecondes;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondChrono(boolean surLePremierChrono, Comparateur comparateur, int nombreDeSecondes) {
			this.surLePremierChrono = surLePremierChrono;
			this.comparateur = comparateur;
			this.nombreDeSecondes = nombreDeSecondes;
		}
		
		
		
	}

	public static class CondArgent implements Condition {
		public final Comparateur comparateur;
		public final int argent;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondArgent(Comparateur comparateur, int argent) {
			this.comparateur = comparateur;
			this.argent = argent;
		}
	}
	
	public static class CondObjet implements Condition {
		public final int idObjet;
		public final boolean estPossede;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondObjet(int idObjet, boolean estPossede) {
			this.idObjet = idObjet;
			this.estPossede = estPossede;
		}
	}
	
	
	public static class CondDirection implements Condition {
		public final EvenementDeplacable evenement;
		public final ExecEnum.Direction direction;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		public CondDirection(EvenementDeplacable evenement, Direction direction) {
			this.evenement = evenement;
			this.direction = direction;
		}
		
		
	}
	
	public static class CondVehiculeUtilise implements Condition {
		public final ExecEnum.Vehicule vehicule;
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}

		public CondVehiculeUtilise(Vehicule vehicule) {
			this.vehicule = vehicule;
		}
		
		
		
	}
	
	
	public static class CondEventDemarreParAppui implements Condition {

		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
		
	}
	
	
	public static class CondMusiqueJoueePlusDUneFois implements Condition {
		

		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	

	
	public static class CondHerosDansLEquipe implements Condition {
		public final int idHeros;
		
		

		public CondHerosDansLEquipe(int idHeros) {
			this.idHeros = idHeros;
		}



		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	
	
	

	public static class CondHerosAPourNom implements Condition {
		public final int idHeros;
		public final String nom;
		
		

		public CondHerosAPourNom(int idHeros, String nom) {
			this.idHeros = idHeros;
			this.nom = nom;
		}



		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	

	public static class CondHerosNiveauMin implements Condition {
		public final int idHeros;
		public final int niveau;
		
		
		

		public CondHerosNiveauMin(int idHeros, int niveau) {
			this.idHeros = idHeros;
			this.niveau = niveau;
		}




		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	

	public static class CondHerosAAuMoinsHp implements Condition {
		public final int idHeros;
		public final int hp;
		
		
		
		

		public CondHerosAAuMoinsHp(int idHeros, int hp) {
			this.idHeros = idHeros;
			this.hp = hp;
		}





		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	

	public static class CondHerosPossedeSort implements Condition {
		public final int idHeros;
		public final int idSort;
		
		
		

		public CondHerosPossedeSort(int idHeros, int idSort) {
			this.idHeros = idHeros;
			this.idSort = idSort;
		}




		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	

	public static class CondHerosPossedeObjet implements Condition {
		public final int idHeros;
		public final int idObjet;
		
		
		

		public CondHerosPossedeObjet(int idHeros, int idObjet) {
			this.idHeros = idHeros;
			this.idObjet = idObjet;
		}




		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	

	public static class CondHerosAStatut implements Condition {
		public final int idHeros;
		public final int statut;
		
		
		
		

		public CondHerosAStatut(int idHeros, int statut) {
			this.idHeros = idHeros;
			this.statut = statut;
		}





		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
		
	}
	
	
	
	
	
}
