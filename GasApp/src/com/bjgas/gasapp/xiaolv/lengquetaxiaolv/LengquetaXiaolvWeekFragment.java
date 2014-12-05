package com.bjgas.gasapp.xiaolv.lengquetaxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class LengquetaXiaolvWeekFragment extends LengquetaXiaolvFragments {

	public static Fragment newInstance() {
		return new LengquetaXiaolvWeekFragment();
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
