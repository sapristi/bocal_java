package poissons;

import java.awt.Graphics;


/**
 * Class de laquelle dérivent tous les objets présents 
 * dans l'environnement.
 * 
 * On déclare les méthodes abstraites distance et angle qui permettent 
 * aux poissons de se repérer par rapport à un objet.
 * La classe objet possède aussi un attribute type qui permet aux poisson
 * d'identifier le type des objets qu'ils rencontrent.
 * 
 * On déclare enfin les méthode abstraites update et draw.
 * 
 *
 */
abstract public class Objet {
	
	public static enum Type {
		POISSON,
		PREDATEUR,
		ROCHER,
		MUR,
	}

	
	public Type type;
	public int TTL;
	public Objet() {super();}
	// distance entre this et o2
	public abstract double distance(AbstractPoisson o2);
	// direction dans laquelle se trouve this par rapport � o2
	public abstract Angle direction(AbstractPoisson o2);
	
	public abstract Objet update(Bocal b);
	
	public abstract void dessine(Graphics g);
	
	public Objet(Type t) {
		super();
		this.type = t;
	}
	
	
}
