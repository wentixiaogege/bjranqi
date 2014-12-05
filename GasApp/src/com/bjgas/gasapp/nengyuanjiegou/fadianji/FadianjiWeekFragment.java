package com.bjgas.gasapp.nengyuanjiegou.fadianji;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

import android.support.v4.app.Fragment;

public class FadianjiWeekFragment extends FadianjiFragments {

	public static Fragment newInstance() {
		return new FadianjiWeekFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getWeekRequestUrl();
	}
}
