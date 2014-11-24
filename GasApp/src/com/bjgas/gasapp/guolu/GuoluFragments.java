package com.bjgas.gasapp.guolu;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.GuoluBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.zongjiegou.InputsFragment;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class GuoluFragments extends BaseFragment<GuoluBean> {

	public static Fragment newInstance() {
		return new InputsFragment();
	}

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("锅炉系统");
	};

	/**
	 * 创建视图时的回调
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_util, container, false);
		mChart = (LineChart) v.findViewById(R.id.chart1);

		// 通过新的线程获取数据
		getDataFromweb();
		// 初始化chart
		initChart();
		return v;
	}

	@Override
	public void convertJsonToBean(String json) {
		try {
			JSONArray fdjInfos = new JSONArray(json);

			for (int i = 0; i < fdjInfos.length(); i++) {
				GuoluBean bean = new GuoluBean();

				JSONObject oneObject = fdjInfos.getJSONObject(i);
				bean.setRiqi(oneObject.getInt("day"));
				bean.setHaoqi(getProperData(oneObject, "haoqi"));
				bean.setZhire(getProperData(oneObject, "zhire"));
				// arrOutputs.add(bean);
				jsonResults.add(bean);
			}
			Collections.sort(jsonResults);
		} catch (JSONException e) {
			Log.d("convertJsonToBean Error", e.getMessage());
		}
	}

	private float getProperData(JSONObject jo, String string) throws JSONException {
		double value = jo.getDouble(string);
		return (float) value;
	}

	@Override
	protected void displayChart() {
		try {
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaoqi = new ArrayList<Entry>();
			ArrayList<Entry> yZhire = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (GuoluBean bean : jsonResults) {
				xTimes.add("前" + bean.getRiqi() + "天");
				yHaoqi.add(new Entry(bean.getHaoqi(), i));
				yZhire.add(new Entry(bean.getZhire(), i));
				++i;
			}

			LineDataSet setHaoqi = getDefaultDataset(yHaoqi, InfoUtils.DISPLAY_HAOQi, 1);

			LineDataSet setZhire = getDefaultDataset(yZhire, InfoUtils.DISPLAY_ZHIRE, 2);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setHaoqi); // add the datasets
			dataSets.add(setZhire);

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
			return InfoUtils.DISPLAY_HAOQi;
		case 1:
			return InfoUtils.DISPLAY_ZHIRE;
		default:
			return null;
		}
	}

}