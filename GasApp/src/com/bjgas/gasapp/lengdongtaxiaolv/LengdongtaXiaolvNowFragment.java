package com.bjgas.gasapp.lengdongtaxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class LengdongtaXiaolvNowFragment extends LengdongtaXiaolvFragments {

	public static Fragment newInstance() {
		return new LengdongtaXiaolvNowFragment();
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

	@Override
	public void convertJsonToBean(String json) {
		// TODO Auto-generated method stub
		super.convertJsonToBean(json);
	}
}
