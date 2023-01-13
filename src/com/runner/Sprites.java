package com.runner;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprites {

	private static Sprites instance = null;

	public Image player;
	public Image deadPlayer;
	public Image obstacle;
	public Image jump;
	public Image jump2;

	private Sprites() {

		try {
			player = ImageIO.read(new File("res/images/player.png"));
			deadPlayer = ImageIO.read(new File("res/images/dead_player.png"));
			obstacle = ImageIO.read(new File("res/images/obstacle.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			jump = ImageIO.read(new File("res/images/jump.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			jump2 = ImageIO.read(new File("res/images/jump2.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static Sprites getInstance() {
		if (instance == null)
			instance = new Sprites();

		return instance;
	}


	
}
