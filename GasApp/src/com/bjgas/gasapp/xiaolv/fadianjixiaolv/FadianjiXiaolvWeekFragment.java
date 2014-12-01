package com.bjgas.gasapp.xiaolv.fadianjixiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class FadianjiXiaolvWeekFragment extends FadianjiXiaolvFragments {

	public static Fragment newInstance() {
		return new FadianjiXiaolvWeekFragment();
	}

	/**
	 * ����ҳ���url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String
				.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Week);
		return mRequestUrl;
	}
}
