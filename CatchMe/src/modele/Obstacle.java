package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import anim.Animation;
import game.Game;

public abstract class Obstacle {

	// Liste des obstacles
	protected static final String[] obstacleTypes = {"obstacle.Tree","obstacle.Rock","obstacle.Bush","obstacle.FlowerBush"};

	// Dimensions de la hit box
	protected final int WIDTH, HEIGHT;

	protected Animation animation;

	// Instance du jeu qui a créé l'obstacle
	protected Game game;

	// Position
	protected int x, y;

	/*public Obstacle(int width, int height) {
		Random r = new Random();		
		this.WIDTH = width;
		this.HEIGHT = height;

		this.x = r.nextInt(Game.FRAMEWIDTH);
		this.y = 0 - HEIGHT;
	}*/

	// Créer un obstacle aléatoirement mais dans une zone donnée de l'écran
	public Obstacle(Game game, int width, int height, String position) {
		int min, max;
		Random r = new Random();		
		this.WIDTH = width;
		this.HEIGHT = height;
		this.game = game;

		if (position.equals("left"))
		{
			min = 0;
			max = Game.FRAMEWIDTH / 2 - this.WIDTH;
		}else if (position.equals("right")){
			min = Game.FRAMEWIDTH / 2;
			max = Game.FRAMEWIDTH - this.WIDTH;
		}else{
			min = 0;
			max = Game.FRAMEWIDTH - this.WIDTH;
		}
		this.x = r.nextInt(max - min) + min;
		this.y = 0 - HEIGHT;
	}

	/*public Obstacle() {
		Random r = new Random();
		this.WIDTH = r.nextInt(60);
		this.HEIGHT = r.nextInt(60);
		this.x = r.nextInt(Game.FRAMEWIDTH);
		this.y = 0 - HEIGHT;
	}*/

	public void paint(Graphics2D g){
		if (animation != null){
			g.drawImage(animation.getSprite(), x, y, null);
			animation.update();
			animation.start();
		}else{
			g.setColor(Color.RED);
			g.fillOval(x, y, WIDTH, HEIGHT);
		}
		//drawHitbox(g);
	}

	public void update(){
		y += 2;
		collisionCharacter();
		collisionTarget();
	}

	public static final Obstacle createRandomObstacle (Game game){
		return createRandomObstacle(game, "all");
	}

	@SuppressWarnings("unchecked")
	public static final Obstacle createRandomObstacle(Game game, String position){
		Obstacle obs;
		Random r = new Random();
		int rand = r.nextInt(Obstacle.obstacleTypes.length);
		String randObsClassName = Obstacle.obstacleTypes[rand];			
		try {
			Class<modele.Obstacle> c = (Class<Obstacle>) Class.forName(randObsClassName);
			obs = c.getConstructor(Game.class, String.class).newInstance(game, position);
			return obs;
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Génère le domaine de collision de l'objet
	public abstract Shape getHitbox();

	/**
	 * Dessine la hitbox de l'obstacle
	 * @param g
	 */
	protected void drawHitbox(Graphics2D g){
		g.setColor(Color.RED);
		g.draw(getHitbox());
	}

	/**
	 * Renvoi vrai si l'obstacle est en collision avec le personnage du jeu
	 * @return
	 */
	public boolean collisionCharacter(){
		if (getHitbox().intersects((Rectangle2D) game.c.getHitbox()))
		{
			System.out.println("*COLLISION* - "+ toString() +" / "+game.c.toString());
			game.c.getCollided();
			return true;
		}else
			return false;
	}
	
	/**
	 * Gestion de la collision de l'obstacle avec la cible {@link Target}
	 * @return
	 */
	public boolean collisionTarget(){
		if (getHitbox().intersects((Rectangle2D) game.target.getHitbox()))
		{
			//System.out.println("*Target COLLISION* - "+ toString() +" / "+game.target.toString());
			game.target.getCollided();
			return true;
		}else
			return false;		
	}	
	
	/**
	 * Renvoi vrai si l'obstacle est sorti de l'écran (afin qu'il soit supprimé)
	 * @return
	 */
	public boolean isOutOfScreen(){
		if (y>Game.FRAMEHEIGHT)
			return true;
		else
			return false;
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}
}
