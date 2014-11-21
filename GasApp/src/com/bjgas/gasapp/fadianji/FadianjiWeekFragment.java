package com.bjgas.gasapp.fadianji;

import com.bjgas.util.DateUtils;

import android.support.v4.app.Fragment;

public class FadianjiWeekFragment extends FadianjiFragments {

	public static Fragment newInstance() {
		return new FadianjiWeekFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl
	 */
	@Override
	public String getRequestUrl() {
		act_module = "dynamo";
		act_type = "week";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
