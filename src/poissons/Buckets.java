package poissons;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * 
 * Classe qui permet de ranger les poissons de manière 
 * à pouvoir accéder rapidement aux voisins (sans avoir 
 * besoin de parcourir la liste entière des poissons).
 * 
 * L'espace est divisé en carrés (de coté 30, rayon du voisinage d'un poisson)
 * 
 * Pour chaque carré, un bucket est associé, dans lequel on va mettre les poissons 
 * qui se trouvent dans le carré correspondant (méthode register )
 * 
 * À chaque mise à jour, les buckets sont vidés, puis les poissons s'enregistrent.
 * Ils se mettent alors à jour  (en demandant la liste de leurs voisins aux buckets).
 * 
 * Les buckets sont ici gérés à l'aide d'une Map. On aurait pu le faire à l'aide d'un 
 * tableau, mais il faut prévoir le cas où les poissons sortent du bocal, ce qui
 * n'est pas impossible.
 */


public class Buckets {
	int size;
	int _x; 
	int _y;
	class K implements Comparable<K> {
		int x,y;

		public K(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(K k2) {
			if (Integer.compare(this.x, k2.x) == 0) {
				return Integer.compare(this.y, k2.y);
			} else {
				return Integer.compare(this.x, k2.x);
			}
		}
		
	}
	public TreeMap<K, HashSet<AbstractPoisson>> data;
	
	public Buckets(int width, int height, int size) {
		super();
		this.size = size;
		this._x = width / size;
		this._y = height / size;
		
		this.data = new TreeMap<K,HashSet<AbstractPoisson>>();
	}
	
	public void reset() {
		this.data.clear();
	}
	
	
	public void register(AbstractPoisson p, int x, int y) {
		int xx = x / this.size;
		int yy = y / this.size;
		K k = new K(xx,yy);
		
		if (data.containsKey(k)) {
			data.get(k).add(p);
		} else {
			HashSet<AbstractPoisson> temp = new HashSet<AbstractPoisson>();
			temp.add(p);
			data.put(k, temp);
		}
		
	}
	
	public Stream<AbstractPoisson> getNeighbours(int x, int y) {
		int xx = x / this.size;
		int yy = y / this.size;
		
		Stream<AbstractPoisson> res = this.data.get(new K(xx,yy)).stream();
		HashSet<AbstractPoisson> empty_bucket = new HashSet<AbstractPoisson>();
		
		if (xx > 0) {
			res = Stream.concat(res, this.data.getOrDefault(new K(xx-1,yy), empty_bucket).stream());
		}
		if (xx < _x){
			res = Stream.concat(res, this.data.getOrDefault(new K(xx+1,yy), empty_bucket).stream());
		}
		if (yy > 0) {
			res = Stream.concat(res, this.data.getOrDefault(new K(xx,yy-1), empty_bucket).stream());
		}
		if (yy < _y){
			res = Stream.concat(res, this.data.getOrDefault(new K(xx,yy+1), empty_bucket).stream());
		}
		
		return res;
	}
}
