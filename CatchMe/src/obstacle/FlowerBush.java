package obstacle;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import anim.Animation;
import anim.Sprite;
import game.Game;
import modele.Obstacle;

public class FlowerBush extends Obstacle {

	// Distance entre position du sprite et position de la hitbox
	protected final int XHB = 7;
	protected final int YHB = 9;


/*	public FlowerBush() {
		super(58, 58);
		BufferedImage[] sprite = new BufferedImage[]{Sprite.getSprite(2, 0, "bushes", 75, 75)};
		this.animation = new Animation(sprite, 12);
	}*/

	public FlowerBush(Game game, String position) {
		super(game, 58, 58, position);
		BufferedImage[] sprite = new BufferedImage[]{Sprite.getSprite(2, 0, "bushes", 75, 75)};
		this.animation = new Animation(sprite, 12);
	}

	@Override
	public Shape getHitbox() {
		return (Shape) new Ellipse2D.Float(x+XHB, y+YHB, WIDTH, HEIGHT);
	}

}
