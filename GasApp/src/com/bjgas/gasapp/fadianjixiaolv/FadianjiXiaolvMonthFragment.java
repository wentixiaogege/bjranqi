package com.bjgas.gasapp.fadianjixiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class FadianjiXiaolvMonthFragment extends FadianjiXiaolvFragments {

	public static Fragment newInstance() {
		return new FadianjiXiaolvMonthFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		act_module = "xiaolv";
		act_type = "month";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
