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
import com.bjgas.common.BaseFragment;
import com.bjgas.common.ChartDoNothing;
import com.bjgas.common.GasMarkerView;
import com.bjgas.gasapp.R;
import com.bjgas.util.DateUtils;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public class InputsFragment extends BaseFragment<AllInputBean> {
	public static Fragment newInstance() {
		return new InputsFragment();
	}

	private static final String TAG_ZONGJIEGOUCHART = "InputsFragment";

	private String mRequestUrl;

	private double minValue = 0.0;
	private double maxValue = 100.0;



	// // ����һ��Handler�������߳�ͬ����
	// @SuppressLint("HandlerLeak")
	// private Handler mHandler = new Handler() {
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case GET_JSON_SUCCESSFUL:
	// displayChart();
	// break;
	// case GET_JSON_ERROR:
	// displayErr();
	// break;
	// default:
	// break;
	// }
	// }
	//
	// };

	/**
	 * ��ʼ����ͼ
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setOnChartGestureListener(new ChartDoNothing());
		mChart.setOnChartValueSelectedListener(new ChartDoNothing());

		mChart.setDescription("������Դ");

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it��������棬����ʾ��Ϣ��
		GasMarkerView mv = new GasMarkerView(getActivity(), R.layout.custom_marker_view, this);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
		mChart.setMarkerView(mv);
	};

	/**
	 * ������ͼʱ�Ļص�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inputs_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		// �¿���һ���̣߳����Json����
		// new Thread() {
		// @Override
		// public void run() {
		// Log.d(TAG_ZONGJIEGOUCHART, String.format("mRequestUrl:%s",
		// mRequestUrl));
		// mJsonInfo = NetUtils.connServerForResult(getActivity(), mRequestUrl);
		// if (StringUtils.isNotEmpty(mJsonInfo))
		// mHandler.sendEmptyMessage(GET_JSON_SUCCESSFUL);
		// else {
		// mHandler.sendEmptyMessage(GET_JSON_ERROR);
		// }
		// };
		// }.start();
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
			JSONObject jObject = new JSONObject(json);
			// ȡ��input��Ϣ
			JSONArray inputs;
			inputs = jObject.getJSONArray("inputs");

			for (int i = 0; i < inputs.length(); i++) {
				AllInputBean bean = new AllInputBean();

				JSONObject oneObject = inputs.getJSONObject(i);
				// Pulling items from the array
				bean.setTime(oneObject.getInt("time"));
				bean.setAir(getProperData(oneObject, "air"));
				bean.setElec(getProperData(oneObject, "elec"));
				bean.setWater(getProperData(oneObject, "water"));
				jsonResults.add(bean);
			}

			Collections.sort(jsonResults);
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
		act_module = "construction";
		act_type = "all";
		mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());
		return mRequestUrl;
	}


}
