package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant;


public interface ElementIntermediaire extends Element {
	public Element[] getFils();
	
	/**
	 * Recrée l'élément avec les nouveaux fils donnés dans le même ordre que getFils.
	 * <p>
	 * Doit TOUJOURS renvoyer un objet du même type que l'objet utilisant la méthode. C'est-à-dire que si l'élément
	 * implémenté est Cheval, fonctionDeReceration doit obligatoirement recréer un Cheval, et pas un Animal ni un
	 * Poulain.
	 * 
	 * @param elementsFils
	 * @return
	 */
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils);
}
