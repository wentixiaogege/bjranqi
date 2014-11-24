package com.bjgas.gasapp.guolu;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class GuoluMonthFragment extends GuoluFragments {

	public static Fragment newInstance() {
		return new GuoluMonthFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		act_module = "guolu";
		act_type = "month";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
