package com.runner;

import javax.swing.JFrame;

public class Window extends JFrame {

	public Window(Game game) {
		setTitle(Game.TITLE);
		setUndecorated(true);
		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(game);
		setVisible(true);
	}

}