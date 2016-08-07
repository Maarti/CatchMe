package modele;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import anim.Animation;
import game.Game;

public abstract class Cat extends Character {	

	// Nom du fichier du sprite (sans le ".png") + dimensions img
	protected static final String spriteFileName="cats";

	// Dimension du sprite
	public static final int IMGHEIGHT = 48;
	public static final int IMGWIDTH = 48;

	
	// Vitesse de défilement des animations
	protected final int WALKANIMSPEED=9;		// vitesse de l'animation de marche (0-12 : 0 est le + rapide)
	protected final int RUNANIMSPEED=4;			// vitesse de l'animation de course (0-12 : 0 est le + rapide)
	protected final int STANDANIMSPEED=12;		// vitesse de l'animation d'attente (0-12 : 0 est le + rapide)

	// Animations
	protected Animation walkLeft;
	protected Animation walkRight;
	protected Animation walkUp;
	protected Animation walkDown;
	protected Animation runLeft;
	protected Animation runRight;
	protected Animation runUp;
	protected Animation runDown;
	protected Animation runTopLeft;
	protected Animation runTopRight;
	protected Animation stand;
		
	// L'animation actuelle
	protected Animation animation;


	public Cat(int x, int y, Game game, BufferedImage[] toLeft, BufferedImage[] toRight,
			BufferedImage[] toUp, BufferedImage[] toDown, BufferedImage[] toTopLeft,  BufferedImage[] toTopRight, BufferedImage[] standing) {
		super(x, y, game);
	
		 this.walkLeft = new Animation(toLeft, WALKANIMSPEED);
		 this.walkRight = new Animation(toRight, WALKANIMSPEED);
		 this.walkUp = new Animation(toUp, WALKANIMSPEED);
		 this.walkDown = new Animation(toDown, WALKANIMSPEED);
		 this.runLeft = new Animation(toLeft, RUNANIMSPEED);
		 this.runRight = new Animation(toRight, RUNANIMSPEED);
		 this.runUp = new Animation(toUp, RUNANIMSPEED);
		 this.runDown = new Animation(toDown, RUNANIMSPEED);
		 this.runTopLeft = new Animation(toTopLeft, RUNANIMSPEED);
		 this.runTopRight = new Animation(toTopRight, RUNANIMSPEED);
		 this.stand = new Animation(standing, STANDANIMSPEED);
		 
		 this.animation = runUp;
	}
	
	public void move() {
		if (x + xa > 0 && x + xa < game.getWidth() - IMGWIDTH)
			x += xa;
		if (y + ya > 0 && y + ya < game.getHeight() - IMGHEIGHT)
			y += ya;
	}

	public void paint(Graphics2D g){
		g.drawImage(animation.getSprite(), x, y, null);
		//drawHitbox(g);
		animation.update();
		animation.start();
	}

	protected void moveLeft() {
		xa=-3;
		animation = runTopLeft;
	    animation.start();
	}

	protected void moveRight() {
		xa=3;
		animation = runTopRight;
	    animation.start();
	}
	
	protected void moveUp() {
		ya=1;
		animation = runUp;
	    animation.start();
	}

	protected void moveStop() {
		xa=0;
		ya=0;
		animation=runUp;
		animation.start();
	}
	
	public Shape getHitbox(){
		return (Shape) new Rectangle(x+15,y+25,WIDTH,HEIGHT);
	}
	
}

