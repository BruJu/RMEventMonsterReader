package fr.bruju.rmeventreader.utilitaire;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public class Utilitaire {
	
	/**
	 * Fourni des fonctions afin de traiter un tableau comme étant une pile
	 * 
	 * @author Bruju
	 *
	 */
	public static class Pile {
		/**
		 * Donne le dernier élément de la liste
		 * @param tableau La liste
		 * @return Le dernier élément
		 */
		public static <T> T peek(List<T> tableau) {
			if (tableau.isEmpty())
				return null;
			
			return tableau.get(tableau.size() - 1);
		}
		
		/**
		 * Dépile le dernier élément de la liste
		 * @param tableau La liste
		 * @return Le dernier élément
		 */
		public static <T> T pop(List<T> tableau) {
			if (tableau.isEmpty())
				return null;
			
			T t = tableau.get(tableau.size() - 1);
			
			tableau.remove(tableau.size() - 1);
			return t;
		}
	}

	/**
	 * Fourni des fonctions pour les maps
	 * 
	 * @author Bruju
	 *
	 */
	public static class Maps {
		/**
		 * Renvoi la valeur stockée à la clé donnée dans la map. Si aucune valeur n'est dans la map, utilise le
		 * supplier pour mettre une valeur initiale, puis renvoie cette dernière.
		 * @param map La map
		 * @param key La clé
		 * @param supplier Un fournisseur de valeur de base
		 * @return La valeur présente dans la map, ou à défaut la valeur fournie par supplier.
		 */
		public static <K, V> V getX(Map<K, V> map, K key, Supplier<? extends V> supplier) {
			V value = map.get(key);
			
			if (value == null) {
				value = supplier.get();
				map.put(key, value);
			}
			
			return value;
		}

		/**
		 * Ajoute dans la map receveur toutes les clés de donneur
		 * @param receveur La map destination
		 * @param donneur La map source
		 * @param fonctionDAjout if (valeurPresente == null) receveur.put(cle, fonctionDAjout.apply(valeur));
		 * @param fonctionSiPresent if (valeurPresente != null)
		 * receveur.put(cle, fonctionSiPresent.apply(valeurPresente, valeur));
		 */
		public static <K, V> void fusionnerDans(Map<K, V> receveur,
				Map<K, V> donneur, UnaryOperator<V> fonctionDAjout, BinaryOperator<V> fonctionSiPresent) {
			donneur.forEach((cle, valeur) -> {
				V valeurPresente = receveur.get(cle);
				
				if (valeurPresente == null) {
					receveur.put(cle, fonctionDAjout.apply(valeur));
				} else {
					receveur.put(cle, fonctionSiPresent.apply(valeurPresente, valeur));
				}
			});
		}
		
		/**
		 * Ajoute un élément à la liste qui est associé à la clé donnée. Si la clé n'est pas initialisée, met une
		 * ArrayList pour cette clé.
		 * @param map La map
		 * @param cle La clé
		 * @param element L'élément à ajouter à la liste
		 */
		public static <K, V> void ajouterElementDansListe(Map<K, List<V>> map, K cle, V element) {
			List<V> liste = map.get(cle);
			if (liste == null) {
				liste = new ArrayList<V>();
				map.put(cle, liste);
			}
			
			liste.add(element);
		}
	}

	


	
	
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
			return 9;
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
	
	/**
	 * Applique fonctionFusion sur tous les éléments de la liste jusqu'à que ce ne soit plus possible
	 * @param listeDeBase La liste à transformer
	 * @param fonctionFusion La fonction de fusion. Renvoie null si la fusion n'est pas possible
	 * @return La liste avec fonctionFusion appliquée le plus de fois possible
	 */
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
					}
				}

				transformee.add(p);
			}
		}

		return transformee;
	}
	
	/**
	 * Fonction en faisant rien. Remplace () -> {} par Utilitaire::doNothing dans les lambda
	 */
	public static void doNothing() {
		
	}
	
	/**
	 * Converti un objet en ArrayList
	 * @param element L'objet à convertir
	 * @return Une liste d'une case contenant l'élément 
	 */
	public static <T> ArrayList<T> toArrayList(T element) {
		ArrayList<T> arrayList = new ArrayList<>(1);
		arrayList.add(element);
		return arrayList;
	}
	
	/**
	 * Ecrit dans le fichier dont le chemin est spécifié la chaîne à écrire
	 * @param chemin Le fichier
	 * @param chaineAEcrire La chaîne
	 */
	public static void Fichier_Ecrire(String chemin, String chaineAEcrire) {
		File f = new File(chemin);

		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			ff.write(chaineAEcrire);
			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime le dossier donné en supprimant tous les fichiers à l'intérieur
	 * @param dossier Le dossier à supprimer
	 */
	public static void Fichier_supprimerDossier(File dossier) {
		for (File fichierPresent : dossier.listFiles()) {
			if (fichierPresent.isDirectory())
				Fichier_supprimerDossier(fichierPresent);
			else
				fichierPresent.delete();
		}
		
		dossier.delete();
	}

	public static int[] toArrayInt(List<Integer> liste) {
		int[] tableau = new int[liste.size()];
		
		for (int i = 0 ; i != tableau.length ; i++) {
			tableau[i] = liste.get(i);
		}
		
		return tableau;
	}
	
}
