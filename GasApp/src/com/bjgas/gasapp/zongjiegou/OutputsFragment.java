package com.bjgas.gasapp.zongjiegou;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.AllInputBean;
import com.bjgas.bean.AllOutPutBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.common.ChartDoNothing;
import com.bjgas.common.GasMarkerView;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.R;
import com.bjgas.util.DateUtils;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public class OutputsFragment extends BaseFragment<AllOutPutBean> {
	public static Fragment newInstance(SearchMethod sm) {
		return new OutputsFragment(sm);
	}

	private SearchMethod searchMethod;

	public OutputsFragment(SearchMethod sm) {
		this.searchMethod = sm;
	}

	private static final String TAG_ZONGJIEGOUCHART = "OutputsFragment";

	@Override
	public void initChart() {
		super.initChart();

		mChart.setDescription("�����Դ");

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.outputs_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		getDataFromweb();

		// ��ʼ��chart
		initChart();
		return v;
	}

	/**
	 * ���߳�ȡ�����ݺ���ʾͼ��
	 */
	@Override
	protected void displayChart() {
		try {
			Log.d(TAG_ZONGJIEGOUCHART, String.format("get json info:%s", mJsonInfo));
			// ���ú�������
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yHots = new ArrayList<Entry>();
			ArrayList<Entry> yColds = new ArrayList<Entry>();

			// ȡ��������InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0) {
				return;
			}
			// ����x�������y��ֵ
			int i = 0;
			for (AllOutPutBean bean : jsonResults) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yHots.add(new Entry(bean.getHot(), i));
				yColds.add(new Entry(bean.getCold(), i));
				++i;
			}

			LineDataSet setElecs = getDefaultDataset(yElecs, InfoUtils.OUTPUT_ELEC, 1);
			LineDataSet setHots = getDefaultDataset(yHots, InfoUtils.OUTPUT_HOT, 3);
			LineDataSet setColds = getDefaultDataset(yColds, InfoUtils.OUTPUT_COLD, 2);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setColds);
			dataSets.add(setHots);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			mChart.setData(data);
			mChart.invalidate();
			// ȡ��output��Ϣ

		} catch (Exception e) {
			e.printStackTrace();
		}

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
						AllOutPutBean bean = new AllOutPutBean();
						bean.setTime("ǰ" + k + "��");
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					AllOutPutBean bean = jsonResults.get(j);
					// ������ܺĵ�
					if (key.equals(InfoUtils.OUTPUT_ELEC)) {
						bean.setElec((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.OUTPUT_COLD)) {
						bean.setCold((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.OUTPUT_HOT)) {
						bean.setHot((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	@Override
	protected void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		String label = dataSetIndex == 0 ? InfoUtils.OUTPUT_ELEC : (dataSetIndex == 1 ? InfoUtils.OUTPUT_COLD
				: InfoUtils.OUTPUT_HOT);
		return label;
	}

	/**
	 * ����ҳ���url
	 */
	@Override
	public String getRequestUrl() {
		String searchFilter = SearchMethod.Now.toString();
		if (searchMethod.equals(SearchMethod.Week))
			searchFilter = SearchMethod.Week.toString();
		else if (searchMethod.equals(SearchMethod.Month)) {
			searchFilter = SearchMethod.Month.toString();
		}
		String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE, NENTYUAN_CATEGORY, "zongjiegou", searchFilter);
		return mRequestUrl;
	}

	@Override
	protected String getModule() {
		return "zongjiegou";
	}

}
