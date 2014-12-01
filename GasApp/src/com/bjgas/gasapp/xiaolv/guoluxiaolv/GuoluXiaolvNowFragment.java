package com.bjgas.gasapp.xiaolv.guoluxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class GuoluXiaolvNowFragment extends GuoluXiaolvFragments {

	public static Fragment newInstance() {
		return new GuoluXiaolvNowFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String
.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Now);
		return mRequestUrl;
	}
}
