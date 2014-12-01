package com.bjgas.gasapp.fadianji;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

import android.support.v4.app.Fragment;

public class FadianjiMonthFragment extends FadianjiFragments {

	public static Fragment newInstance() {
		return new FadianjiMonthFragment();
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
