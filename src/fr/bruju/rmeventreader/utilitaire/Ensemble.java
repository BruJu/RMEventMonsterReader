package fr.bruju.rmeventreader.utilitaire;

public class Ensemble {
	private Ensemble() { }
	
	public static int getPosition(int element, int[] elements) {
		for (int i = 0 ; i != elements.length ; i++) {
			if (elements[i] == element) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	public static boolean appartient(int element, int[] elements) {
		return getPosition(element, elements) != -1;
	}
}
