package com.bjgas.gasapp.xiaolv.lengdongtaxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
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
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Now);
		return mRequestUrl;
	}
}
