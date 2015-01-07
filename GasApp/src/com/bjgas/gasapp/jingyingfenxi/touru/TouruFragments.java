package com.bjgas.gasapp.jingyingfenxi.touru;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.AllInputBean;
import com.bjgas.bean.ZhilengBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.jingyingfenxi.JingyingFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class TouruFragments extends JingyingFragments<AllInputBean> {

	private static final String JINGYING_TOURU_ELEC = InfoUtils.JINGYING_TOURU_ELEC;
	private static final String JINGYING_TOURU_WATER = InfoUtils.JINGYING_TOURU_WATER;
	private static final String JINGYING_TOURU_GAS = InfoUtils.JINGYING_TOURU_GAS;

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("投入分析");
	};

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
						AllInputBean bean = new AllInputBean();
						bean.setTime(getProperTime(k, values.length()));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					AllInputBean bean = jsonResults.get(j);
					// 如果是总耗电
					if (key.equals(JINGYING_TOURU_ELEC)) {
						bean.setElec((float) values.getDouble(j));
					} else if (key.equals(JINGYING_TOURU_GAS)) {
						bean.setAir((float) values.getDouble(j));
					} else if (key.equals(JINGYING_TOURU_WATER)) {
						bean.setWater((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	@Override
	protected String getModule() {
		return InfoUtils.TOURU;
	}

	/**
	 * 子线程取得数据后，显示图表
	 */
	@Override
	protected void displayChart() {
		try {
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yAirs = new ArrayList<Entry>();
			ArrayList<Entry> yWaters = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (AllInputBean bean : jsonResults) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yAirs.add(new Entry(bean.getAir(), i));
				yWaters.add(new Entry(bean.getWater(), i));
				++i;
			}

			LineDataSet setElecs = getDefaultDataset(yElecs, JINGYING_TOURU_ELEC, 1);

			LineDataSet setAirs = getDefaultDataset(yAirs, JINGYING_TOURU_GAS, 2);
			LineDataSet setWaters = getDefaultDataset(yWaters, JINGYING_TOURU_WATER, 3);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setAirs);
			dataSets.add(setWaters);

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

	// @Override
	// public String getMarkViewDesc(int dataSetIndex) {
	// String label = dataSetIndex == 0 ? JINGYING_TOURU_ELEC : (dataSetIndex ==
	// 1 ? JINGYING_TOURU_GAS
	// : JINGYING_TOURU_WATER);
	// return label;
	// }

}