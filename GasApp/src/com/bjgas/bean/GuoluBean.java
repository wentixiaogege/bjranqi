package com.bjgas.bean;

public class GuoluBean {
	// 日期(返回离现在多少天，前n日)
	private String riqi;

	/**
	 * 耗电
	 */
	private float haoqi;

	/**
	 * 制冷
	 */
	private float zhire;

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
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

}
