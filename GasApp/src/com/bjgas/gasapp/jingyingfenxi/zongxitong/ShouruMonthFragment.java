package com.bjgas.gasapp.jingyingfenxi.zongxitong;

import com.bjgas.common.SearchMethod;

public class ShouruMonthFragment extends ShouruFragment {


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
