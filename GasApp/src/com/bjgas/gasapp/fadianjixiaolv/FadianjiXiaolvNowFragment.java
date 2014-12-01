package com.bjgas.gasapp.fadianjixiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class FadianjiXiaolvNowFragment extends FadianjiXiaolvFragments {

	public static Fragment newInstance() {
		return new FadianjiXiaolvNowFragment();
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
