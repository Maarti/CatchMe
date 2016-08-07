package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import game.Game;

public abstract class Target {

	// Dimensions de la hit box
	protected final int WIDTH = 16;
	protected final int HEIGHT = 16;
	
	// Dimension du sprite
	public int IMGHEIGHT;
	public int IMGWIDTH;

	// Position
	protected int x;
	protected int y;
	
	// Direction de la cible en cas de collision (+1 = droite, -1 = gauche)
	protected int collisionDirection = 2;
	
	// Dernière fois que la Cible ait ralenti
	private long lastTimeSlow = System.currentTimeMillis()/1000;
	
	// Temps entre 2 ralentissemnt de la cible (en s)
	private long TIME_BETWEEN_SLOW = 1;
	
	// Nb de pixel dont la cible descend à chaque raentissement
	private int slowSpeed = 2;
	
	// Instance du jeu qui a créé la cible
	protected Game game;
	
	public Target(int x, int y, int imgheight, int imgwidth, Game game) {
		super();
		this.x = x;
		this.y = y;
		this.IMGHEIGHT = imgheight;
		this.IMGWIDTH = imgwidth;
		this.game = game;
	}

	public void paint(Graphics2D g){
		g.setColor(Color.CYAN);
		g.fillRect(x, y, IMGWIDTH, IMGHEIGHT);
		drawHitbox(g);
	}
	
	/**
	 * Dessine la hitbox de la cible (pour le debug)
	 * @param g
	 */
	protected void drawHitbox(Graphics2D g){
		g.setColor(Color.GREEN);
		g.draw(getHitbox());
	}
	
	/**
	 * Retourne la hitbox de la cible (par rapport à ses coordonnées x, y)
	 * @return
	 */
	public Shape getHitbox(){
		return (Shape) new Rectangle(x-(WIDTH-IMGWIDTH)/2,y-(HEIGHT-IMGHEIGHT)/2,WIDTH,HEIGHT);
		//return (Shape) new Ellipse2D.Float(x,y,WIDTH,HEIGHT);
	}
	
	public void getCollided(){
		x += collisionDirection;
	}
	
	public void update() {
		// Gestion de la direction en cas de collision
		if (collisionDirection<0 && x <= IMGWIDTH+15)
			collisionDirection = 2;
		else if(collisionDirection>0 && x >= Game.FRAMEWIDTH-(IMGWIDTH+15))
			collisionDirection = -2;
		
		// Gestion du ralentissement de la cible
		if ((System.currentTimeMillis()/1000 - lastTimeSlow) > TIME_BETWEEN_SLOW){
			slow();
		}
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Fait ralentir la cible.<br/>
	 * La Cible descend d'un nombre de pixel (slowSpeed) toutes les TIME_BETWEEN_SLOW secondes
	 */
	public void slow(){
		this.y += slowSpeed;
		this.lastTimeSlow = System.currentTimeMillis()/1000;
		System.out.println(toString() + " ralenti de "+slowSpeed);
	}
}
