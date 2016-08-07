package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import game.Game;


public abstract class Character {
		
	// Dimensions de la hit box
	protected final int WIDTH = 16;
	protected final int HEIGHT = 18;
	
	// Position
	protected int x;
	protected int y;
	
	// Vitesse de déplacement
	protected int xa=0;
	protected int ya=0;
	
	// Instance du jeu qui a créé le personnage
	protected Game game;

	public Character(int x, int y, Game game) {
		super();
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public abstract void paint(Graphics2D g);
	
	public abstract void move();
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			moveLeft();
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRight();
		}
	}

	public void keyReleased(KeyEvent e) {
		moveStop();
	}
	
	protected abstract void moveLeft();
	protected abstract void moveRight();
	protected abstract void moveUp();
	protected abstract void moveStop();
	
	/**
	 *  Génère le domaine de collision de l'objet
	 * @return
	 */
	public abstract Shape getHitbox();
	
	/**
	 * Dessine la hitbox du personnage (pour le debug)
	 * @param g
	 */
	protected void drawHitbox(Graphics2D g){
		g.setColor(Color.GREEN);
		g.draw(getHitbox());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Réaction en cas de collision
	 */
	public void getCollided(){
		this.y++;
	}
	
}
