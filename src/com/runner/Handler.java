package com.runner;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();

	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.render(g);
		}
	}
	
}
