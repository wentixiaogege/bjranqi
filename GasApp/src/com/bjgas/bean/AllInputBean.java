package com.bjgas.bean;

public class AllInputBean implements Comparable<AllInputBean> {
	private int time;
	private float elec;
	private float air;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public float getElec() {
		return elec;
	}

	public void setElec(float elec) {
		this.elec = elec;
	}

	public float getAir() {
		return air;
	}

	public void setAir(float air) {
		this.air = air;
	}

	public float getWater() {
		return water;
	}

	public void setWater(float water) {
		this.water = water;
	}

	private float water;

	@Override
	public int compareTo(AllInputBean another) {
		if (this.time > another.time)
			return 1;

		else if (this.time < another.time) {
			return -1;
		}
		return 0;
	}

}
