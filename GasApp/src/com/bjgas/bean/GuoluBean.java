package com.bjgas.bean;

public class GuoluBean implements Comparable<GuoluBean> {
	// ����(���������ڶ����죬ǰn��)
	private int riqi;

	/**
	 * �ĵ�
	 */
	private float haoqi;

	/**
	 * ����
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
