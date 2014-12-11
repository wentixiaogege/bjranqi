package com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian;


public class ShengchanyongdianSearchFragment extends ShengchanyongdianFragments {
	String startM;
	String endM;

	public ShengchanyongdianSearchFragment(String start, String end) {
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
