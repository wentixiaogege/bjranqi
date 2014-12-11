package com.bjgas.gasapp.nengyuanjiegou.zhileng;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.ZhilengBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.nengyuanjiegou.NengyuanFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class ZhilengFragments extends NengyuanFragments<ZhilengBean> {

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("制冷系统");
	};

	// /**
	// * 创建视图时的回调
	// */
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View v = inflater.inflate(R.layout.fragment_util, container, false);
	// mChart = (LineChart) v.findViewById(R.id.chart1);
	//
	// // 通过新的线程获取数据
	// getDataFromweb();
	// // 初始化chart
	// initChart();
	// return v;
	// }


	/**
	 * 将传入的Json转化成AllInputBean数组
	 * 
	 * @param arrInputs
	 * @param json
	 */
	@Override
	public void convertJsonToBean(String json) {
		try {
			JSONArray jArray = new JSONArray(json);
			jsonResults.clear();

			for (int i = 0; i < jArray.length(); i++) {
				// 利用这个函数，将i转化成日期。
				JSONObject jo = jArray.getJSONObject(i);
				String key = jo.getString("name");
				JSONArray values = jo.getJSONArray("data");

				// 根据values的长度，初始化jsonResults，并初始化时间。
				// 如果是第一次循环
				if (0 == i)
					for (int k = 0; k < values.length(); k++) {
						ZhilengBean bean = new ZhilengBean();
						bean.setRiqi(getProperTime(k, values.length()));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					ZhilengBean bean = jsonResults.get(j);
					// 如果是总耗电
					if (key.equals(InfoUtils.ZHILENG_HAODIAN)) {
						bean.setHaodian((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.ZHILENG_ZHILENG)) {
						bean.setZhileng((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	@Override
	protected String getModule() {
		return InfoUtils.ZHILENG_KEY;
	}

	@Override
	protected void displayChart() {
		try {
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaodian = new ArrayList<Entry>();
			ArrayList<Entry> yZhileng = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (ZhilengBean bean : jsonResults) {
				xTimes.add(bean.getRiqi());
				yHaodian.add(new Entry(bean.getHaodian(), i));
				yZhileng.add(new Entry(bean.getZhileng(), i));
				++i;
			}

			LineDataSet setHaodian = getDefaultDataset(yHaodian, InfoUtils.ZHILENG_HAODIAN, 1);

			LineDataSet setZhileng = getDefaultDataset(yZhileng, InfoUtils.ZHILENG_ZHILENG, 2);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setHaodian); // add the datasets
			dataSets.add(setZhileng);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			mChart.setData(data);
			mChart.invalidate();
			// 取得output信息

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		switch (dataSetIndex) {
		case 0:
			return InfoUtils.ZHILENG_HAODIAN;
		case 1:
			return InfoUtils.ZHILENG_ZHILENG;
		default:
			return null;
		}
	}

}