package poissons;


/**
 * Classe Angle : sert à stoquer et manipuler un angle de manière à ce qu'il 
 * soit toujours entre 0 et 2 PI
 * 
 *
 */

public class Angle {
    public static double normalizeAngle(double a, double center) {
        return a - 2*Math.PI * Math.floor((a + Math.PI - center) / (2*Math.PI));
    }
	private double angle;
	public Angle(double a) {this.angle = normalizeAngle(a, Math.PI);}
	public Angle(Vector v) {this(Math.atan2(v.y, v.x));}
	public double getValue() {return this.angle;}
	public void setValue(double a) {this.angle = normalizeAngle(a, Math.PI);}
	public void addToValue(double delta_a) {this.angle = normalizeAngle(this.angle + delta_a, Math.PI);}
	public static Angle getRandom() {return new Angle(Main.next_random() * 2*Math.PI);}
	public static double relative_angle(Angle a1, Angle a2) {
		return normalizeAngle(a1.getValue() - a2.getValue(), 0);
	}
	
}
