package imagereader;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe contient la liste des motifs à reconnaître.
 * Ses instructions sont pseudo générées par la reconnaissance de motifs
 */
public class SymboleReconnus {
	/**
	 * Renvoie la liste des motifs déjà connus
	 */
	public List<Motif> getMotifs() {
		
		List<Motif> motifs = new ArrayList<>(164 - 18 + 1);
		
		motifs.add(new Motif('-', new int[]{15}));
		motifs.add(new Motif('-', new int[]{31}));
		motifs.add(new Motif('-', new int[]{7}));
		motifs.add(new Motif('?', new int[]{30, 33, 32, 32, 24, 4, 4, 0, 4, 4}));
		motifs.add(new Motif('\'', new int[]{1, 1, 1}));
		motifs.add(new Motif('\'', new int[]{2, 2, 1}));
		motifs.add(new Motif('ä', new int[]{10, 0, 14, 16, 30, 17, 17, 30}));
		motifs.add(new Motif('A', new int[]{12, 12, 18, 18, 18, 63, 33, 33}));
		motifs.add(new Motif('â', new int[]{12, 18, 0, 14, 16, 30, 17, 17, 30}));
		motifs.add(new Motif('a', new int[]{14, 16, 30, 17, 17, 30}));
		motifs.add(new Motif('A', new int[]{24, 24, 36, 36, 66, 66, 126, 129, 129}));
		motifs.add(new Motif('â', new int[]{24, 36, 0, 30, 32, 32, 62, 33, 33, 62}));
		motifs.add(new Motif('a', new int[]{30, 32, 32, 62, 33, 33, 62}));
		motifs.add(new Motif('à', new int[]{4, 8, 0, 14, 16, 30, 17, 17, 30}));
		motifs.add(new Motif('A', new int[]{8, 8, 20, 20, 34, 34, 62, 65, 65}));
		motifs.add(new Motif('b', new int[]{1, 1, 1, 15, 17, 17, 17, 17, 15}));
		motifs.add(new Motif('b', new int[]{1, 1, 1, 29, 35, 33, 33, 33, 33, 31}));
		motifs.add(new Motif('B', new int[]{15, 17, 17, 17, 31, 33, 33, 33, 31}));
		motifs.add(new Motif('B', new int[]{15, 17, 17, 31, 33, 33, 33, 31}));
		motifs.add(new Motif('ç', new int[]{14, 17, 1, 1, 1, 17, 14, 8, 6}));
		motifs.add(new Motif('c', new int[]{14, 17, 1, 1, 1, 17, 14}));
		motifs.add(new Motif('ç', new int[]{14, 17, 1, 1, 17, 14, 8, 6}));
		motifs.add(new Motif('c', new int[]{14, 17, 1, 1, 17, 14}));
		motifs.add(new Motif('c', new int[]{30, 33, 1, 1, 1, 33, 30}));
		motifs.add(new Motif('C', new int[]{60, 66, 1, 1, 1, 1, 1, 66, 60}));
		motifs.add(new Motif('C', new int[]{60, 66, 1, 1, 1, 1, 66, 60}));
		motifs.add(new Motif('d', new int[]{16, 16, 16, 30, 17, 17, 17, 17, 30}));
		motifs.add(new Motif('D', new int[]{31, 33, 65, 65, 65, 65, 33, 31}));
		motifs.add(new Motif('D', new int[]{31, 33, 65, 65, 65, 65, 65, 33, 31}));
		motifs.add(new Motif('d', new int[]{32, 32, 32, 62, 33, 33, 33, 33, 49, 46}));
		motifs.add(new Motif('ê', new int[]{12, 18, 0, 14, 17, 31, 1, 17, 14}));
		motifs.add(new Motif('ê', new int[]{12, 18, 0, 30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('e', new int[]{14, 17, 31, 1, 17, 14}));
		motifs.add(new Motif('é', new int[]{16, 8, 0, 30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('ë', new int[]{18, 0, 30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('è', new int[]{2, 4, 0, 14, 17, 31, 1, 17, 14}));
		motifs.add(new Motif('e', new int[]{30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('e', new int[]{30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('E', new int[]{31, 1, 1, 31, 1, 1, 1, 31}));
		motifs.add(new Motif('è', new int[]{4, 8, 0, 30, 33, 33, 63, 1, 33, 30}));
		motifs.add(new Motif('E', new int[]{63, 1, 1, 1, 63, 1, 1, 1, 63}));
		motifs.add(new Motif('é', new int[]{8, 4, 0, 14, 17, 31, 1, 17, 14}));
		motifs.add(new Motif('f', new int[]{12, 2, 2, 15, 2, 2, 2, 2, 2, 2}));
		motifs.add(new Motif('f', new int[]{12, 2, 2, 15, 2, 2, 2, 2, 2}));
		motifs.add(new Motif('F', new int[]{31, 1, 1, 15, 1, 1, 1, 1}));
		motifs.add(new Motif('F', new int[]{63, 1, 1, 1, 31, 1, 1, 1, 1}));
		motifs.add(new Motif('g', new int[]{30, 17, 17, 17, 17, 30, 16, 14}));
		motifs.add(new Motif('G', new int[]{60, 66, 1, 1, 1, 113, 65, 66, 124}));
		motifs.add(new Motif('G', new int[]{60, 66, 1, 1, 113, 65, 65, 66, 60}));
		motifs.add(new Motif('G', new int[]{60, 66, 1, 1, 113, 65, 66, 60}));
		motifs.add(new Motif('g', new int[]{62, 33, 33, 33, 33, 49, 46, 32, 30}));
		motifs.add(new Motif('g', new int[]{62, 33, 33, 33, 33, 49, 46, 32, 32, 30}));
		motifs.add(new Motif('h', new int[]{1, 1, 1, 15, 17, 17, 17, 17, 17}));
		motifs.add(new Motif('h', new int[]{1, 1, 1, 29, 35, 33, 33, 33, 33, 33}));
		motifs.add(new Motif('H', new int[]{33, 33, 33, 63, 33, 33, 33, 33}));
		motifs.add(new Motif('H', new int[]{65, 65, 65, 65, 127, 65, 65, 65, 65}));
		motifs.add(new Motif('i', new int[]{1, 0, 0, 1, 1, 1, 1, 1, 1, 1}));
		motifs.add(new Motif('i', new int[]{1, 0, 0, 1, 1, 1, 1, 1, 1}));
		motifs.add(new Motif('î', new int[]{2, 5, 0, 2, 2, 2, 2, 2, 2, 2}));
		motifs.add(new Motif('ï', new int[]{5, 0, 2, 2, 2, 2, 2, 2, 2}));
		motifs.add(new Motif('ï', new int[]{5, 0, 2, 2, 2, 2, 2, 2}));
		motifs.add(new Motif('I', new int[]{7, 2, 2, 2, 2, 2, 2, 2, 7}));
		motifs.add(new Motif('I', new int[]{7, 2, 2, 2, 2, 2, 2, 7}));
		motifs.add(new Motif('J', new int[]{14, 8, 8, 8, 8, 8, 8, 7}));
		motifs.add(new Motif('j', new int[]{4, 0, 0, 6, 4, 4, 4, 4, 4, 4, 3}));
		motifs.add(new Motif('k', new int[]{1, 1, 1, 17, 9, 5, 3, 5, 9, 17}));
		motifs.add(new Motif('k', new int[]{1, 1, 1, 17, 9, 5, 7, 9, 17}));
		motifs.add(new Motif('K', new int[]{33, 17, 9, 5, 3, 5, 9, 17, 33}));
		motifs.add(new Motif('K', new int[]{33, 17, 9, 5, 7, 9, 17, 33}));
		motifs.add(new Motif('l', new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
		motifs.add(new Motif('l', new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}));
		motifs.add(new Motif('L', new int[]{1, 1, 1, 1, 1, 1, 1, 1, 63}));
		motifs.add(new Motif('L', new int[]{1, 1, 1, 1, 1, 1, 1, 31}));
		motifs.add(new Motif('M', new int[]{195, 195, 165, 165, 165, 153, 153, 129, 129}));
		motifs.add(new Motif('m', new int[]{205, 307, 273, 273, 273, 273, 273}));
		motifs.add(new Motif('m', new int[]{239, 273, 273, 273, 273, 273, 273}));
		motifs.add(new Motif('m', new int[]{239, 273, 273, 273, 273, 273}));
		motifs.add(new Motif('M', new int[]{99, 99, 85, 85, 73, 73, 65, 65}));
		motifs.add(new Motif('n', new int[]{15, 17, 17, 17, 17, 17}));
		motifs.add(new Motif('n', new int[]{29, 35, 33, 33, 33, 33, 33}));
		motifs.add(new Motif('N', new int[]{35, 35, 37, 37, 41, 41, 49, 49}));
		motifs.add(new Motif('ô', new int[]{12, 18, 0, 14, 17, 17, 17, 17, 14}));
		motifs.add(new Motif('o', new int[]{14, 17, 17, 17, 17, 14}));
		motifs.add(new Motif('O', new int[]{28, 34, 65, 65, 65, 65, 34, 28}));
		motifs.add(new Motif('o', new int[]{30, 33, 33, 33, 33, 33, 30}));
		motifs.add(new Motif('O', new int[]{60, 66, 129, 129, 129, 129, 129, 66, 60}));
		motifs.add(new Motif('P', new int[]{15, 17, 17, 17, 15, 1, 1, 1}));
		motifs.add(new Motif('p', new int[]{15, 17, 17, 17, 17, 15, 1, 1}));
		motifs.add(new Motif('p', new int[]{29, 35, 33, 33, 33, 33, 31, 1, 1, 1}));
		motifs.add(new Motif('p', new int[]{29, 35, 33, 33, 33, 33, 31, 1, 1}));
		motifs.add(new Motif('P', new int[]{31, 33, 33, 33, 33, 31, 1, 1, 1}));
		motifs.add(new Motif('Q', new int[]{28, 34, 65, 65, 65, 65, 34, 28, 16, 96}));
		motifs.add(new Motif('q', new int[]{30, 17, 17, 17, 17, 30, 16, 16}));
		motifs.add(new Motif('q', new int[]{62, 33, 33, 33, 33, 49, 46, 32, 32}));
		motifs.add(new Motif('r', new int[]{13, 3, 1, 1, 1, 1, 1}));
		motifs.add(new Motif('r', new int[]{13, 3, 1, 1, 1, 1}));
		motifs.add(new Motif('R', new int[]{15, 17, 17, 17, 15, 9, 17, 33}));
		motifs.add(new Motif('R', new int[]{31, 33, 33, 33, 17, 15, 17, 33, 65}));
		motifs.add(new Motif('R', new int[]{31, 33, 33, 33, 31, 9, 17, 33, 65}));
		motifs.add(new Motif('s', new int[]{14, 1, 3, 12, 8, 7}));
		motifs.add(new Motif('s', new int[]{30, 1, 1, 14, 16, 16, 15}));
		motifs.add(new Motif('S', new int[]{30, 33, 1, 1, 30, 32, 32, 33, 30}));
		motifs.add(new Motif('S', new int[]{30, 33, 1, 6, 24, 32, 33, 30}));
		motifs.add(new Motif('S', new int[]{62, 65, 1, 1, 62, 64, 64, 65, 62}));
		motifs.add(new Motif('T', new int[]{127, 8, 8, 8, 8, 8, 8, 8}));
		motifs.add(new Motif('t', new int[]{2, 2, 15, 2, 2, 2, 2, 12}));
		motifs.add(new Motif('t', new int[]{2, 2, 15, 2, 2, 2, 2, 2, 12}));
		motifs.add(new Motif('t', new int[]{2, 2, 31, 2, 2, 2, 2, 2, 28}));
		motifs.add(new Motif('ü', new int[]{10, 0, 17, 17, 17, 17, 17, 30}));
		motifs.add(new Motif('u', new int[]{17, 17, 17, 17, 17, 30}));
		motifs.add(new Motif('ù', new int[]{2, 4, 0, 17, 17, 17, 17, 17, 30}));
		motifs.add(new Motif('U', new int[]{33, 33, 33, 33, 33, 33, 33, 30}));
		motifs.add(new Motif('u', new int[]{33, 33, 33, 33, 33, 49, 46}));
		motifs.add(new Motif('V', new int[]{129, 129, 66, 66, 66, 36, 36, 24, 24}));
		motifs.add(new Motif('v', new int[]{17, 17, 10, 10, 10, 4, 4}));
		motifs.add(new Motif('v', new int[]{17, 17, 10, 10, 4, 4}));
		motifs.add(new Motif('v', new int[]{33, 33, 18, 18, 18, 12, 12}));
		motifs.add(new Motif('V', new int[]{33, 33, 33, 18, 18, 18, 12, 12}));
		motifs.add(new Motif('x', new int[]{17, 10, 4, 4, 10, 17}));
		motifs.add(new Motif('x', new int[]{17, 17, 10, 4, 10, 17, 17}));
		motifs.add(new Motif('x', new int[]{33, 33, 18, 12, 12, 12, 18, 33, 33}));
		motifs.add(new Motif('X', new int[]{33, 33, 18, 12, 12, 18, 33, 33}));
		motifs.add(new Motif('X', new int[]{65, 65, 34, 20, 8, 20, 34, 65, 65}));
		motifs.add(new Motif('y', new int[]{17, 10, 10, 10, 4, 4, 4, 2}));
		motifs.add(new Motif('y', new int[]{17, 17, 10, 10, 10, 4, 4, 4, 2}));
		motifs.add(new Motif('y', new int[]{33, 33, 18, 18, 18, 12, 12, 4, 4, 2}));
		motifs.add(new Motif('z', new int[]{15, 8, 4, 2, 1, 15}));
		motifs.add(new Motif('z', new int[]{31, 16, 8, 4, 2, 1, 31}));
		motifs.add(new Motif('Z', new int[]{63, 32, 16, 8, 4, 2, 1, 63}));
		motifs.add(new Motif("<<", new int[]{20, 10, 5, 10, 20}));
		motifs.add(new Motif(">>", new int[]{5, 10, 20, 10, 5}));
		motifs.add(new Motif("ct", new int[]{64, 64, 1006, 81, 65, 65, 65, 81, 910}));
		motifs.add(new Motif("ct", new int[]{64, 64, 494, 81, 65, 65, 81, 398}));
		motifs.add(new Motif("ff", new int[]{204, 34, 34, 255, 34, 34, 34, 34, 34, 34}));
		motifs.add(new Motif("ff", new int[]{204, 34, 34, 255, 34, 34, 34, 34, 34}));
		motifs.add(new Motif("ffa", new int[]{924, 66, 66, 31215, 32834, 32834, 63554, 33858, 33858, 63554}));
		motifs.add(new Motif("fo", new int[]{28, 2, 2, 975, 1058, 1058, 1058, 1058, 1058, 962}));
		motifs.add(new Motif("ît", new int[]{2, 21, 16, 122, 18, 18, 18, 18, 98}));
		motifs.add(new Motif("L'aff", new int[]{13369472, 2234497, 2234433, 16720897, 2237441, 2245121, 2245121, 2260481, 2261249, 2261311}));
		motifs.add(new Motif("ra", new int[]{237, 259, 481, 273, 273, 481}));
		motifs.add(new Motif("rj", new int[]{64, 0, 0, 109, 67, 65, 65, 65, 65, 65, 64, 64, 56}));
		motifs.add(new Motif("rt", new int[]{32, 32, 253, 35, 33, 33, 33, 193}));
		motifs.add(new Motif("rt", new int[]{32, 32, 509, 35, 33, 33, 33, 33, 449}));
		motifs.add(new Motif("Te", new int[]{127, 8, 1800, 2184, 3976, 136, 2184, 1800}));
		motifs.add(new Motif("Te", new int[]{127, 8, 3848, 4232, 4232, 8072, 136, 4232, 3848}));
		motifs.add(new Motif("To", new int[]{127, 8, 1800, 2184, 2184, 2184, 2184, 1800}));
		motifs.add(new Motif("tt", new int[]{34, 34, 255, 34, 34, 34, 34, 204}));
		
		return motifs;
	}
}
