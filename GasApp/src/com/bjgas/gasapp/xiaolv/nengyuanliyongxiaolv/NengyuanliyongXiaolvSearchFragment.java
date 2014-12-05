package com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv;


public class NengyuanliyongXiaolvSearchFragment extends NengyuanliyongXiaolvFragments {
	String startM;
	String endM;

	public NengyuanliyongXiaolvSearchFragment(String start, String end) {
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
