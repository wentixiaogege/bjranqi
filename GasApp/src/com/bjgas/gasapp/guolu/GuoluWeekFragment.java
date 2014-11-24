package com.bjgas.gasapp.guolu;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class GuoluWeekFragment extends GuoluFragments {

	public static Fragment newInstance() {
		return new GuoluWeekFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		act_module = "guolu";
		act_type = "week";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
