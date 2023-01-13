package com.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Player extends GameObject {

	private boolean jump = false;
	private int anim = 1;

	private Game game;
	private Handler handler;

	// public Player(double x, double y, double velX, double velY, double width, double height) {
	// 	super(x, y, velX, velY, width, height);	
	// }

	public Player(double x, double y, double width, double height, Color color, ID id, Handler handler, Game game) {
		super(x, y, 0, 10, width, height, color, id);
		this.handler = handler;
		this.game = game;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

		anim++;
		if (anim == 10)
			anim = 0;

		y = GameUtilities.clamp(0, 500, y);
		velY += 1;
		velY = GameUtilities.clamp(-100, 50, velY);
		if (y == 500 && jump)
			jump = false;

		collisionCheck();

	}

	@Override
	public void render(Graphics g) {
		// g.setColor(color);
		// g.fillRect((int)x, (int)y, (int)width, (int)height);

		Graphics2D g2d = (Graphics2D) g;		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		if (game.state == State.Game || game.state == State.Pause) {
			g2d.drawImage(Sprites.getInstance().player, (int)x, (int)y, null);
			if (jump) {
				if (anim < 5) {
					g2d.drawImage(Sprites.getInstance().jump, (int) x, (int) (y + height), null);
				} else if (anim < 10) {
					g2d.drawImage(Sprites.getInstance().jump2, (int) x, (int) (y + height), null);
				}
			}
		} else {
			g2d.drawImage(Sprites.getInstance().deadPlayer, (int)x, (int)y, null);
		}
	}

	public void jump() {
		if (!jump) {
			velY = -25;
			jump = true;
		}
	}

	private void collisionCheck() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);

			if (tempObject.getId() == ID.Obstacle) {
				if (getBounds().intersects(tempObject.getBounds())) {
					game.state = State.GameOver;
					game.saveScores();
				}
			}
			
		}
	}

}
