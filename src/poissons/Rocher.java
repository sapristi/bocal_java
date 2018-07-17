package poissons;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Rocher
 * 
 *
 */


public class Rocher extends Objet {
	private int x, y, rayon;
	
	public Rocher(int x, int y, int rayon, int tTL) {
		super(Type.ROCHER);
		this.x = x;
		this.y = y;
		this.rayon = rayon;
		TTL = tTL;
	}
	

	public boolean estMort() {return this.TTL <= 0;}


	@Override
	public double distance(AbstractPoisson o2) {
		double sqd = (this.x - o2.x) * (this.x - o2.x) + (this.y - o2.y) * (this.y - o2.y);
		return Math.max(Math.sqrt(sqd) -this.rayon, 0 );
	}


	@Override
	public Angle direction(AbstractPoisson o2) {
		double rel_x = this.x - o2.x;
		double rel_y = this.y - o2.y;
		return new Angle(Math.atan2(rel_y, rel_x));
	}


	@Override
	public Rocher update(Bocal b) {
		this.TTL -= 1;
		return this;
	}


	@Override
	public void dessine(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(this.x - this.rayon, this.y - this.rayon, this.rayon*2, this.rayon*2);
	}
	
}
