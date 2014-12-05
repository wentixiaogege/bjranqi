package com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ShengchanyongdianMonthFragment extends ShengchanyongdianFragments {

	public static Fragment newInstance() {
		return new ShengchanyongdianMonthFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Month);
		return mRequestUrl;
	}
}
