package com.bjgas.gasapp.fadianji;

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

import com.bjgas.bean.FadianjiBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.common.ChartDoNothing;
import com.bjgas.common.MyMarkerView;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.zongjiegou.InputsFragment;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class FadianjiFragments extends BaseFragment<FadianjiBean> {

	public static Fragment newInstance() {
		return new InputsFragment();
	}

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setOnChartGestureListener(new ChartDoNothing());
		mChart.setOnChartValueSelectedListener(new ChartDoNothing());

		mChart.setDescription("输入能源");

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it（点击上面，会提示信息）
		MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view, this);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
		mChart.setMarkerView(mv);
	};

	/**
	 * 创建视图时的回调
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inputs_fragment, container, false);

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
				FadianjiBean bean = new FadianjiBean();

				JSONObject oneObject = fdjInfos.getJSONObject(i);
				bean.setRiqi(oneObject.getInt("day"));
				bean.setFadian(getProperData(oneObject, "fadian"));
				bean.setHaodian(getProperData(oneObject, "haodian"));
				bean.setYure(getProperData(oneObject, "yure"));
				// arrOutputs.add(bean);
				jsonResults.add(bean);
			}
			Collections.sort(jsonResults);
		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
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
				xTimes.add("前" + bean.getRiqi() + "天");
				yFadian.add(new Entry(bean.getFadian(), i));
				yHaoqi.add(new Entry(bean.getHaodian(), i));
				yYures.add(new Entry(bean.getYure(), i));
				++i;
			}

			LineDataSet setFadian = getDefaultDataset(yFadian, InfoUtils.DISPLAY_FADIAN, 1);

			LineDataSet setHaoqi = getDefaultDataset(yHaoqi, InfoUtils.DISPLAY_HAOQI, 2);
			LineDataSet setYure = getDefaultDataset(yYures, InfoUtils.DISPLAY_YURE, 3);

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
			return InfoUtils.DISPLAY_FADIAN;
		case 1:
			return InfoUtils.DISPLAY_HAOQI;
		case 2:
			return InfoUtils.DISPLAY_YURE;
		default:
			return null;
		}
	}

}
