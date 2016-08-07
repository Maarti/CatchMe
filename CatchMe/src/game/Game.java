package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import anim.Sprite;
import character.BlackCat;
import modele.Cat;
import modele.Character;
import modele.Target;
import target.Mouse;

@SuppressWarnings("serial")
public class Game extends JPanel {

	private ObstacleManagement om = new ObstacleManagement(this);
	private int backgdY;	
	public static final int FRAMEWIDTH = 400; //394
	public static final int FRAMEHEIGHT = 500; //472
	public Character c = (modele.Character) new BlackCat((FRAMEWIDTH-Cat.IMGWIDTH)/2, FRAMEHEIGHT-(Cat.IMGHEIGHT+30), this);
	//public Target target = (modele.Target) new Target((FRAMEWIDTH-Target.IMGWIDTH)/2, 10, this);
	public Target target = (modele.Target) new Mouse((FRAMEWIDTH-Mouse.IMGWIDTH)/2, 10, this);
	private boolean running = true;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;
	private int score = 0;

	public Game() {
		super();
		backgdY = Sprite.BACKGD.getHeight(null)*(-1);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P)
					switchGamePause();
				c.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				c.keyReleased(e);
			}
		});
		setFocusable(true);
		this.om = new ObstacleManagement(this);
	}

	public static void main(String [ ] args) throws InterruptedException
	{
		JFrame frame = new JFrame("Catch Me If You Can");
		Game game = new Game();		

		frame.add(game);
		// Pour définir la dimension de la partie jouable de l'écran (sans les bordures de la fenêtre)
		frame.getContentPane().setPreferredSize(new Dimension(FRAMEWIDTH-10, FRAMEHEIGHT-10));
		//frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
		frame.pack();		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		game.runGameLoop();
	}


	//Starts a new thread and runs the game loop in it.
	private void runGameLoop()
	{
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}


	//Only run this in another Thread!
	private void gameLoop()
	{
		//This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running)
		{
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused)
			{
				//Do as many game updates as we need to, potentially playing catchup.
				while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
				{
					updateGame();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				//If for some reason an update takes forever, we don't want to do an insane number of catchups.
				//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
				{
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				//Render. To do so, we need to calculate interpolation for a smooth render.
				// float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
				repaint();
				//  drawGame(interpolation);
				lastRenderTime = now;

				//Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime)
				{
					//System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
				}

				//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
				{
					Thread.yield();

					//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
					//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
					//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
					try {Thread.sleep(1);} catch(Exception e) {} 

					now = System.nanoTime();
				}
			}
		}
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);	//Efface l'écran
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		drawBackgd(g2d);		
		om.paint(g2d);
		target.paint(g2d);
		c.paint(g2d);
		drawFps(g2d);
		drawScore(g2d);
		frameCount++;
		
		if (paused)
			drawPause(g2d);
	}

	private void drawBackgd(Graphics2D g){
		for(int i=0;i<this.getWidth()+Sprite.BACKGD.getWidth(null);i+=Sprite.BACKGD.getWidth(null)){
			for(int j=backgdY;j<this.getHeight()+Sprite.BACKGD.getHeight(null);j+=Sprite.BACKGD.getHeight(null)){			
				g.drawImage(Sprite.BACKGD, i, j, null);
			}
		}
	}

	private void drawFps(Graphics2D g){
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + fps, FRAMEWIDTH-50, 10);         
	}
	
	private void drawScore(Graphics2D g){
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		g.drawString("Score : " + score/10, 5, 15);         
	}

	private void updateGame(){
		moveBackground();
		c.move();
		om.update();
		target.update();
		score++;
	}

	private void moveBackground(){
		if (backgdY<0)
			backgdY+=2;
		else
			backgdY=Sprite.BACKGD.getHeight(null)*(-1);
	}

	public void switchGamePause(){
		if (this.paused)
			this.paused = false;
		else
			this.paused = true;
		repaint();
	}

	private void drawPause(Graphics2D g){
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Console", Font.ITALIC, 24));
		g.drawString("PAUSED", (this.getWidth()-24*3)/2, (this.getHeight()+24)/2);
	}

}
