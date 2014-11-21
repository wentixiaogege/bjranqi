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

	// // 定义一个Handler，用于线程同步。
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

		mChart.setDescription("输出能源");
		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it（点击上面，会提示信息）
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

		// // 新开启一个线程，获得Json数据
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

		// 初始化chart
		initChart();
		return v;
	}

	/**
	 * 子线程取得数据后，显示图表
	 */
	@Override
	protected void displayChart() {
		try {
			Log.d(TAG_ZONGJIEGOUCHART, String.format("get json info:%s", mJsonInfo));
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yHots = new ArrayList<Entry>();
			ArrayList<Entry> yColds = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0) {
				return;
			}
			// 设置x坐标轴和y的值
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
			// 取得output信息

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将传入的Json转化成AllInputBean数组
	 * 
	 * @param arrOutputs
	 * @param json
	 */
	@Override
	public void convertJsonToBean(String json) {
		try {
			JSONObject jObject = new JSONObject(json);
			// 取得input信息
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
	 * 如果取得的数据比理论上最大值大，或者比理论上最小值小，则变为最大值或最小值。
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
	 * 请求页面的url
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
