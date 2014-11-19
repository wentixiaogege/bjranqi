package com.bjgas.bean;

public class AllOutPutBean implements Comparable<AllOutPutBean> {
	private int time;
	private float hot;
	private float cold;
	private float elec;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public float getHot() {
		return hot;
	}

	public void setHot(float hot) {
		this.hot = hot;
	}

	public float getCold() {
		return cold;
	}

	public void setCold(float cold) {
		this.cold = cold;
	}

	public float getElec() {
		return elec;
	}

	public void setElec(float elec) {
		this.elec = elec;
	}

	@Override
	public int compareTo(AllOutPutBean another) {
		if (this.time > another.time)
			return 1;

		else if (this.time < another.time) {
			return -1;
		}
		return 0;
	}

}
