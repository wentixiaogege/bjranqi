package com.bjgas.gasapp.nengyuanjiegou.guolu;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class GuoluWeekFragment extends GuoluFragments {

	public static Fragment newInstance() {
		return new GuoluWeekFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getWeekRequestUrl();
	}
}
