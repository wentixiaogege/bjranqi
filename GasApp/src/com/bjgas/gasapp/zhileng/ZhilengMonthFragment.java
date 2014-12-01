package com.bjgas.gasapp.zhileng;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZhilengMonthFragment extends ZhilengFragments {

	public static Fragment newInstance() {
		return new ZhilengMonthFragment();
	}

	/**
	 * ÇëÇóÒ³ÃæµÄurl FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Month);
		return mRequestUrl;
	}
}
