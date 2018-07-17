package poissons;

import java.awt.Color;
import java.awt.Graphics;


/**
 * Classe AbstractPoisson  : 
 * définit les attributs et les méthodes communes à
 * tous les poissons ( on aura ici des poissons
 * basiques et des prédateurs, qui hériteront de cette classe)
 *
 */

abstract public class AbstractPoisson extends Objet {
	public static final double VNORM = 1;
	public static final double DISTANCE_MIN = 10;
	public static final double DISTANCE_MAX = 30;
	public static final double OBST_DIST = 20;

	public double x, y;
	public Angle dir;
	

	public AbstractPoisson(double x, double y) {
		super(Type.POISSON);
		this.x = x;
		this.y = y;
		this.dir =  Angle.getRandom();
		this.TTL = 1;
	}

	@Override
	public double distance(AbstractPoisson o2) {
		return Math.sqrt((this.x - o2.x) * (this.x - o2.x) + (this.y - o2.y) * (this.y - o2.y));
	}
	@Override
	abstract public AbstractPoisson update(Bocal bocal);
	
	@Override
	public Angle direction(AbstractPoisson o2) {
		double rel_x = this.x - o2.x;
		double rel_y = this.y - o2.y;
		return new Angle(Math.atan2(rel_y, rel_x));
	}

	@Override
	public void dessine(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval((int)this.x-1, (int)this.y-1, 2, 2);
		g.drawLine((int)this.x, 
				   (int)this.y, 
				   (int)(this.x - 10*Math.cos(this.dir.getValue())), 
				   (int)(this.y - 10*Math.sin(this.dir.getValue())));
	}
	
	public String describe() {
		return "poisson (" + (int)this.x + "," + (int)this.y + "), direction : " + this.dir.getValue()/Math.PI + "pi";
	}

	public void kill() {
		this.TTL = 0;
		
	}
	
}
