package com.bjgas.gasapp.zhileng;

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

import com.bjgas.bean.ZhilengBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.zongjiegou.InputsFragment;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class ZhilengFragments extends BaseFragment<ZhilengBean> {

	public static Fragment newInstance() {
		return new InputsFragment();
	}

	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("����ϵͳ");
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
	public void convertJsonToBean(String json) {
		try {
			JSONArray fdjInfos = new JSONArray(json);

			for (int i = 0; i < fdjInfos.length(); i++) {
				ZhilengBean bean = new ZhilengBean();

				JSONObject oneObject = fdjInfos.getJSONObject(i);
				bean.setRiqi(oneObject.getInt("day"));
				bean.setHaodian(getProperData(oneObject, "haodian"));
				bean.setZhileng(getProperData(oneObject, "zhileng"));
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
			// ���ú�������
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yHaodian = new ArrayList<Entry>();
			ArrayList<Entry> yZhileng = new ArrayList<Entry>();

			// ȡ��������InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// ����x�������y��ֵ
			int i = 0;
			for (ZhilengBean bean : jsonResults) {
				xTimes.add("ǰ" + bean.getRiqi() + "��");
				yHaodian.add(new Entry(bean.getHaodian(), i));
				yZhileng.add(new Entry(bean.getZhileng(), i));
				++i;
			}

			LineDataSet setHaodian = getDefaultDataset(yHaodian, InfoUtils.DISPLAY_HAODIAN, 1);

			LineDataSet setZhileng = getDefaultDataset(yZhileng, InfoUtils.DISPLAY_ZHILENG, 2);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setHaodian); // add the datasets
			dataSets.add(setZhileng);

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
			return InfoUtils.DISPLAY_HAODIAN;
		case 1:
			return InfoUtils.DISPLAY_ZHILENG;
		default:
			return null;
		}
	}

}