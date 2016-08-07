package anim;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Sprite {

	
	public static final java.awt.Image BACKGD = Toolkit.getDefaultToolkit().getImage(Sprite.class.getResource("grass_6.png"));
	
	private static String spriteFileName;
    private static BufferedImage spriteSheet;
    
    //private static final int TILE_WIDTH = 48;
    //private static final int TILE_HEIGHT = 48;

    public static BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(Sprite.class.getResource(file+".png"));
        	//sprite = ImageIO.read(new File(file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid, String spriteFileName, int tileWidth, int tileHeight) {

        if (Sprite.spriteFileName != spriteFileName) {
            spriteSheet = loadSprite(spriteFileName);
            Sprite.spriteFileName = spriteFileName;
        }

        return spriteSheet.getSubimage(xGrid * tileWidth, yGrid * tileHeight, tileWidth, tileHeight);
    }
    
    public static BufferedImage getSprite(int xGrid, int yGrid, String spriteFileName, int tileWidth, int tileHeight, boolean horizontalFlip) {

    	if (horizontalFlip){
        if (Sprite.spriteFileName != spriteFileName) {
            spriteSheet = loadSprite(spriteFileName);
            Sprite.spriteFileName = spriteFileName;
        }

        BufferedImage img = spriteSheet.getSubimage(xGrid * tileWidth, yGrid * tileHeight, tileWidth, tileHeight);

		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(img, null);
    	}else{
    		return getSprite(xGrid, yGrid, spriteFileName, tileWidth, tileHeight);
    	}
    }

}

