package game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import modele.Obstacle;

public class ObstacleManagement {

	private Game game;

	// Liste des obstacles à afficher
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	// Dernier passage du garbage collector (suppression des obstacles hors éran)
	private long lastTimeGarbage = System.currentTimeMillis()/1000;

	// Temps entre deux garbage collector (en s)
	private final long TIME_BETWEEN_GARBAGES = 10;

	// Chance d'apparation des obstacles
	private int difficulty = 10;

	public ObstacleManagement(Game g) {
		this.game = g;
	}

	public void paint(Graphics2D g){
		for(Obstacle o : obstacles){
			o.paint(g);			
		}
	}

	private void generateObstacle(){
		Random r = new Random();
		int rand = r.nextInt(1000);
		if (rand < difficulty){
			obstacles.add(0,Obstacle.createRandomObstacle(game, "left"));
			obstacles.add(0,Obstacle.createRandomObstacle(game, "right"));
			obstacles.add(0,Obstacle.createRandomObstacle(game, "all"));
		}
	}

	/**
	 *  Tente de générer un obstacle et met à jour tous les obstacles existants
	 */
	public void update(){
		generateObstacle();
		for(Obstacle o : obstacles){
			o.update();
		}

		// Supprime les obstacles sortis de l'écran toutes les x secondes
		if ((System.currentTimeMillis()/1000 - lastTimeGarbage) > TIME_BETWEEN_GARBAGES){
			garbageCollector();
		}
	}

	/**
	 *  Supprime les obstacles sortis de l'écran
	 */
	private void garbageCollector(){
		Iterator<Obstacle> i = obstacles.iterator();
		int count = 0;
		while (i.hasNext()) {
			Obstacle o = i.next();
			if (o.isOutOfScreen())
			{
				i.remove();
				count++;
			}
		}
		this.lastTimeGarbage = System.currentTimeMillis()/1000;
		System.out.println("[Garbage Collector at "+lastTimeGarbage+" - Obstacles deleted : "+count+"]");
	}

}