package fr.bruju.rmeventreader.interfaceutilisateur;

import java.util.Scanner;
import java.util.function.Consumer;

public class Option {
	public final String nom;
	public final Consumer<Scanner> runnable;

	public Option(String nom, Runnable runnable) {
		this.nom = nom;
		this.runnable = scanner -> runnable.run();
	}

	public Option(String nom, Consumer<Scanner> runnable) {
		this.nom = nom;
		this.runnable = runnable;
	}
}
