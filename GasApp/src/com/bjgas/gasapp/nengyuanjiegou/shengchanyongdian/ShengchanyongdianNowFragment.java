package com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian;

import android.support.v4.app.Fragment;

public class ShengchanyongdianNowFragment extends ShengchanyongdianFragments {

	public static Fragment newInstance() {
		return new ShengchanyongdianNowFragment();
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
