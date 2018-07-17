package poissons;

/**
 * 
 * Vecteur en coordonnées cartésiennes, sert principalement
 * ici à faire la moyenne des directions des poissons proches.
 *
 */

public class Vector {

	public double x, y;

	public Vector(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Vector(Angle a) {this(Math.cos(a.getValue()), Math.sin(a.getValue()));}
	public static Vector add(Vector v1,Vector v2) {return new Vector(v1.x + v2.x, v1.y + v2.y);}
}
