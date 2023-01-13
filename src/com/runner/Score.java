package com.runner;

import java.io.Serializable;

public class Score implements Serializable {

	public Score() {
		highestScore = 0;
		currentScore = 0;
	}

	public int highestScore;
	public double currentScore;
	
}
