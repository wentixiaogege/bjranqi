package com.bjgas.gasapp.nengyuanjiegou.zongjiegou;

import com.bjgas.common.SearchMethod;

public class InputsSearchFragment extends InputsFragment {


	String startM;
	String endM;

	public InputsSearchFragment(String start, String end) {
		startM = start;
		endM = end;
	}

	/**
	 * 请求页面的url FadianjiFragments
	 */
	@Override
	public String getRequestUrl() {
		return getSearchRequestUrl(startM, endM);
	}

	/**
	 * 搜索的时候，index表示月份。。。
	 */
	@Override
	public String getProperTime(int index, int length) {
		return getProperMonth(startM, index);
	}
}
