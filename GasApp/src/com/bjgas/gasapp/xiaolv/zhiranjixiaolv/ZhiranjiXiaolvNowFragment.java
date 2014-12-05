package com.bjgas.gasapp.xiaolv.zhiranjixiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZhiranjiXiaolvNowFragment extends ZhiranjiXiaolvFragments {

	public static Fragment newInstance() {
		return new ZhiranjiXiaolvNowFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(), SearchMethod.Now);
		return mRequestUrl;
	}
}
