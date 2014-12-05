package com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ShengchanyongdianWeekFragment extends ShengchanyongdianFragments {

	public static Fragment newInstance() {
		return new ShengchanyongdianWeekFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getWeekRequestUrl();
	}
}
