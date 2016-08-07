package target;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import anim.Animation;
import anim.Sprite;
import game.Game;
import modele.Target;

public class Mouse extends Target {

	// Nom du fichier du sprite (sans le ".png") + dimensions img
	protected static final String spriteFileName="mouse";

	// Sprites	
	protected BufferedImage[] toTop = new BufferedImage[]{
			Sprite.getSprite(0, 3, spriteFileName, IMGWIDTH, IMGHEIGHT),
			Sprite.getSprite(1, 3, spriteFileName, IMGWIDTH, IMGHEIGHT),
			Sprite.getSprite(2, 3, spriteFileName, IMGWIDTH, IMGHEIGHT),
			Sprite.getSprite(3, 3, spriteFileName, IMGWIDTH, IMGHEIGHT)};
	
	// Animations
	protected Animation runTop = new Animation(toTop, 3);
	
	// Animation actuelle
	Animation animation = runTop;
	
	// Dimension du sprite
	public static final int IMGHEIGHT = 16;
	public static final int IMGWIDTH = 16;


	public Mouse(int x, int y, Game game) {
		super(x, y, IMGHEIGHT, IMGWIDTH, game);
	}


	@Override
	public void paint(Graphics2D g) {
		g.drawImage(animation.getSprite(), x, y, null);
		//drawHitbox(g);
		animation.update();
		animation.start();
	}

	
}
