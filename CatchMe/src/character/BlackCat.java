package character;

import java.awt.image.BufferedImage;

import anim.Sprite;
import game.Game;
import modele.Cat;

public class BlackCat extends Cat {




	public BlackCat(int x, int y, Game game) {
		super(x, y, game,
				// Création des séquences d'animation depuis le fichier png
				// toLeft
				new BufferedImage[]{
						Sprite.getSprite(8, 20, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(8, 21, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(8, 22, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(8, 23, spriteFileName, IMGWIDTH, IMGHEIGHT)
		},
				// toRight
				new BufferedImage[]{
						Sprite.getSprite(8, 20, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(8, 21, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(8, 22, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(8, 23, spriteFileName, IMGWIDTH, IMGHEIGHT, true)
		},
				// toUp
				new BufferedImage[]{
						Sprite.getSprite(6, 20, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(6, 21, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(6, 22, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(6, 23, spriteFileName, IMGWIDTH, IMGHEIGHT)
		},
				// toDown
				new BufferedImage[]{
						Sprite.getSprite(10, 20, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(10, 21, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(10, 22, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(10, 23, spriteFileName, IMGWIDTH, IMGHEIGHT)
		},
				// toTopLeft
				new BufferedImage[]{
						Sprite.getSprite(7, 20, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(7, 21, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(7, 22, spriteFileName, IMGWIDTH, IMGHEIGHT),
						Sprite.getSprite(7, 23, spriteFileName, IMGWIDTH, IMGHEIGHT)
		},
				// toTopRight
				new BufferedImage[]{
						Sprite.getSprite(7, 20, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(7, 21, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(7, 22, spriteFileName, IMGWIDTH, IMGHEIGHT, true),
						Sprite.getSprite(7, 23, spriteFileName, IMGWIDTH, IMGHEIGHT, true)
		},
				// Standing
				new BufferedImage[]{
						Sprite.getSprite(10, 21, spriteFileName, IMGWIDTH, IMGHEIGHT)
		}
				);
	}



}
