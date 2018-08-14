package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles.Action;

/**
 * Interpreteur de l'arbre XML généré par EasyRPG.
 * 
 * @author Bruju
 *
 */
public class InterpreterMapXML implements Interpreter {
	private static Map<Long, Action> actionsConnues = ActionsPossibles.remplirActions();
	
	
	/** Gestionnaire d'action */
	private ActionMaker actionMaker;
	
	private int idEvent;
	
	private int idPage;
	
	
	public InterpreterMapXML(ActionMaker actionMaker, int idEvent, int idPage) {
		this.actionMaker = actionMaker;
		this.idEvent = idEvent;
		this.idPage = idPage;
	}

	@Override
	public void inputFile(String path) throws IOException {
		Document doc = UtilXML.lireDocument(path);
		
		if (doc == null) {
			return;
		}
		
		// Recupérer le bon event à la bonne page
		NodeList eventNodes = doc.getElementsByTagName("Event");
		Node eventNode = UtilXML.chercherID(eventNodes, idEvent);
		Node eventPage = UtilXML.chercherPage(eventNode, idPage);
		Node event_commands = UtilXML.goToEventCommands(eventPage);
		List<Node> events = UtilXML.extraireEvenements(event_commands);
		traiterEvenements(events);
	}

	private void traiterEvenements(List<Node> events) {
		for (Node node : events) {
			traiterEvenement(node);			
		}
	}

	/**
	 * 
	 * 
	 * <EventCommand>
     *  <code>10150</code>
     *  <indent>0</indent>
     *  <string></string>
     *  <parameters>1 1</parameters>
     * </EventCommand>
	 * 
	 * @param item
	 */
	
	private void traiterEvenement(Node item) {
		// Lecture du noeud
		NodeList children = item.getChildNodes();
		
		String code = null;
		String string = null;
		String parameters = null;
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node child = children.item(i);
			
			if (child.getNodeName().equals("code")) {
				code = child.getTextContent();
			}
			if (child.getNodeName().equals("string")) {
				string = child.getTextContent();
			}
			if (child.getNodeName().equals("parameters")) {
				parameters = child.getTextContent();
			}
		}
		
		if (code == null || string == null || parameters == null) {
			throw new RuntimeException("Element invalide");
		}
		
		// Decodage
		long codeD = Long.valueOf(code);
		int[] parametersD = decrypterParameters(parameters);
		
		// Traitement
		executer(codeD, string, parametersD);
	}

	private int[] decrypterParameters(String parameters) {
		if (parameters.length() == 0)
			return null;
		
		String[] parametersS = parameters.split(" ");
		int[] parametersD = new int[parametersS.length];
		for (int i = 0 ; i != parametersS.length ; i++) {
			parametersD[i] = Integer.decode(parametersS[i]);
		}
		
		return parametersD;
	}

	private void executer(long codeD, String string, int[] parameters) {
		Action action = actionsConnues.get(codeD);
		
		if (action == null) {
			actionMaker.notImplementedFeature("Code " + codeD + " non implémenté");
		} else {
			action.exec(actionMaker, string, parameters);
		}
	}

}
