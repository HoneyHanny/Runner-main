package com.runner;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Text {

	private String text;
	private Point point;

	public Text() {
		text = "";
		point = new Point(0, 0);
	}

	public Text(Point point) {
		text = "";
		this.point = point;
	}

	public Text(String text) {
		this.text = text;
		point = new Point(0, 0);
	}

	public Text(String text, Point point) {
		this.text = text;
		this.point = point;
	}

	public Text(String text, int x, int y) {
		this(text, new Point(x, y));
	}

	public void renderCenteredTextInsideBox(Graphics g, Rectangle box) {
		Point centerPoint = getCenterTextInBoxPosition(g, box.x, box.y, box.width, box.height);
		g.drawString(text, centerPoint.x, centerPoint.y);
	}

	public void renderCenteredTextInsideBox(Graphics g, Point boxPoint, int width, int height) {
		renderCenteredTextInsideBox(g, new Rectangle(boxPoint.x, boxPoint.y, width, height));
	}

	public void renderCenteredTextInsideBox(Graphics g, int x, int y, int width, int height) {
		renderCenteredTextInsideBox(g, new Point(x, y), width, height);
	}

	public void renderTextInCenter(Graphics g, int width, int height) {
		Point center = getCenterTextOnScreenPosition(g, width, height);
		g.drawString(text, center.x, center.y);
	}

	public void renderTextInCenter(Graphics g) {
		Point center = getCenterTextOnScreenPosition(g);
		g.drawString(text, center.x, center.y);
	}

	public void renderText(Graphics g) {
		g.drawString(text, point.x, point.y);
	}

	public Point getCenterTextOnScreenPosition(Graphics g, int width, int height) {
		int x, y;
		x = width / 2 - g.getFontMetrics().stringWidth(text) / 2;
		y = height / 2 - g.getFontMetrics().getHeight() / 2;
		return new Point(x, y);
	}

	public Point getCenterTextOnScreenPosition(Graphics g) {
		return getCenterTextOnScreenPosition(g, Game.WIDTH, Game.HEIGHT);
	}

	public Point getCenterTextInBoxPosition(Graphics g, Rectangle box) {
		int cx, cy;
		FontMetrics fm = g.getFontMetrics();
		int stringWidth = fm.stringWidth(text);
		int stringHeight = fm.getHeight();

		cx = box.x + box.width / 2 - stringWidth / 2;
		cy = box.y + box.height / 2 + stringHeight / 2;
		return new Point(cx, cy);
	}

	public Point getCenterTextInBoxPosition(Graphics g, Point boxPoint, int width, int height) {
		return getCenterTextInBoxPosition(g, new Rectangle(boxPoint.x, boxPoint.y, width, height));
	}

	public Point getCenterTextInBoxPosition(Graphics g, int x, int y, int width, int height) {
		return getCenterTextInBoxPosition(g, new Point(x, y), width, height);
	}

	public String getText() {
		return this.text;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
