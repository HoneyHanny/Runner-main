package com.runner;

import java.awt.Point;

public final class GameUtilities {

	public final static int clamp(int min, int max, int val) {
		if (val < min)
			return min;
		if (val > max)
			return max;

		return val;
	}

	public static final double clamp(double min, double max, double val) {
		if (val < min)
			return min;
		if (val > max)
			return max;

		return val;
	}

	public static final Point getCenterPoint(int width, int height) {
		return new Point(getCenterHorizontal(width), getCenterVertical(height));
	}
	
	public static final int getCenterHorizontal(int width) {
		return Game.WIDTH / 2 - width / 2;
	}
	
	public static final int getCenterVertical(int height) {
		return Game.HEIGHT / 2 - height / 2;
	}

	public static final Point getCenterPointInBox(int width, int height, Box box) {
		return new Point(getCenterHorizontalInBox(width, box), getCenterVerticalInBox(height, box));
	}

	public static final int getCenterHorizontalInBox(int width, Box box) {
		return Game.WIDTH / 2 - width / 2;
	}

	public static final int getCenterVerticalInBox(int height, Box box) {
		return Game.HEIGHT / 2 - height / 2;
	}
	
	public static final boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if ((mx > x && mx < x + width) && (my > y && my < y + height))
			return true;
		return false;
	}
	
}
