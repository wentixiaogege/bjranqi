package com.bjgas.gasapp.xiaolv.guoluxiaolv;


public class GuoluXiaolvSearchFragment extends GuoluXiaolvFragments {
	String startM;
	String endM;

	public GuoluXiaolvSearchFragment(String start, String end) {
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
