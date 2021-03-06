package com.bjgas.gasapp.xiaolv.fadianjixiaolv;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.XiaolvBean;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.xiaolv.XiaolvFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class FadianjiXiaolvFragments extends XiaolvFragments<XiaolvBean> {

	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription(InfoUtils.FADIANJI_XIAOLV);
	};


	@Override
	protected String getModule() {
		return InfoUtils.FADIANJIXIAOLV;
	}

	@Override
	protected void displayChart() {
		try {
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaodian = new ArrayList<Entry>();

			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			int i = 0;
			for (XiaolvBean bean : jsonResults) {
				xTimes.add(bean.getTime());
				yHaodian.add(new Entry(bean.getXiaolv(), i));
				++i;
			}

			LineDataSet setXiaolv = getDefaultDataset(yHaodian, InfoUtils.FADIANJI_XIAOLV, 1);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setXiaolv); // add the datasets

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			mChart.setData(data);
			mChart.invalidate();

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
	// switch (dataSetIndex) {
	// case 0:
	// return InfoUtils.FADIANJI_XIAOLV;
	// case 1:
	// return InfoUtils.FADIANJI_XIAOLV;
	// default:
	// return null;
	// }
	// }

}