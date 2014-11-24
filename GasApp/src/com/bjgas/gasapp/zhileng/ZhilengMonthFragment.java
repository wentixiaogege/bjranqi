package com.bjgas.gasapp.zhileng;

import android.support.v4.app.Fragment;

import com.bjgas.util.DateUtils;

public class ZhilengMonthFragment extends ZhilengFragments {

	public static Fragment newInstance() {
		return new ZhilengMonthFragment();
	}

	/**
	 * ����ҳ���url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		act_module = "zhileng";
		act_type = "month";
		String mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}
}
