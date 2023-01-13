package com.runner;

import java.awt.Rectangle;
import java.awt.Graphics;

public class Box extends Rectangle {

	public int arcWidth;
	public int arcHeight;

	public Box(int x, int y, int width, int height) {
		super(x, y, width, height);
		arcWidth = 0;
		arcHeight = 0;
	}

	public Box(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		super(x, y, width, height);
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
	}

	public void drawBox(Graphics g) {
		g.drawRect(x, y, width, height);
	}

	public void drawRoundBox(Graphics g, int arcWidth, int arcHeight) {
		g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	public void drawRoundBox(Graphics g, int arc) {
		drawRoundBox(g, arc, arc);
	}

	public void fillBox(Graphics g) {
		g.fillRect(x, y, width, height);
	}

	public void fillRoundBox(Graphics g, int arcWidth, int arcHeight) {
		g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	public void fillRoundBox(Graphics g, int arc) {
		g.fillRoundRect(x, y, width, height, arc, arc);
	}

}