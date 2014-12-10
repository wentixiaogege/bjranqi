package com.bjgas.gasapp.nengyuanjiegou.fadianji;

import android.support.v4.app.Fragment;

public class FadianjiNowFragment extends FadianjiFragments {

	public static Fragment newInstance() {
		return new FadianjiNowFragment();
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
