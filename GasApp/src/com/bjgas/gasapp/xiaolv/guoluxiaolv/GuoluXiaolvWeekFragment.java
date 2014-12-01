package com.bjgas.gasapp.xiaolv.guoluxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class GuoluXiaolvWeekFragment extends GuoluXiaolvFragments {

	public static Fragment newInstance() {
		return new GuoluXiaolvWeekFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String
				.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Week);
		return mRequestUrl;
	}
}
