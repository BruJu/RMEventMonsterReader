package fr.bruju.rmeventreader.implementation.recomposeur.maillon;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Calcul;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Filtre;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.SousAlgorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.NombreAleatoire;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurRetourneur;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class FormuleToString extends VisiteurRetourneur<FormuleToString.Res> {
	public static class Res {
		public Integer prio;
		public String s;
		public Res() {}
		public Res(Integer prio, String s) {this.prio=prio;this.s=s;}
	}
	
	private static final Integer PRIO_CONDARME = 10;

	private static final Integer PRIO_CONDITIONNELLE = 15;

	private static final Integer PRIO_CONSTANTE = 20;
	
	private BaseDeVariables base;

	public FormuleToString(BaseDeVariables base) {
		this.base = base;
	}

	@Override
	protected Res traiter(Affectation element) {
		return traiter(element.base);
	}

	@Override
	protected Res traiter(ConditionArme element) {
		String s = base.getNomParIDHeros(element.heros) + "@" + base.getNomParIDObjet(element.objet);
		
		if (!element.haveToOwn)
			s = "Non " + s;
		
		return new Res(PRIO_CONDARME, s);
	}


	@Override
	protected Res traiter(Conditionnelle element) {
		Res c = traiter(element.condition);

		Res v = traiter(element.siVrai);
		Res f = traiter(element.siFaux);
		
		Res r = new Res();
		
		r.prio = PRIO_CONDITIONNELLE;
		r.s = "si(" + c.s + " ; " + v.s + " ; " + f.s +" )";
		
		return r;
	}

	@Override
	protected Res traiter(ConditionValeur element) {
		Res g = traiter(element.gauche);
		Res d = traiter(element.droite);
		String op = Utilitaire.getSymbole(element.operateur);
		
		return new Res(PRIO_CONDARME, g.s + " " + op + " " + d.s);
	}

	@Override
	protected Res traiter(Calcul element) {
		return super.traiter(element);
	}

	@Override
	protected Res traiter(NombreAleatoire element) {
		return new Res(PRIO_CONSTANTE, element.min +"~" + element.max);
	}

	@Override
	protected Res traiter(Constante element) {
		return new Res(PRIO_CONSTANTE, Integer.toString(element.valeur));
	}

	@Override
	protected Res traiter(Entree element) {
		return new Res(PRIO_CONSTANTE, base.getNomVariable(element.id));
	}

	@Override
	protected Res traiter(Algorithme element) {
		List<Res> sousRes = element.composants.stream().map(this::traiter)
									.collect(Collectors.toList());
		
		
		return super.traiter(element);
	}

	@Override
	protected Res traiter(ConditionFixe element) {
		throw new RuntimeException("Ne gère pas les conditions fixe");
	}

	@Override
	protected Res traiter(Filtre element) {
		throw new RuntimeException("Ne gère pas les filtres");
	}

	@Override
	protected Res traiter(SousAlgorithme element) {
		throw new RuntimeException("Ne gère pas les sous algorithmes");
	}
	
	

}
