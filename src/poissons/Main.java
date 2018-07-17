package poissons;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

/**
 * 
 * Classe Main
 * 
 * Contient un générateur aléatoire, 
 * la JFRame de l'interface graphique,
 * le bocal dans lequel se trouvent les poissons
 * et le scheduler qui lance des mises à jour à intervalles réguliers
 *
 */


public class Main {


	private static Random rgenerator = new Random(System.nanoTime());
	public static double next_random() {return rgenerator.nextDouble();}
	
	
	public static int width = 1600;
	public static int height = 800;
	
	
	public static int nb_poissons = 600;
	
	public static Bocal bocal = new Bocal(nb_poissons, width, height);

	protected static Timer timer = new Timer();
	
	public static void main(String[] args) {
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TimerTask tache = new TimerTask() {
			@Override
			public void run() {
				bocal.update();
			}
		};
		timer.scheduleAtFixedRate(tache, 0, 20);
		
		frame.setContentPane(bocal);
		frame.pack();
		frame.setVisible(true);
	}
}
