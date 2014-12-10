package com.bjgas.gasapp.jingyingfenxi.touru;

import android.support.v4.app.Fragment;

public class TouruDetailNowFragment extends TouruFragments {

	public static Fragment newInstance() {
		return new TouruDetailNowFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getNowRequestUrl();
	}

	/**
	 * 搜索的时候，index表示月份。。。
	 */
	@Override
	public String getProperTime(int index) {
		return getProperDay(index);
	}
}
