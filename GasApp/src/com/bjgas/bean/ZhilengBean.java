package com.bjgas.bean;

public class ZhilengBean implements Comparable<ZhilengBean> {
	// ����(���������ڶ����죬ǰn��)
	private int riqi;

	/**
	 * �ĵ�
	 */
	private float haodian;

	/**
	 * ����
	 */
	private float zhileng;

	public float getHaodian() {
		return haodian;
	}

	public void setHaodian(float haodian) {
		this.haodian = haodian;
	}

	public float getZhileng() {
		return zhileng;
	}

	public void setZhileng(float zhileng) {
		this.zhileng = zhileng;
	}

	public int getRiqi() {
		return riqi;
	}

	public void setRiqi(int riqi) {
		this.riqi = riqi;
	}

	@Override
	public int compareTo(ZhilengBean another) {
		if (this.riqi > another.riqi)
			return -1;

		else if (this.riqi < another.riqi) {
			return 1;
		}
		return 0;
	}
}
