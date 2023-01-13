package com.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

public class Obstacle extends GameObject {

	Random random;
	
	public Obstacle(double x, double y, double width, double height, Color color, ID id) {
		super(x, y, width, height, color, id);
		velX = -10;
		random = new Random();
	}

	@Override
	public void tick() {

		x += velX;
		
		if (x + width < 0)
			x = random.nextDouble((double) Game.WIDTH) + (double)Game.WIDTH;

	}

	@Override
	public void render(Graphics g) {
		// g.setColor(color);
		// g.fillRect((int)x, (int)y, (int)width, (int)height);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(Sprites.getInstance().obstacle, (int)x, (int)y, null);

	}
	
}
