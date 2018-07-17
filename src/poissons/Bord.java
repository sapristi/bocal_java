package poissons;

import java.awt.Graphics;


/**
 * 
 * Classe Bord, qui hérite de objet.
 * 
 * Un bord est caractérisé par son orientation, 
 * et par la fonction qui donne sa distance par rapport 
 * à un poisson donné.
 * 
 * Un fonction qui trace le bord a aussi été ajoutée 
 * (à des fins de débuggage)
 *
 */

public class Bord extends Objet{
	private Angle orientation;
	private Distance_fun distance_fun;
	private Dessine dess;
	
	public Bord(Distance_fun dist, double a, Dessine dess) {
		super(Objet.Type.MUR);
		this.distance_fun = dist;
		this.orientation = new Angle(a);
		this.TTL = 1;
		this.dess = dess;
	}

	@Override
	public double distance(AbstractPoisson o2) {
		return this.distance_fun.distance(o2);
	}
	@Override
	public Angle direction(AbstractPoisson o2) {
		return this.orientation;
	}

	@Override
	public Bord update(Bocal b) {
		// do nothing
		return this;
	}

	@Override
	public void dessine(Graphics g) {
		this.dess.dessine(g);
	}
}
