package com.bjgas.gasapp.xiaolv.yurexiaolv;


public class YureXiaolvSearchFragment extends YureXiaolvFragments {
	String startM;
	String endM;

	public YureXiaolvSearchFragment(String start, String end) {
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
	public String getProperTime(int index) {
		return getProperMonth(startM, index);
	}
}
