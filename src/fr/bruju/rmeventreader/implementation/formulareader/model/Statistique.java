package fr.bruju.rmeventreader.implementation.formulareader.model;

// TODO : ceci est en réalité une ressource
public enum Statistique {
	Niveau,
	HP,
	MP,
	HPMAX,
	MPMAX,
	Force,
	Magie,
	Defense,
	Esprit,
	Dexterite,
	Esquive,
	Fatigue,
	AttaqueArme1,
	AttaqueArme2,
	AttaqueBouclier,
	AttaqueAutre,
	DefenseArmure,
	DefenseMagiqueArmure,
	BonusMagieOff,
	BonusMagieDef,
	BonusPhysique,
	BonusDefensif;
	
	public static Statistique reconnaitre(String string) {
		for (Statistique stat : Statistique.values()) {
			if (stat.toString().equals(string)) {
				return stat;
			}
		}
		throw new RuntimeException("Statistique inconnue " + string);
	}
	
}
