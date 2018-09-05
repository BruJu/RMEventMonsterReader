package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtCondition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Vehicule;

public interface Condition {
	public default <T> T appliquerInterrupteur(Function<CondInterrupteur, T> fonction) {
		return null;
	}

	public default <T> T appliquerVariable(Function<CondVariable, T> fonction) {
		return null;
	}

	public default <T> T appliquerPresence(Function<CondHerosDansLEquipe, T> fonction) {
		return null;
	}

	public default <T> T appliquerPossedeHeros(Function<CondHerosPossedeObjet, T> fonction) {
		return null;
	}

	public default <T> T appliquerPossedeSortHeros(Function<CondHerosPossedeSort, T> fonction) {
		return null;
	}

	
	public default <T> T appliquerPossede(Function<CondObjet, T> fonction) {
		return null;
	}
	
	
	
	public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
			Function<CondVariable, T> fonctionVariable,
			Function<CondChrono, T> fonctionChrono,
			Function<CondObjet, T> fonctionObjet,
			Function<CondArgent, T> fonctionArgent,
			Function<CondDirection , T> fonctionDirection,
			Function<CondVehiculeUtilise  , T> fonctionVehicule,
			Function<CondEventDemarreParAppui   , T> fonctionAppui,
			Function<CondMusiqueJoueePlusDUneFois    , T> fonctionMusique,
			Function<CondHerosDansLEquipe     , T> fonctionHerosEquipe,
			Function<CondHerosAPourNom      , T> fonctionHerosNom,
			Function<CondHerosNiveauMin       , T> fonctionHerosNiveauMin,
			Function<CondHerosAAuMoinsHp        , T> fonctionHerosAUnHP ,
			Function<CondHerosPossedeSort         , T> fonctionHerosSort ,
			Function<CondHerosPossedeObjet          , T> fonctionHerosObjet ,
			Function<CondHerosAStatut           , T> fonctionHerosStatut
			);
	
	
	
	public static class CondInterrupteur implements Condition {
		public final int interrupteur;
		public final boolean etat;
		
		public CondInterrupteur(int interrupteur, boolean etat) {
			this.interrupteur = interrupteur;
			this.etat = etat;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonctionInterrupteur == null ? null : fonctionInterrupteur.apply(this);
		}

		@Override
		public <T> T appliquerInterrupteur(Function<CondInterrupteur, T> fonction) {
			return fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.interrupteur(this);
		}
	}
	
	public static class CondVariable implements Condition {
		public final int variable;
		public final Comparateur comparateur;
		public final FixeVariable valeurDroite;
		
		public CondVariable(int variable, Comparateur comparateur, FixeVariable valeurDroite) {
			this.variable = variable;
			this.comparateur = comparateur;
			this.valeurDroite = valeurDroite;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonction, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public <T> T appliquerVariable(Function<CondVariable, T> fonction) {
			return fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.variable(this);
		}
	}
	
	public static class CondChrono implements Condition {
		public final boolean surLePremierChrono;
		public final Comparateur comparateur;
		public final int nombreDeSecondes;

		public CondChrono(boolean surLePremierChrono, Comparateur comparateur, int nombreDeSecondes) {
			this.surLePremierChrono = surLePremierChrono;
			this.comparateur = comparateur;
			this.nombreDeSecondes = nombreDeSecondes;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonction,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.chrono(this);
		}
	}

	public static class CondArgent implements Condition {
		public final Comparateur comparateur;
		public final int argent;
		
		public CondArgent(Comparateur comparateur, int argent) {
			this.comparateur = comparateur;
			this.argent = argent;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonction,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.argent(this);
		}
	}
	
	public static class CondObjet implements Condition {
		public final int idObjet;
		public final boolean estPossede;
		
		public CondObjet(int idObjet, boolean estPossede) {
			this.idObjet = idObjet;
			this.estPossede = estPossede;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonction, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public <T> T appliquerPossede(Function<CondObjet, T> fonction) {
			return fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.objet(this);
		}
	}
	
	
	public static class CondDirection implements Condition {
		public final EvenementDeplacable evenement;
		public final ExecEnum.Direction direction;
		
		public CondDirection(EvenementDeplacable evenement, Direction direction) {
			this.evenement = evenement;
			this.direction = direction;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonction, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.direction(this);
		}
	}
	
	public static class CondVehiculeUtilise implements Condition {
		public final ExecEnum.Vehicule vehicule;
		
		public CondVehiculeUtilise(Vehicule vehicule) {
			this.vehicule = vehicule;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonction,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.vehicule(this);
		}
		
		
	}
	
	
	public static class CondEventDemarreParAppui implements Condition {

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonction,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.eventDemarreParAppui(this);
		}
	}
	
	
	public static class CondMusiqueJoueePlusDUneFois implements Condition {
		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionDPA,
				Function<CondMusiqueJoueePlusDUneFois, T> fonction,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.musiqueABoucle(this);
		}
	}
	

	
	public static class CondHerosDansLEquipe implements Condition {
		public final int idHeros;
		
		public CondHerosDansLEquipe(int idHeros) {
			this.idHeros = idHeros;
		}

		@Override
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonction, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public <T> T appliquerPresence(Function<CondHerosDansLEquipe, T> fonction) {
			return fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosPresent(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonction,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosNomme(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonction,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}
		

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosNiveau(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonction,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosVivant(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonction,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public <T> T appliquerPossedeSortHeros(Function<CondHerosPossedeSort, T> fonction) {
			return fonction.apply(this);
		}

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosSort(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonction,
				Function<CondHerosAStatut, T> fonctionHerosStatut) {
			return fonction == null ? null : fonction.apply(this);
		}

		@Override
		public <T> T appliquerPossedeHeros(Function<CondHerosPossedeObjet, T> fonction) {
			return fonction.apply(this);
		}

		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosObjet(this);
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
		public <T> T appliquer(Function<CondInterrupteur, T> fonctionInterrupteur,
				Function<CondVariable, T> fonctionVariable, Function<CondChrono, T> fonctionChrono,
				Function<CondObjet, T> fonctionObjet, Function<CondArgent, T> fonctionArgent,
				Function<CondDirection, T> fonctionDirection, Function<CondVehiculeUtilise, T> fonctionVehicule,
				Function<CondEventDemarreParAppui, T> fonctionAppui,
				Function<CondMusiqueJoueePlusDUneFois, T> fonctionMusique,
				Function<CondHerosDansLEquipe, T> fonctionHerosEquipe, Function<CondHerosAPourNom, T> fonctionHerosNom,
				Function<CondHerosNiveauMin, T> fonctionHerosNiveauMin,
				Function<CondHerosAAuMoinsHp, T> fonctionHerosAUnHP,
				Function<CondHerosPossedeSort, T> fonctionHerosSort,
				Function<CondHerosPossedeObjet, T> fonctionHerosObjet,
				Function<CondHerosAStatut, T> fonction) {
			return fonction == null ? null : fonction.apply(this);
		}
		
		@Override
		public boolean accept(ExtCondition extCondition) {
			return extCondition.herosStatut(this);
		}
	}


	public boolean accept(ExtCondition extCondition);
}
