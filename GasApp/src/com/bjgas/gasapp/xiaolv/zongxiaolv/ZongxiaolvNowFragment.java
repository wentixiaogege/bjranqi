package com.bjgas.gasapp.xiaolv.zongxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZongxiaolvNowFragment extends ZongxiaolvFragments {

	public static Fragment newInstance() {
		return new ZongxiaolvNowFragment();
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
