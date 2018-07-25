package fr.bruju.rmeventreader.utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public class Utilitaire {
	public static String getSymbole(Operator operateur) {
		switch (operateur) {
		case AFFECTATION:
			return "←";
		case DIFFERENT:
			return "≠";
		case DIVIDE:
			return "/";
		case IDENTIQUE:
			return "=";
		case INF:
			return "<";
		case INFEGAL:
			return "≤";
		case MINUS:
			return "-";
		case MODULO:
			return "%";
		case PLUS:
			return "+";
		case SUP:
			return ">";
		case SUPEGAL:
			return "≥";
		case TIMES:
			return "×";
		default:
			return "??";
		}
	}

	public static int getPriorite(Operator operateur) {
		switch (operateur) {
		case MODULO:
			return 10;
		case TIMES:
		case DIVIDE:
			return 9;
		case MINUS:
		case PLUS:
			return 8;
		case AFFECTATION:
			return 7;
		case DIFFERENT:
		case IDENTIQUE:
			return 6;
		case INF:
			return 5;
		case INFEGAL:
			return 5;
		case SUP:
			return 5;
		case SUPEGAL:
			return 5;
		default:
			return Integer.MAX_VALUE;
		}
	}
	
	
	public static <K, V> void mapAjouterElementAListe(Map<K, List<V>> map, K cle, V element) {
		List<V> liste = map.get(cle);
		if (liste == null) {
			liste = new ArrayList<V>();
			liste.add(element);
			map.put(cle, liste);
		} else {
			liste.add(element);
		}
	}
	
	
	public static <T> List<T> fusionnerJusquaStabilite(List<T> listeDeBase, BinaryOperator<T> fonctionFusion) {
		List<T> base;
		List<T> transformee = listeDeBase;
		boolean stable = false;
		
		while (!stable) {
			stable = true;
			base = transformee;
			transformee = new ArrayList<>();
			
			boucleorig:
			for (int i = 0 ; i != base.size(); i++) {
				T p = base.get(i);
				
				for (int j = i+1 ; j!= base.size() ; j++) {
					T s = base.get(j);
					
					T u = fonctionFusion.apply(p, s);
					
					if (u != null) {
						stable = false;
						base.remove(s);
						transformee.add(u);
						continue boucleorig;
					} else {
						System.out.println("Echec pour unifier " + i + " et " + j);
						
					}
				}
				
				transformee.add(p);
			}
		}

		return transformee;
	}
}
