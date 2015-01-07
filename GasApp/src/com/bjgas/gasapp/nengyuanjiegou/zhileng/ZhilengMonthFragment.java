package com.bjgas.gasapp.nengyuanjiegou.zhileng;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZhilengMonthFragment extends ZhilengFragments {

	public static Fragment newInstance() {
		return new ZhilengMonthFragment();
	}

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
