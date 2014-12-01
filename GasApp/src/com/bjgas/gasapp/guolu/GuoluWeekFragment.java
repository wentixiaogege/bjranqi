package com.bjgas.gasapp.guolu;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class GuoluWeekFragment extends GuoluFragments {

	public static Fragment newInstance() {
		return new GuoluWeekFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Week);
		return mRequestUrl;
	}
}
