package com.bjgas.bean;

public class ZhilengBean {
	// 日期(返回离现在多少天，前n日)
	private String riqi;

	/**
	 * 耗电
	 */
	private float haodian;

	/**
	 * 制冷
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

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}

}
