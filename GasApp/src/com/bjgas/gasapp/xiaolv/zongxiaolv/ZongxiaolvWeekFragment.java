package com.bjgas.gasapp.xiaolv.zongxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZongxiaolvWeekFragment extends ZongxiaolvFragments {

	public static Fragment newInstance() {
		return new ZongxiaolvWeekFragment();
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
