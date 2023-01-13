package com.runner;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.MemoryImageSource;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {

	public static final String TITLE = "Runner";
	public static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	public static final double DWIDTH = SCREEN.getWidth();
	public static final double DHEIGHT = SCREEN.getHeight();
	public static final int WIDTH = (int) DWIDTH;
	public static final int HEIGHT = (int) DHEIGHT;

	public boolean doCapture = true;

	public Score scores;
	public State state;
	public Box bxResume;
	public Box bxExit;

	private static String SCORE_FILE = "data/scores.txt";

	private boolean running;
	private boolean imgLoaded;
	
	private Thread thread;
	private Handler handler;
	private Menu menu;
	private MouseInput mi;
	private Random r;
	private BufferedImage back;
	private Image imgGameOver;
	private Box bxOverlay;

	public Game() {
		loadScores();
		if (scores == null)
			scores = new Score();

		running = false;
		state = State.Menu;
		new Window(this);
		handler = new Handler();

		menu = new Menu(this);

		mi = new MouseInput(this, menu, handler);
		addKeyListener(new KeyInput(this, menu, handler));
		addMouseListener(mi);
		addMouseMotionListener(mi);

		r = new Random();

		try {
			imgGameOver = ImageIO.read(new File("res/images/game_over.png"));
			imgLoaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		start();
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.run();
	}

	private synchronized void stop() {
		if (!running)
			return;

		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			render();
		}

		stop();
	}

	private void tick() {
		if (state == State.Game) {
			handler.tick();
			scores.currentScore += .01;
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		/* Start draw */

		/* Render background */
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		/* Render based on game state */
		switch (state) {
			case Menu:
			case ConfirmExit:
				menu.render(g); // Render menu
				break;

			case Game:
				handler.render(g); // Render game
				renderGame(g);
				break;

			case Pause:
				handler.render(g);
				renderGame(g);
				renderPause(g);
				break;

			case GameOver:
				handler.render(g);
				renderGame(g);
				renderGameOver(g);
				break;
		}

		// Show status
		// g.drawString(state.toString(), 100, 100);

		/* End draw */

		g.dispose();
		bs.show();
	}

	public void newGame() {
		int x = 400;
				
		int size = 50;
		int y = 600 - size;
		for (int i = 0; i < 5; i++) {
			x = r.nextInt(WIDTH) + WIDTH;
			handler.objects.add(new Obstacle(x, y, size, size, Color.RED, ID.Obstacle));
		}

		x = 400;
		y = 600;
		size = 100;
		handler.objects.add(new Player(x, y, size, size, Color.MAGENTA, ID.Player, handler, this));

		scores.currentScore = 0;
		doCapture = true;

		setCursorVisible(false);
		
	}

	public void setCursorVisible(boolean visible) {

		if (visible) {
			setCursor(null);
		} else {
			int[] pixels = new int[16 * 16];
			Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
			Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0),
					"invisibleCursor");
			setCursor(transparentCursor);
		}

	}

	private void renderGame(Graphics g) {
		renderFloor(g);
		renderScore(g);
	}

	private void renderFloor(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawLine(0, 600, WIDTH, 600);
	}

	private void renderGameOver(Graphics g) {
		if (!imgLoaded) {
			g.setFont(new Font("Aquire", Font.PLAIN, 50));
			String txt = "Game Over";
			FontMetrics fm = g.getFontMetrics();
			g.drawString(txt, GameUtilities.getCenterHorizontal(fm.stringWidth(txt)), GameUtilities.getCenterVertical(fm.getHeight()));
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(imgGameOver, GameUtilities.getCenterHorizontal(imgGameOver.getWidth(null)), 0, null);
		}
	}

	private void renderScore(Graphics g) {

		int x = 60;
		int y = 70;

		g.setColor(Color.CYAN);
		g.setFont(new Font("Aquire", Font.PLAIN, 30));
		
		g.drawString("Score " + (int)Math.floor(scores.currentScore), x, y);
		y = 100;
		g.drawString("Highest " + scores.highestScore, x, y);

	}

	private void renderPause(Graphics g) {
		Text txtConfirm = new Text("Resume");
		Text txtCancel = new Text("Exit");

		int width = 400;
		int height = 400;
		int x = GameUtilities.getCenterHorizontal(width);
		int y = GameUtilities.getCenterVertical(height);
		bxOverlay = new Box(x, y, width, height);

		width = 200;
		height = 100;
		x = GameUtilities.getCenterHorizontalInBox(width, bxExit);

		y = 300;
		bxResume = new Box(x, y, width, height);

		y = 450;
		bxExit = new Box(x, y, width, height);

		final float[] matrix = {
				1 / 9f, 1 / 9f, 1 / 9f,
				1 / 9f, 1 / 9f, 1 / 9f,
				1 / 9f, 1 / 9f, 1 / 9f,
		};

		try {
			if (doCapture) {
				Robot robot = new Robot();
				back = robot.createScreenCapture(bxOverlay);
				doCapture = false;
			}
			BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
			BufferedImage blur = op.filter(back, null);
			g.drawImage(blur, bxOverlay.x, bxOverlay.y, null);
		} catch (AWTException e) {
			e.printStackTrace();
		}

		g.setColor(new Color(1f, 1f, 1f, .1f));
		bxOverlay.fillBox(g);

		g.setColor(Color.WHITE);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.GREEN);

		g2d.setStroke(new BasicStroke(3f));
		x = bxResume.x;
		y = bxResume.y;
		width = bxResume.width;
		height = bxResume.height;
		g2d.fillRoundRect(x, y, width, height, 20, 20);
		txtConfirm.renderCenteredTextInsideBox(g, bxResume);

		g2d.setColor(Color.RED);
		x = bxExit.x;
		y = bxExit.y;
		width = bxExit.width;
		height = bxExit.height;
		g2d.fillRoundRect(x, y, width, height, 20, 20);
		txtCancel.renderCenteredTextInsideBox(g, bxExit);

		g2d.dispose();
		
	}

	public void saveScores() {

		if (scores.currentScore < scores.highestScore)
			return;

		scores.highestScore = (int) scores.currentScore;

		try {
			FileOutputStream fos = new FileOutputStream(SCORE_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(scores);

			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadScores() {
		try {
			FileInputStream fis = new FileInputStream(SCORE_FILE);
			ObjectInputStream ois = new ObjectInputStream(fis);

			scores = (Score) ois.readObject();

			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Game();
	}
	
}