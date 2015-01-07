package com.bjgas.gasapp.nengyuanjiegou.fadianji;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

import android.support.v4.app.Fragment;

public class FadianjiMonthFragment extends FadianjiFragments {

	public static Fragment newInstance() {
		return new FadianjiMonthFragment();
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
