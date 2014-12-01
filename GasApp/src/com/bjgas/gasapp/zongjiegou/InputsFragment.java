package com.bjgas.gasapp.zongjiegou;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.AllInputBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.R;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public class InputsFragment extends BaseFragment<AllInputBean> {

	private SearchMethod searchMethod;

	public InputsFragment(SearchMethod sm) {
		this.searchMethod = sm;
	}

	public static InputsFragment Instance(SearchMethod sm) {
		return new InputsFragment(sm);
	}

	private static final String TAG_ZONGJIEGOUCHART = "InputsFragment";

	private double minValue = 0.0;
	private double maxValue = 100.0;

	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("������Դ");
	};

	/**
	 * ������ͼʱ�Ļص�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inputs_fragment, container, false);

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
			ArrayList<Entry> yAirs = new ArrayList<Entry>();
			ArrayList<Entry> yWaters = new ArrayList<Entry>();

			// ȡ��������InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0)
				return;
			// ����x�������y��ֵ
			int i = 0;
			for (AllInputBean bean : jsonResults) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yAirs.add(new Entry(bean.getAir(), i));
				yWaters.add(new Entry(bean.getWater(), i));
				++i;
			}

			LineDataSet setElecs = getDefaultDataset(yElecs, InfoUtils.INPUT_ELEC, 1);

			LineDataSet setAirs = getDefaultDataset(yAirs, InfoUtils.INPUT_AIR, 2);
			LineDataSet setWaters = getDefaultDataset(yWaters, InfoUtils.INPUT_WATER, 3);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setAirs);
			dataSets.add(setWaters);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			// ��ʱ����������������
			// LimitLine ll1 = new LimitLine(100f);
			// ll1.setLineWidth(1f);
			// ll1.enableDashedLine(10f, 10f, 0f);
			// ll1.setDrawValue(false);
			// ll1.setLabelPosition(LimitLabelPosition.RIGHT);
			//
			// LimitLine ll2 = new LimitLine(0f);
			// ll2.setLineWidth(1f);
			// ll2.enableDashedLine(10f, 10f, 0f);
			// ll2.setDrawValue(false);
			// ll2.setLabelPosition(LimitLabelPosition.RIGHT);
			//
			// data.addLimitLine(ll1);
			// data.addLimitLine(ll2);

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
						AllInputBean bean = new AllInputBean();
						bean.setTime("ǰ" + k + "��");
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					AllInputBean bean = jsonResults.get(j);
					// ������ܺĵ�
					if (key.equals(InfoUtils.INPUT_ELEC)) {
						bean.setElec((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.INPUT_AIR)) {
						bean.setAir((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.INPUT_WATER)) {
						bean.setWater((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	/**
	 * ���ȡ�õ����ݱ����������ֵ�󣬻��߱���������СֵС�����Ϊ���ֵ����Сֵ��
	 * 
	 * @param jo
	 * @param string
	 * @return
	 * @throws JSONException
	 */
	private float getProperData(JSONObject jo, String string) throws JSONException {
		double value = jo.getDouble(string);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return (float) value;
	}

	@Override
	protected void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		String label = dataSetIndex == 0 ? InfoUtils.INPUT_ELEC : (dataSetIndex == 1 ? InfoUtils.INPUT_AIR
				: InfoUtils.INPUT_WATER);
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

}
