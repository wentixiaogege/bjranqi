package com.bjgas.gasapp.nengyuanjiegou.zongjiegou;

import com.bjgas.common.SearchMethod;

public class InputsMonthFragment extends InputsFragment {


	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getMonthRequestUrl();
	}

	@Override
	public String getProperTime(int index, int length) {
		return super.getProperLastMonth(index, length);
	}
}
