package com.runner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	private Game game;
	private Handler handler;
	private Menu menu;

	public KeyInput(Game game, Menu menu, Handler handler) {
		this.game = game;
		this.menu = menu;
		this.handler = handler;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (game.state) {
			case Menu:
				if (key == KeyEvent.VK_ESCAPE)
					game.state = State.ConfirmExit;
				break;

			case ConfirmExit:
				if (key == KeyEvent.VK_ESCAPE) {
					game.state = State.Menu;
					menu.doCapture = true;
				}

			case Game:
				if (key == KeyEvent.VK_SPACE) {
					int size = handler.objects.size();
					for (int i = 0; i < size; i++) {
						if (handler.objects.get(i).getId() == ID.Player) {
							Player tempObject = (Player) handler.objects.get(i);
							tempObject.jump();
						}
					}
				} else if (key == KeyEvent.VK_ESCAPE) {
					game.state = State.Pause;
					game.setCursorVisible(true);
				}
				break;

			case Pause:
				if (key == KeyEvent.VK_ESCAPE) {
					game.state = State.Game;
					game.doCapture = true;
					game.setCursorVisible(false);
				}
				break;

			case GameOver:
				if (key != KeyEvent.VK_ALT && key != KeyEvent.VK_CONTROL && key != KeyEvent.VK_SHIFT &&
						key != KeyEvent.VK_CAPS_LOCK && key != KeyEvent.VK_TAB) {
					game.state = State.Game;
					handler.objects.clear();
					game.newGame();
				}
				break;

				
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}