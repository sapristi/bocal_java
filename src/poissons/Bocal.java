package poissons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JPanel;

interface Distance_fun{double distance(AbstractPoisson o2);}
interface Dessine{void dessine(Graphics g);}



/** 
 * Classe Bocal : 
 * hérite de JPanel pour afficher l'animation.
 * 
 * Cette classe contient les Objets (les poissons et les obstacles)
 * dans des structures adaptées.
 * 
 * Les obstacles (dont font partie les murs) sont contenus dans un simple ArrayList (il y en a peu)
 * Les poissons (de tous types) sont contenus dans une classe Buckets, qui permet un accès rapide aux voisins
 * 
 * À chaque mise à jour du Bocal lancée par le scheduler de la classe Main, 
 * tous les Objets sont mis à jour, puis on appelle la méthode repaint
 * pour mettre à jour l'interface
 * 
 * Un mouselistener sert à ajouter des obstacles lors d'un clic de souris.
 * 
 *  */


public class Bocal extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 5368616486474475373L;
	private int width, height;
	public ArrayList<AbstractPoisson> poissons;
	public Buckets buckets;
	
	public ArrayList<Objet> obstacles;
	
	long time;
	
	public Bocal(int nb_poissons, int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.buckets = new Buckets(width, height, 30);
		
		
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.addMouseListener(this);
		
		this.poissons = new ArrayList<AbstractPoisson>(nb_poissons);
		for (int i = 0; i<nb_poissons; i++) {
			this.poissons.add(new Poisson((int)(Main.next_random() * width), (int)(Main.next_random() * height)));
		}
		this.poissons.add(new Predateur((int)(Main.next_random() * width), (int)(Main.next_random() * height)));
		
		
		this.obstacles = new ArrayList<Objet>();
		
		// ajoute les bords à la liste d'obstacles
		// Bord gauche
		this.obstacles.add(new Bord(o2 -> Math.sqrt(o2.x * o2.x)-20, 
									Math.PI,
									g -> {g.setColor(Color.RED);
										g.drawLine(0, 0, 0, this.height);}));
		// Bord droit
		this.obstacles.add(new Bord(o2 -> Math.sqrt((this.width - o2.x) * (this.width - o2.x))-20, 
									0,
									g -> {g.setColor(Color.BLUE);g.drawLine(this.width, 0, this.width, this.height);}));
		// Bord du haut
		this.obstacles.add(new Bord(o2 -> Math.sqrt(o2.y * o2.y) -20, 
									Math.PI*3/2,
									g -> {g.setColor(Color.GREEN);g.drawLine(0, 0, this.width, 0);}));
		// Bord du bas
		this.obstacles.add(new Bord(o2 -> Math.sqrt((this.height - o2.y) * (this.height - o2.y))-20, 
									Math.PI/2,
									g -> g.drawLine(0, this.height, this.width, this.height)));
	}
	
	public void update() {
		//System.out.println("mean direction : " + mean_dir/Math.PI);
		
		this.buckets.reset();
		this.poissons.stream().forEach(o -> this.buckets.register(o, (int)o.x, (int)o.y));
		
		this.poissons = this.poissons.stream().map(o->o.update(this)).filter(o->o.TTL>0).collect(Collectors.toCollection(ArrayList::new));;
		//obstacles.stream().forEach(o->o.update(this));
		this.obstacles = this.obstacles.stream().map(o->o.update(this)).filter(o->o.TTL>0).collect(Collectors.toCollection(ArrayList::new));
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.ORANGE); 
		
		
		long new_time = System.nanoTime();
		long elapsed_time = new_time - this.time;
		g.setColor(Color.black);
		g.drawString((int)(1000000000/((double)elapsed_time)) + "fps", 30, 40);
		this.time = new_time;
		
		int poissons_nb = poissons.size();
		g.drawString("nb poissons :" + poissons_nb, 30, 50);
		
		
		this.poissons.stream().forEach(o -> o.dessine(g));
		this.obstacles.stream().forEach(o -> o.dessine(g));
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY());
		this.obstacles.add(new Rocher(e.getX(), e.getY(), 25, 500));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}
	
}
