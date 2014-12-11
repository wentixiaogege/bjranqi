package com.bjgas.gasapp.jingyingfenxi.zongxitong;

import android.support.v4.app.Fragment;

public class TouruNowFragment extends TouruFragment {

	public static Fragment newInstance() {
		return new TouruNowFragment();
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
	public String getProperTime(int index, int length) {
		return getProperDay(index, length);
	}
}
