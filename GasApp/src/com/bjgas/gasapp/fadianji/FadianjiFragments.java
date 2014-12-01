package com.bjgas.gasapp.fadianji;

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
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public abstract class FadianjiFragments extends BaseFragment<FadianjiBean> {

	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();

		mChart.setDescription("�����ϵͳ");
	};

	@Override
	protected String getModule() {
		return InfoUtils.FADIANJI_KEY;
	}

	/**
	 * ������ͼʱ�Ļص�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inputs_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		// ͨ���µ��̻߳�ȡ����
		getDataFromweb();

		// ��ʼ��chart
		initChart();
		return v;
	}

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
						FadianjiBean bean = new FadianjiBean();
						bean.setRiqi("ǰ" + k + "��");
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					FadianjiBean bean = jsonResults.get(j);
					// ������ܺĵ�
					if (key.equals(InfoUtils.FADIANJI_FADIAN)) {
						bean.setFadian((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.FADIANJI_YURE)) {
						bean.setYure((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.FADIANJI_HAOQI)) {
						bean.setYure((float) values.getDouble(j));
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
			// ���ú�������
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yFadian = new ArrayList<Entry>();
			ArrayList<Entry> yHaoqi = new ArrayList<Entry>();
			ArrayList<Entry> yYures = new ArrayList<Entry>();

			// ȡ��������InputBean
			convertJsonToBean(mJsonInfo);

			if (jsonResults.size() == 0)
				return;
			// ����x�������y��ֵ
			int i = 0;
			for (FadianjiBean bean : jsonResults) {
				xTimes.add("ǰ" + bean.getRiqi() + "��");
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
