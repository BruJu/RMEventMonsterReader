package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles;
import fr.bruju.rmeventreader.actionmakers.xml.ActionsPossibles.Action;
import fr.bruju.rmeventreader.dictionnaires.ExtractionXML;
import fr.bruju.rmeventreader.dictionnaires.UtilXML;
import fr.bruju.rmeventreader.utilitaire.Triplet;

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
		Document doc = UtilXML.lireDocument(path);
		
		if (doc == null) {
			return;
		}
		
		String codeIDEvent = UtilXML.transformerId(idEvent);
		String codeIDPage = UtilXML.transformerId(idPage);
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile(
					"/LMU/Map/events/Event[@id='"+codeIDEvent+"']/pages/EventPage[@id='"+codeIDPage+"']"
					+ "/event_commands");
			traiterEventCommands(doc, expr);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public void inputFile(String path, int idEvent) throws IOException {
		Document doc = UtilXML.lireDocument(path);
		
		if (doc == null) {
			return;
		}
		
		String codeIDEvent = UtilXML.transformerId(idEvent);
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile(
					"/LDB/Database/commonevents/CommonEvent[@id='"+codeIDEvent+"']/event_commands");
			traiterEventCommands(doc, expr);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	private void traiterEventCommands(Document doc, XPathExpression xPath) throws XPathExpressionException {
		Node event_commands = (Node) xPath.evaluate(doc, XPathConstants.NODE);
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
		Triplet<Long, String, int[]> triplet = ExtractionXML.decrypterNoeudEventCommand(item);
		
		// Traitement
		executer(triplet.a, triplet.b, triplet.c);
	}

	private void executer(long codeD, String string, int[] parameters) {
		Action action = actionsConnues.get(codeD);
		
		if (action == null) {
			ActionsPossibles.afficher(actionMaker, codeD, string, parameters);
		} else {
			action.exec(actionMaker, string, parameters);
		}
	}

}
