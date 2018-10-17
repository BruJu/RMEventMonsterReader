package fr.bruju.rmeventreader.utilitaire;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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
		
		public static <K, V> V getY(Map<K, V> map, K key, Function<K, V> initialisateur) {
			V value = map.get(key);
			
			if (value == null) {
				value = initialisateur.apply(key);
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
				liste = new ArrayList<>();
				map.put(cle, liste);
			}
			
			liste.add(element);
		}

		/**
		 * Ajoute un élément à l'ensemble qui est associé à la clé donnée. Si la clé n'est pas initialisée, met un
		 * TreeSet pour cette clé.
		 * @param map La map
		 * @param cle La clé
		 * @param element L'élément à ajouter à l'ensemble
		 */
		public static <K, V> void ajouterElementDansSet(Map<K, Set<V>> map, K cle, V element) {
			Set<V> liste = map.get(cle);
			if (liste == null) {
				liste = new TreeSet<>();
				map.put(cle, liste);
			}
			
			liste.add(element);
		}

		public static <K, V> void combiner(Map<K, V> destination, Map<K, V> source1, Map<K, V> source2,
				BinaryOperator<V> fonctionDeCombinaison) {
			Stream.of(source1.keySet(), source2.keySet())
				  .flatMap(Set::stream)
				  .distinct()
				  .forEach(cle -> {
					  V valeur1 = source1.get(cle);
					  V valeur2 = source2.get(cle);
					  if (valeur1 != null || valeur2 != null) {
						  destination.put(cle, fonctionDeCombinaison.apply(valeur1, valeur2));
					  }
				  });
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

	/**
	 * Transforme la liste en un tableau de int
	 * @param liste La liste
	 * @return Un tableau de int
	 */
	public static int[] toArrayInt(List<Integer> liste) {
		int[] tableau = new int[liste.size()];
		
		for (int i = 0 ; i != tableau.length ; i++) {
			tableau[i] = liste.get(i);
		}
		
		return tableau;
	}
	
	/**
	 * Donne la position de l'élément dans le tableau, ou -1 si il est absent
	 * @param element L'élément à chercher
	 * @param elements Le tableau de nombres
	 * @return La position du nombre
	 */
	public static int getPosition(int element, int[] elements) {
		for (int i = 0 ; i != elements.length ; i++) {
			if (elements[i] == element) {
				return i;
			}
		}
		
		return -1;
	}

	public static <T> T[] Arrays_aggrandir(T[] tableau, int tailleVoulue, Supplier<T> provider) {
		if (tableau.length == tailleVoulue) {
			return tableau;
		}
		
		T[] nouveauTableau = Arrays.copyOf(tableau, tailleVoulue);
		
		for (int i = tableau.length ; i != tailleVoulue ; i++) {
			nouveauTableau[i] = provider.get();
		}
		
		return nouveauTableau;
	}
}
