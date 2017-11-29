package com.bj.perso.rmeventreader.donnees;

import utility.Ternaire;

public interface Condition {

	void add(Catalogue catalogueIf, Catalogue catalogueElse, Enregistrement enregistrement);

	Ternaire tester(Enregistrement enresgitrement);

	Ternaire tester(SousEnregistrement enresgitrement);
}
