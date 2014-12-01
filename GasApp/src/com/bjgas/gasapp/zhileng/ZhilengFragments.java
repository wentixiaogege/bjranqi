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

import com.bjgas.bean.FadianjiBean;
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


	/**
	 * �������Jsonת����AllInputBean����
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
				// ���������������iת�������ڡ�
				JSONObject jo = jArray.getJSONObject(i);
				String key = jo.getString("name");
				JSONArray values = jo.getJSONArray("data");

				// ����values�ĳ��ȣ���ʼ��jsonResults������ʼ��ʱ�䡣
				// ����ǵ�һ��ѭ��
				if (0 == i)
					for (int k = 0; k < values.length(); k++) {
						ZhilengBean bean = new ZhilengBean();
						bean.setRiqi("ǰ" + k + "��");
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					ZhilengBean bean = jsonResults.get(j);
					// ������ܺĵ�
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