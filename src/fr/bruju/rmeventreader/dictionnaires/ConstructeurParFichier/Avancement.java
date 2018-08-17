package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

public enum Avancement {
	/** A fini son traitement */
	Suivant,
	/** N'est pas concerné par la ligne. Demande au suivant de traiter la ligne */
	SuivantDirect,
	/** Demande à recevoir la ligne suivante */
	Rester,
	/** Fichier non conforme à ce qui était attendu : dead end*/
	Tuer,
	/** Comment suivant et comme suivantdirect (quoi ?) */
	FinTraitement
	
	;
}
