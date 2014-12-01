package com.bjgas.gasapp.zongxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class ZongxiaolvMonthFragment extends ZongxiaolvFragments {

	public static Fragment newInstance() {
		return new ZongxiaolvMonthFragment();
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
