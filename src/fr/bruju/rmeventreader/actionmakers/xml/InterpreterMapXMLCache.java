package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Node;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles.Action;
import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.ExtractionXML;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.InstancieurXML;

/**
 * Interpreteur de l'arbre XML généré par EasyRPG.
 * 
 * @author Bruju
 *
 */
public class InterpreterMapXMLCache {
	private static Map<Long, Action> actionsConnues = ActionsPossibles.remplirActions();

	/** Gestionnaire d'action */
	private ActionMaker actionMaker;

	public InterpreterMapXMLCache(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
	}

	public void inputFile(String path, int idEvent, int idPage) {
		List<Instruction> instructions = LecteurDeCache.chargerInstructions(idEvent, idPage);
		instructions.forEach(this::executer);
	}

	private void executer(Instruction instruction) {
		Action action = actionsConnues.get((long) instruction.code);

		if (action == null) {
			ActionsPossibles.afficher(actionMaker, instruction.code, instruction.string, instruction.parameters);
		} else {
			action.exec(actionMaker, instruction.string, instruction.parameters);
		}
	}
}
