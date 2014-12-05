package com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class NengyuanliyongXiaolvMonthFragment extends NengyuanliyongXiaolvFragments {

	public static Fragment newInstance() {
		return new NengyuanliyongXiaolvMonthFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(),
				SearchMethod.Month);
		return mRequestUrl;
	}
}
