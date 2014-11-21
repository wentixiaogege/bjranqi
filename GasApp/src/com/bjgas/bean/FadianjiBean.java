package com.bjgas.bean;

public class FadianjiBean implements Comparable<FadianjiBean> {
	// 日期(返回离现在多少天，前n日)
	private int riqi;
	// 号店
	private float haodian;
	// 发电
	private float fadian;
	// 余热
	private float yure;

	public int getRiqi() {
		return riqi;
	}

	public void setRiqi(int riqi) {
		this.riqi = riqi;
	}

	public float getHaodian() {
		return haodian;
	}

	public void setHaodian(float haodian) {
		this.haodian = haodian;
	}

	public float getFadian() {
		return fadian;
	}

	public void setFadian(float fadian) {
		this.fadian = fadian;
	}

	public float getYure() {
		return yure;
	}

	public void setYure(float yure) {
		this.yure = yure;
	}

	@Override
	public int compareTo(FadianjiBean another) {
		if (this.riqi > another.riqi)
			return -1;

		else if (this.riqi < another.riqi) {
			return 1;
		}
		return 0;
	}

}
