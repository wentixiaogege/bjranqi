package com.bjgas.gasapp.nengyuanjiegou.fadianji;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.FadianjiBean;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.nengyuanjiegou.NengyuanFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class FadianjiFragments extends NengyuanFragments<FadianjiBean> {

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();

		mChart.setDescription("发电机系统");
	};

	@Override
	protected String getModule() {
		return InfoUtils.FADIANJI_KEY;
	}

	// /**
	// * 创建视图时的回调
	// */
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View v = inflater.inflate(R.layout.inputs_fragment, container, false);
	//
	// mChart = (LineChart) v.findViewById(R.id.chart1);
	//
	// // 通过新的线程获取数据
	// getDataFromweb();
	//
	// // 初始化chart
	// initChart();
	// return v;
	// }

	// @Override
	// public void convertJsonToBean(String json) {
	// try {
	// JSONArray fdjInfos = new JSONArray(json);
	//
	// for (int i = 0; i < fdjInfos.length(); i++) {
	// FadianjiBean bean = new FadianjiBean();
	//
	// JSONObject oneObject = fdjInfos.getJSONObject(i);
	// bean.setRiqi(oneObject.getInt("day"));
	// bean.setFadian(getProperData(oneObject, "fadian"));
	// bean.setHaodian(getProperData(oneObject, "haodian"));
	// bean.setYure(getProperData(oneObject, "yure"));
	// // arrOutputs.add(bean);
	// jsonResults.add(bean);
	// }
	// Collections.sort(jsonResults);
	// } catch (JSONException e) {
	// Log.d("Error", e.getMessage());
	// }
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
						FadianjiBean bean = new FadianjiBean();
						bean.setRiqi(getProperTime(k, values.length()));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					FadianjiBean bean = jsonResults.get(j);
					// 如果是总耗电
					if (key.equals(InfoUtils.FADIANJI_FADIAN)) {
						bean.setFadian((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.FADIANJI_YURE)) {
						bean.setYure((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.FADIANJI_HAOQI)) {
						bean.setHaoQi((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}


	@Override
	protected void displayChart() {
		try {
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yFadian = new ArrayList<Entry>();
			ArrayList<Entry> yHaoqi = new ArrayList<Entry>();
			ArrayList<Entry> yYures = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (FadianjiBean bean : jsonResults) {
				xTimes.add(bean.getRiqi());
				yFadian.add(new Entry(bean.getFadian(), i));
				yHaoqi.add(new Entry(bean.getHaoQi(), i));
				yYures.add(new Entry(bean.getYure(), i));
				++i;
			}

			LineDataSet setFadian = getDefaultDataset(yFadian, InfoUtils.FADIANJI_FADIAN, 1);

			LineDataSet setHaoqi = getDefaultDataset(yHaoqi, InfoUtils.FADIANJI_HAOQI, 2);
			LineDataSet setYure = getDefaultDataset(yYures, InfoUtils.FADIANJI_YURE, 3);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setFadian); // add the datasets
			dataSets.add(setHaoqi);
			dataSets.add(setYure);

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
			return InfoUtils.FADIANJI_FADIAN;
		case 1:
			return InfoUtils.FADIANJI_HAOQI;
		case 2:
			return InfoUtils.FADIANJI_YURE;
		default:
			return null;
		}
	}

}
