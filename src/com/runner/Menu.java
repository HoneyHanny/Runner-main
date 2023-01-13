package com.runner;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {

	public boolean doCapture = true;

	public Box bxOverlay;
	public Box bxPlay;
	public Box bxExit;
	public Box bxConfirm;
	public Box bxCancel;

	private boolean tImgLoaded;

	private Image title;
	private Game game;
	private BufferedImage back;

	public Menu(Game game) {
		this.game = game;

		try {
			BufferedImage img = ImageIO.read(new File("res/images/title.png"));
			title = img.getScaledInstance(400, 225, Image.SCALE_SMOOTH);
			tImgLoaded = true;
		} catch (IOException e) {
			tImgLoaded = false;
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		/* Draw title */
		if (!tImgLoaded) {
			g.setColor(Color.RED);
			g.setFont(new Font("Aquire", Font.PLAIN, 150));
			Text titleText = new Text(Game.TITLE, 0, 150);
			int x = GameUtilities.getCenterHorizontal(g.getFontMetrics().stringWidth(titleText.getText()));
			titleText.getPoint().setLocation(x, 100 + g.getFontMetrics().getHeight());
			titleText.renderText(g);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(title, GameUtilities.getCenterHorizontal(title.getWidth(null)), 100, null);
		}
	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Aquire", Font.PLAIN, 20));

		/* Main menu buttons */

		int x, y, width, height;

		width = 200;
		height = 100;
		x = GameUtilities.getCenterHorizontal(width);
		y = 400;
		bxPlay = new Box(x, y, width, height);
		Text txtPlay = new Text("Play");
		txtPlay.renderCenteredTextInsideBox(g, bxPlay);
		g.drawRoundRect(x, y, width, height, 20, 20);

		y = 550;
		bxExit = new Box(x, y, width, height);
		Text txtExit = new Text("Exit");
		txtExit.renderCenteredTextInsideBox(g, bxExit);
		g.drawRoundRect(x, y, width, height, 20, 20);
		

		/* Confirm Exit */
		if (game.state == State.ConfirmExit) {

			Text txtConfirm = new Text("Confirm");
			Text txtCancel = new Text("Cancel");


			width = 400;
			height = 400;
			x = GameUtilities.getCenterHorizontal(width);
			y = GameUtilities.getCenterVertical(height);
			bxOverlay = new Box(x, y, width, height);

			width = 200;
			height = 100;
			x = GameUtilities.getCenterHorizontalInBox(width, bxCancel);

			y = 300;
			bxConfirm = new Box(x, y, width, height);

			y = 450;
			bxCancel = new Box(x, y, width, height);


			final float[] matrix = {
				1 / 9f, 1 / 9f, 1 / 9f,
				1 / 9f, 1 / 9f, 1 / 9f,
				1 / 9f, 1 / 9f, 1 / 9f,
			};

			// final float[] matrix = {
			// 		0.111f, 0.111f, 0.111f,
			// 		0.111f, 0.111f, 0.111f,
			// 		0.111f, 0.111f, 0.111f,
			// };

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
			x = bxConfirm.x;
			y = bxConfirm.y;
			width = bxConfirm.width;
			height = bxConfirm.height;
			g2d.fillRoundRect(x, y, width, height, 20, 20);
			txtConfirm.renderCenteredTextInsideBox(g, bxConfirm);

			g2d.setColor(Color.RED);
			x = bxCancel.x;
			y = bxCancel.y;
			width = bxCancel.width;
			height = bxCancel.height;
			g2d.fillRoundRect(x, y, width, height, 20, 20);
			txtCancel.renderCenteredTextInsideBox(g, bxCancel);

			g2d.dispose();
		}

	}
	
}