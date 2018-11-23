package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.TransformationDeTable;
import fr.bruju.util.table.Table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class UnifierSubstitutions implements TransformationDeTable {
	@Override
	public Table appliquer(Table table) {
		Map<String, BinaryOperator<Object>> map = new LinkedHashMap<>();

		map.put("Monstre", UnifierSubstitutions::unificateurHPMonstres);
		map.put("Algorithme", UnifierSubstitutions::unificateurAbsorbant);

		table.unifier(map);
		return table;
	}

	private static Object unificateurHPMonstres(Object o1, Object o2) {
		SeparateurParHPDeMonstres.ClassificateurMonstreCible c1 =
				(SeparateurParHPDeMonstres.ClassificateurMonstreCible) o1;
		SeparateurParHPDeMonstres.ClassificateurMonstreCible c2 =
				(SeparateurParHPDeMonstres.ClassificateurMonstreCible) o2;

		SeparateurParHPDeMonstres.ClassificateurMonstreCible union =
				new SeparateurParHPDeMonstres.ClassificateurMonstreCible(c1.idMonstre + c2.idMonstre);

		return union;
	}

	private static Object unificateurAbsorbant(Object o1, Object o2) {
		return o1;
	}
}
