package com.bjgas.gasapp.nengyuanjiegou.guolu;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class GuoluMonthFragment extends GuoluFragments {

	public static Fragment newInstance() {
		return new GuoluMonthFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getMonthRequestUrl();
	}

	@Override
	public String getProperTime(int index, int length) {
		return super.getProperLastMonth(index, length);
	}
}
