package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.IOException;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Node;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles.Action;
import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.ExtractionXML;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.InstancieurXML;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;

/**
 * Interpreteur de l'arbre XML généré par EasyRPG.
 * 
 * @author Bruju
 *
 */
public class InterpreterMapXML {
	private static Map<Long, Action> actionsConnues = ActionsPossibles.remplirActions();

	/** Gestionnaire d'action */
	private ActionMaker actionMaker;

	public InterpreterMapXML(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
	}

	public void inputFile(String path, int idEvent, int idPage) throws IOException {
		String codeIDEvent = Utilitaire_XML.transformerId(idEvent);
		String codeIDPage = Utilitaire_XML.transformerId(idPage);
		String xPath;
		
		if (idPage == -1) {
			xPath = "/LDB/Database/commonevents/CommonEvent[@id='" + codeIDEvent + "']/event_commands";
		} else {
			xPath = "/LMU/Map/events/Event[@id='" + codeIDEvent + "']/pages/EventPage[@id='" + codeIDPage + "']"
					+ "/event_commands";
		}
		
		Node event_commands = (Node) Utilitaire_XML.extraireDepuisXPath(path, xPath, XPathConstants.NODE);
		if (event_commands == null)
			return;

		ExtractionXML.extraireEvenements(event_commands).forEach(this::executer);
	}

	private void executer(Node noeud) {
		Instruction instruction = InstancieurXML.instruction(noeud);

		Action action = actionsConnues.get((long) instruction.code);

		if (action == null) {
			ActionsPossibles.afficher(actionMaker, instruction.code, instruction.string, instruction.parameters);
		} else {
			action.exec(actionMaker, instruction.string, instruction.parameters);
		}
	}

}
