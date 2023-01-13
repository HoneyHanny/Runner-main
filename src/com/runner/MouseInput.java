package com.runner;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

	private Game game;
	private Menu menu;
	private Handler handler;

	public MouseInput(Game game, Menu menu, Handler handler) {
		this.game = game;
		this.menu = menu;
		this.handler = handler;
	}

	@Override
	public void mouseDragged(MouseEvent e) { }
		
	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {

		int mx = e.getX();
		int my = e.getY();

		if (game.state == State.Menu) {
			Box bxPlay = menu.bxPlay;
			Box bxExit = menu.bxExit;

			if (GameUtilities.mouseOver(mx, my, (int)bxPlay.getX(), (int)bxPlay.getY(),
					(int)bxPlay.getWidth(), (int)bxPlay.getHeight())) {
						game.state = State.Game;
						game.newGame();
			} else if (GameUtilities.mouseOver(mx, my, (int)bxExit.getX(), (int)bxExit.getY(),
					(int)bxExit.getWidth(), (int)bxExit.getHeight())) {
						game.state = State.ConfirmExit;
			}
			
		} else if (game.state == State.ConfirmExit) {
			Box bxConfirm = menu.bxConfirm;
			Box bxCancel = menu.bxCancel;

			if (GameUtilities.mouseOver(mx, my, (int)bxConfirm.getX(), (int)bxConfirm.getY(),
					(int)bxConfirm.getWidth(), (int)bxConfirm.getHeight())) {
						System.exit(0);
			} else if (GameUtilities.mouseOver(mx, my, (int) bxCancel.getX(), (int) bxCancel.getY(),
					(int) bxCancel.getWidth(), (int) bxCancel.getHeight())) {
						menu.doCapture = true;
						game.state = State.Menu;
			}
		} else if (game.state == State.Pause) {
			Box bxResume = game.bxResume;
			Box bxExit = game.bxExit;

			if (GameUtilities.mouseOver(mx, my, (int) bxResume.getX(), (int) bxResume.getY(),
					(int) bxResume.getWidth(), (int) bxResume.getHeight())) {
						game.state = State.Game;
						game.setCursorVisible(false);
			} else if (GameUtilities.mouseOver(mx, my, (int) bxExit.getX(), (int) bxExit.getY(),
					(int) bxExit.getWidth(), (int) bxExit.getHeight())) {
						handler.objects.clear();
						game.state = State.Menu;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) {	}
	
	
	
}
