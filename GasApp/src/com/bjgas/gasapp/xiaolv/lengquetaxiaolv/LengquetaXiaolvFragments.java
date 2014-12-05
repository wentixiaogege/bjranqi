package com.bjgas.gasapp.xiaolv.lengquetaxiaolv;

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

public abstract class LengquetaXiaolvFragments extends XiaolvFragments<XiaolvBean> {


	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("冷却塔效率");
	};

	/**
	 * ������ͼʱ�Ļص�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_util, container, false);
		mChart = (LineChart) v.findViewById(R.id.chart1);

		// ͨ���µ��̻߳�ȡ����
		getDataFromweb();
		// ��ʼ��chart
		initChart();
		return v;
	}


	@Override
	protected String getModule() {
		return InfoUtils.LENGQUETAXIAOLV;
	}

	@Override
	protected void displayChart() {
		try {
			// ���ú�������
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaodian = new ArrayList<Entry>();

			// ȡ��������InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// ����x�������y��ֵ
			int i = 0;
			for (XiaolvBean bean : jsonResults) {
				xTimes.add(bean.getTime());
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
			// ȡ��output��Ϣ

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