package com.bjgas.gasapp.jingyingfenxi.chanchu;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.util.DateUtils;

public class ChanchuMonthFragment extends ChanchuFragments {



	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getMonthRequestUrl();
	}
}
