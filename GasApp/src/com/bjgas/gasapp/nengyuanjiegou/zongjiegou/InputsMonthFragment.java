package com.bjgas.gasapp.nengyuanjiegou.zongjiegou;

import com.bjgas.common.SearchMethod;

public class InputsMonthFragment extends InputsFragment {


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
