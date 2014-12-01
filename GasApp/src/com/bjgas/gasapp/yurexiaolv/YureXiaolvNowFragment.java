package com.bjgas.gasapp.yurexiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class YureXiaolvNowFragment extends YureXiaolvFragments {

	public static Fragment newInstance() {
		return new YureXiaolvNowFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		act_module = "xiaolv";
		act_type = "now";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
