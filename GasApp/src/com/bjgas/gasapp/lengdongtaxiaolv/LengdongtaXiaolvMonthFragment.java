package com.bjgas.gasapp.lengdongtaxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class LengdongtaXiaolvMonthFragment extends LengdongtaXiaolvFragments {

	public static Fragment newInstance() {
		return new LengdongtaXiaolvMonthFragment();
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
