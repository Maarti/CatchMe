package obstacle;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import anim.Animation;
import anim.Sprite;
import game.Game;
import modele.Obstacle;

public class Tree extends Obstacle {

	// Distance entre position du sprite et position de la hitbox
	protected final int XHB = 0;
	protected final int YHB = 0;
	
	/*public Tree() {
		super(32, 64);
		BufferedImage[] sprite = new BufferedImage[]{Sprite.getSprite(0, 0, "2trees", 32, 64)};
		this.animation = new Animation(sprite, 12);
	}*/

	public Tree(Game game, String position) {
		super(game, 32, 64, position);
		BufferedImage[] sprite = new BufferedImage[]{Sprite.getSprite(0, 0, "2trees", 32, 64)};
		this.animation = new Animation(sprite, 12);
	}

	@Override
	public Shape getHitbox() {
		return (Shape) new Ellipse2D.Float(x+XHB, y+YHB, WIDTH, HEIGHT);
	}
}
