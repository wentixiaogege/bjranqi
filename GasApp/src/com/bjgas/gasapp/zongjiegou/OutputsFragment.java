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

import com.bjgas.bean.AllOutPutBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.common.ChartDoNothing;
import com.bjgas.common.MyMarkerView;
import com.bjgas.gasapp.R;
import com.bjgas.util.DateUtils;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public class OutputsFragment extends BaseFragment<AllOutPutBean> {
	public static Fragment newInstance() {
		return new OutputsFragment();
	}

	private static final String TAG_ZONGJIEGOUCHART = "OutputsFragment";

	private String mRequestUrl;

	private double minValue = 0.0;
	private double maxValue = 100.0;

	public OutputsFragment() {
		act_module = "construction";
		act_type = "all";
		mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", REQUEST_WEBSITE, act_module, act_type,
				DateUtils.getTodaySimplestr());

	}

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

	@Override
	public void initChart() {
		super.initChart();
		mChart.setOnChartGestureListener(new ChartDoNothing());
		mChart.setOnChartValueSelectedListener(new ChartDoNothing());

		mChart.setDescription("�����Դ");
		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it��������棬����ʾ��Ϣ��
		MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view, this);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
		mChart.setMarkerView(mv);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.outputs_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		// // �¿���һ���̣߳����Json����
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * �������Jsonת����AllInputBean����
	 * 
	 * @param arrOutputs
	 * @param json
	 */
	@Override
	public void convertJsonToBean(String json) {
		try {
			JSONObject jObject = new JSONObject(json);
			// ȡ��input��Ϣ
			JSONArray inputs;
			inputs = jObject.getJSONArray("outputs");

			for (int i = 0; i < inputs.length(); i++) {
				AllOutPutBean bean = new AllOutPutBean();

				JSONObject oneObject = inputs.getJSONObject(i);
				// Pulling items from the array
				bean.setTime(oneObject.getInt("time"));
				bean.setCold(getProperData(oneObject, "cold"));
				bean.setElec(getProperData(oneObject, "elec"));
				bean.setHot(getProperData(oneObject, "hot"));
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
		String label = dataSetIndex == 0 ? InfoUtils.OUTPUT_ELEC : (dataSetIndex == 1 ? InfoUtils.OUTPUT_COLD
				: InfoUtils.OUTPUT_HOT);
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
