package com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv;

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

import com.bjgas.bean.XiaolvBean;
import com.bjgas.bean.ZhilengBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.xiaolv.XiaolvFragments;
import com.bjgas.gasapp.zongjiegou.InputsFragment;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class NengyuanliyongXiaolvFragments extends XiaolvFragments<XiaolvBean> {


	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("制冷系统");
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
	protected String getModule() {
		return InfoUtils.NENGYUANLIYONGXIAOLV;
	}


	@Override
	protected void displayChart() {
		try {
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaodian = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (XiaolvBean bean : jsonResults) {
				xTimes.add("前" + bean.getTime() + "天");
				yHaodian.add(new Entry(bean.getXiaolv(), i));
				++i;
			}

			LineDataSet setXiaolv = getDefaultDataset(yHaodian, InfoUtils.DISPLAY_ZONGXIAOLV, 1);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setXiaolv); // add the datasets

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
			return InfoUtils.DISPLAY_ZONGXIAOLV;
		case 1:
			return InfoUtils.DISPLAY_ZHILENG;
		default:
			return null;
		}
	}

}