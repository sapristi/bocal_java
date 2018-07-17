package poissons;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * Classe Prédateur : 
 * 
 * Poisson au comportement très simple : se dirige vers le 
 * poisson le plus proche.
 * 
 * Si un autre poisson se trouve à une distance inférieure à 2,
 * il est tué.
 *
 */


public class Predateur extends AbstractPoisson {
	public static double VNORM = 1.1;
	
	public Predateur(double x, double y) {
		super(x, y);
		this.type = Objet.Type.PREDATEUR;
	}

	@Override
	public Predateur update(Bocal bocal) {
		ArrayList<AbstractPoisson> poissons = bocal.poissons;
		
		AbstractPoisson closest_fish = poissons.stream()
				.filter(p -> p.distance(this) > 0)
				.min((p1, p2) -> Double.compare(p1.distance(this), p2.distance(this))).orElse(null);
		if (closest_fish.distance(this) < 2) {
			closest_fish.kill();
		} else {
			this.dir.setValue(closest_fish.direction(this).getValue());
		}

		this.x += (Math.cos(this.dir.getValue()) * VNORM);
		this.y += (Math.sin(this.dir.getValue()) * VNORM);
		//System.out.println("updated  : " + this.describe() );
		return this;
	}
	

	public void eviteMur(Objet mur) {
		double rel_direction = Angle.relative_angle(this.dir, mur.direction(this));
		if (Math.abs(rel_direction) < Math.PI*3/4) {
			//System.out.println("obstacle proche, direction " + rel_direction/Math.PI );
			if (rel_direction < 0) {
				this.dir.addToValue(-Math.PI / 24);
			} else {
				this.dir.addToValue(Math.PI/24);
			}
		}
	}
	
	public void eviteObstacle(Objet obstacle) {
		double rel_direction = Angle.relative_angle(this.dir, obstacle.direction(this));
		double dist = obstacle.distance(this);
		//System.out.println("obstacle proche, direction " + rel_direction/Math.PI );
		if (dist < 10 && Math.abs(rel_direction) < Math.PI/2) {
			if (rel_direction < 0) {
				this.dir.addToValue(+Math.PI / 8);
			} else {
				this.dir.addToValue(-Math.PI/8);
			}
		}
		if (dist < 20 && Math.abs(rel_direction) < Math.PI/3) {
			if (rel_direction < 0) {
				this.dir.addToValue(+Math.PI / 12);
			} else {
				this.dir.addToValue(-Math.PI/12);
			}
		}
	}
	@Override
	public void dessine(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval((int)this.x-2, (int)this.y-2, 4, 4);
		g.drawLine((int)this.x, (int)this.y, 
				   (int)(this.x - 10*Math.cos(this.dir.getValue())), 
				   (int)( this.y - 10*Math.sin(this.dir.getValue())));
	}

}
