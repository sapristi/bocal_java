package poissons;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/** classe qui gère les poissons de base
 *  
 *  On ajoute à AbstractPoisson les méthodes 
 *  qui définissent le comportement en fonction
 *  des poissons voisins et de l'environnement
*/
public class Poisson extends AbstractPoisson {

	public Poisson(double x, double y) {
		super( x, y);
	}

	@Override
	public Poisson update(Bocal bocal) {
		
		//System.out.println("updating  : " + this.describe() );
		
		
		ArrayList<Objet> obstacles = bocal.obstacles;
		
		
		Objet closest_obstacle = obstacles.stream().min((o1,o2) -> Double.compare(o1.distance(this), o2.distance(this))).orElse(null);
		
		if (closest_obstacle.distance(this) < OBST_DIST) {
			if (closest_obstacle.type == Type.MUR) {
				this.eviteDirection(closest_obstacle.direction(this), 0.05, Math.PI* 3/4);
			} else if (closest_obstacle.type == Type.ROCHER) {
				this.eviteDirection(closest_obstacle.direction(this), 0.05, Math.PI / 3);
			}
		}else {
			
			
//			ArrayList<AbstractPoisson> poissons = bocal.poissons;
//			ArrayList<AbstractPoisson> poissons_proches = poissons.stream()
//					.filter(p -> p.distance(this) < DISTANCE_MAX && p.distance(this) > 0)
//					.collect(Collectors.toCollection(ArrayList::new));
			
			ArrayList<AbstractPoisson> poissons_proches = bocal.buckets.getNeighbours((int)this.x, (int)this.y)
					.filter(p -> p.distance(this) < DISTANCE_MAX && p.distance(this) > 0)
					.collect(Collectors.toCollection(ArrayList::new));
			
			Optional<AbstractPoisson> predateur = poissons_proches.stream().filter(p -> p.type == Objet.Type.PREDATEUR).findFirst();
			if (predateur.isPresent())
			{
				this.dir.setValue( predateur.get().direction(this).getValue() + Math.PI );
			} else if (poissons_proches.size() > 0) {
				this.adapteBanc(poissons_proches);
			}
			
		}
		double random_dir = Math.PI * ((Math.random()-0.5) * 1/30);
		this.dir.addToValue(random_dir);
		
		this.x += (Math.cos(this.dir.getValue()) * VNORM);
		this.y += (Math.sin(this.dir.getValue()) * VNORM);
		
		//System.out.println("updated  : " + this.describe() );
		return this;
	}

	public void adapteBanc(ArrayList<AbstractPoisson> poissons_proches) {
	
		Vector sum_dir = 
			poissons_proches.stream()
							.map(mo -> mo.dir)
							.reduce(new Vector(0,0), 
									(v, d) -> Vector.add(v, new Vector(d)), 
									(v1,v2) -> Vector.add(v1,v2));
		
		double mean_dir = (new Angle(sum_dir)).getValue();
		this.approcheDirection(new Angle(mean_dir), 0.1, Math.PI);
		
		Optional<AbstractPoisson> closest_fish = poissons_proches
				.stream()
				.min((p1, p2) -> Double.compare(p1.distance(this), p2.distance(this)));
		if (closest_fish.isPresent()) {
			if (closest_fish.get().distance(this) < DISTANCE_MIN) {
				this.eviteDirection(closest_fish.get().direction(this), 0.13, Math.PI);
			}
		} 
	}

	private void approcheDirection(Angle dir, double strength, double bound) {
		double rel_direction = Angle.relative_angle(this.dir, dir);
		if (rel_direction != 0) {
			if (Math.abs(rel_direction) < Math.PI * strength ) {
				this.dir.setValue(dir.getValue());
			} else {
				double rel_dir_sign = rel_direction / Math.abs(rel_direction);
				this.dir.addToValue( - strength * Math.PI * rel_dir_sign );
			}
		}
	}
	private void eviteDirection(Angle dir, double strength, double bound) {
		double rel_direction = Angle.relative_angle(this.dir, dir);
		if (Math.abs(rel_direction) < bound) {
			double rel_dir_sign = rel_direction / Math.abs(rel_direction);
			this.dir.addToValue( strength * Math.PI * rel_dir_sign );
		}
	}
}
