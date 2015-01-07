package com.bjgas.gasapp.nengyuanjiegou;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.common.BaseFragment;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.R;
import com.github.mikephil.charting.charts.LineChart;

public abstract class NengyuanFragments<T> extends BaseFragment<T> {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.chart_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		getDataFromweb();

		// 初始化chart
		initChart();
		return v;
	}

	protected String getNowRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Now);
		return mRequestUrl;
	}

	protected String getWeekRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Week);
		return mRequestUrl;
	}

	protected String getMonthRequestUrl() {
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				SearchMethod.Month);
		return mRequestUrl;
	}

	protected String getSearchRequestUrl(String startMonth, String endMonth) {
		String mRequestUrl = String.format(FORMAT_URL_WITHDATE, REQUEST_WEBSITE, NENTYUAN_CATEGORY, getModule(),
				startMonth, endMonth);
		return mRequestUrl;
	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		// TODO Auto-generated method stub
		return StringUtils.EMPTY;
		// return null;
	}

	// /**
	// * 将传入的Json转化成AllInputBean数组
	// *
	// * @param arrInputs
	// * @param json
	// */
	// @Override
	// public void convertJsonToBean(String json) {
	// try {
	// JSONArray jArray = new JSONArray(json);
	// jsonResults.clear();
	//
	// for (int i = 0; i < jArray.length(); i++) {
	// // 利用这个函数，将i转化成日期。
	// JSONObject jo = jArray.getJSONObject(i);
	// JSONArray values = jo.getJSONArray("data");
	//
	// // 根据values的长度，初始化jsonResults，并初始化时间。
	// // 如果是第一次循环
	// if (0 == i)
	// for (int k = 0; k < values.length(); k++) {
	// XiaolvBean bean = new XiaolvBean();
	// bean.setTime(getProperTime(k));
	// jsonResults.add(bean);
	// }
	//
	// for (int j = 0; j < values.length(); j++) {
	// XiaolvBean bean = jsonResults.get(j);
	// bean.setXiaolv((float) values.getDouble(j));
	// }
	// }
	//
	// } catch (JSONException e) {
	// Log.d("Error", e.getMessage());
	// }
	//
	// }
}
