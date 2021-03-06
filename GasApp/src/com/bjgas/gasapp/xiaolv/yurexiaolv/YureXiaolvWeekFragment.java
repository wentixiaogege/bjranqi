package com.bjgas.gasapp.xiaolv.yurexiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class YureXiaolvWeekFragment extends YureXiaolvFragments {

	public static Fragment newInstance() {
		return new YureXiaolvWeekFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String
				.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Week);
		return mRequestUrl;
	}
}
