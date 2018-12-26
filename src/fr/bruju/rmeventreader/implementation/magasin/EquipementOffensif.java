package fr.bruju.rmeventreader.implementation.magasin;

public class EquipementOffensif extends Objet {
	private final int bonusAttaque;

	public EquipementOffensif(int id, int bonusAttaque) {
		super(id);
		this.bonusAttaque = bonusAttaque;
	}

	@Override
	public String getString() {
		return super.getString() + " (+" + bonusAttaque + " Attaque)";
	}
}
