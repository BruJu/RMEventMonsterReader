package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class Remplacement {
	private final Function<Monstre, String> search;
	private final BiConsumer<Monstre, String> replace;

	public Remplacement(Function<Monstre, String> search, BiConsumer<Monstre, String> replace) {
		this.search = search;
		this.replace = replace;
	}
	
	public Function<Monstre, String> getSearch() {
		return search;
	}

	public BiConsumer<Monstre, String> getReplace() {
		return replace;
	}
	
	public static Remplacement nom() {
		return new Remplacement(m -> m.name, (m, s) -> m.name = s);
	}
	
	public static Remplacement drop() {
		return new Remplacement(m -> m.nomDrop, (m, s) -> m.nomDrop = s);
	}
}
