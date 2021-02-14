package com.weather.model;

public class Wind {

	private float speed;
	private int deg;

	public Wind() {
	}

	public Wind(float speed, int deg) {
		this.speed = speed;
		this.deg = deg;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getDeg() {
		return deg;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}

}
