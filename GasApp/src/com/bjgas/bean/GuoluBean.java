package com.bjgas.bean;

public class GuoluBean implements Comparable<GuoluBean> {
	// 日期(返回离现在多少天，前n日)
	private int riqi;

	/**
	 * 耗电
	 */
	private float haoqi;

	/**
	 * 制冷
	 */
	private float zhire;

	public int getRiqi() {
		return riqi;
	}

	public void setRiqi(int riqi) {
		this.riqi = riqi;
	}

	public float getHaoqi() {
		return haoqi;
	}

	public void setHaoqi(float haoqi) {
		this.haoqi = haoqi;
	}

	public float getZhire() {
		return zhire;
	}

	public void setZhire(float zhire) {
		this.zhire = zhire;
	}

	@Override
	public int compareTo(GuoluBean another) {
		if (this.riqi > another.riqi)
			return -1;

		else if (this.riqi < another.riqi) {
			return 1;
		}
		return 0;
	}
}
