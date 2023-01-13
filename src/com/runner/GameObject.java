package com.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected double x,y;
	protected double velX,velY;
	protected double width, height;
	protected Color color;
	protected ID id;

	public GameObject(double x, double y, double width, double height, Color color, ID id) {
		this.x = x;
		this.y = y;
		this.velX = 0;
		this.velY = 0;
		this.width = width;
		this.height = height;
		this.color = color;
		this.id = id;
	}
	
	public GameObject(double x, double y, double velX, double velY, double width, double height, Color color, ID id) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.width = width;
		this.height = height;
		this.color = color;
		this.id = id;
	}

	public abstract void tick();
	public abstract void render(Graphics g);

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ID getId() {
		return this.id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
}
