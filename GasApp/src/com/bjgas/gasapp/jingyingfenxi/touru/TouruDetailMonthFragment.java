package com.bjgas.gasapp.jingyingfenxi.touru;

import com.bjgas.common.SearchMethod;

public class TouruDetailMonthFragment extends TouruFragments {


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
