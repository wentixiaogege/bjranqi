package com.bjgas.gasapp.nengyuanjiegou.zhileng;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ZhilengWeekFragment extends ZhilengFragments {

	public static Fragment newInstance() {
		return new ZhilengWeekFragment();
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getWeekRequestUrl();
	}
}
