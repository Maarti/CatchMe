package obstacle;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import game.Game;
import modele.Obstacle;

public class Rock extends Obstacle {
	
	// Distance entre position du sprite et position de la hitbox
	protected final int XHB = 0;
	protected final int YHB = 0;
	
	/*public Rock() {
		super(30, 30);		
	}*/

	public Rock(Game game, String position) {
		super(game, 30, 30, position);		
	}


	@Override
	public Shape getHitbox() {
		return (Shape) new Ellipse2D.Float(x+XHB, y+YHB, WIDTH, HEIGHT);
	}
}
