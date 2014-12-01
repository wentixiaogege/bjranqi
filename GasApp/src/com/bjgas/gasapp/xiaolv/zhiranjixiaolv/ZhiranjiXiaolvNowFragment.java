package com.bjgas.gasapp.xiaolv.zhiranjixiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class ZhiranjiXiaolvNowFragment extends ZhiranjiXiaolvFragments {

	public static Fragment newInstance() {
		return new ZhiranjiXiaolvNowFragment();
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
