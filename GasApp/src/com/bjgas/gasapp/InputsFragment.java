package com.bjgas.gasapp;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.AllInputBean;
import com.bjgas.common.BaseActivity;
import com.bjgas.common.BaseFragment;
import com.bjgas.common.MyMarkerView;
import com.bjgas.util.DateUtils;
import com.bjgas.util.NetUtils;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

@SuppressLint("NewApi")
public class InputsFragment extends BaseFragment implements OnChartGestureListener, OnChartValueSelectedListener {
	public static Fragment newInstance() {
		return new InputsFragment();
	}


	private static final int GET_JSON_SUCCESSFUL = 1;
	private static final int GET_JSON_ERROR = 9;
	private static final String TAG_ZONGJIEGOUCHART = "InputsFragment";

	private String mJsonInfo;
	private String mRequestUrl;
	private LineChart mChart;

	private double minValue = 0.0;
	private double maxValue = 100.0;

	public InputsFragment() {
		BaseActivity.act_module = "construction";
		BaseActivity.act_type = "all";
		mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", BaseActivity.BASE_URL, BaseActivity.act_module,
				BaseActivity.act_type,
				DateUtils.getTodaySimplestr());

	}

	// 定义一个Handler，用于线程同步。
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_JSON_SUCCESSFUL:
				displayChart();
				break;
			case GET_JSON_ERROR:
				displayErr();
				break;
			default:
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inputs_fragment, container, false);

		mChart = (LineChart) v.findViewById(R.id.chart1);

		// 新开启一个线程，获得Json数据
		new Thread() {

			@Override
			public void run() {
				Log.d(TAG_ZONGJIEGOUCHART, String.format("mRequestUrl:%s", mRequestUrl));
				mJsonInfo = NetUtils.connServerForResult(getActivity(), mRequestUrl);
				if (StringUtils.isNotEmpty(mJsonInfo))
					mHandler.sendEmptyMessage(GET_JSON_SUCCESSFUL);
				else {
					mHandler.sendEmptyMessage(GET_JSON_ERROR);
				}
			};
		}.start();

		// 初始化chart
		initChart();
		return v;
	}

	/**
	 * 初始化chart
	 */
	public void initChart() {

		mChart.setOnChartGestureListener(this);
		mChart.setOnChartValueSelectedListener(this);

		mChart.setUnit("");
		mChart.setDrawUnitsInChart(true);

		// if enabled, the chart will always start at zero on the y-axis
		mChart.setStartAtZero(false);

		// disable the drawing of values into the chart
		mChart.setDrawYValues(true);

		mChart.setDrawBorder(true);
		mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM });

		// no description text
		mChart.setDescription("");
		mChart.setNoDataTextDescription("暂时没有取得数据");

		// // enable / disable grid lines
		// mChart.setDrawVerticalGrid(false);
		// mChart.setDrawHorizontalGrid(false);
		//
		// // enable / disable grid background
		// mChart.setDrawGridBackground(false);
		//
		// mChart.setDrawXLegend(false);
		// mChart.setDrawYLegend(false);

		// enable value highlighting
		mChart.setHighlightEnabled(true);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(false);

		// set an alternative background color
		// mChart.setBackgroundColor(Color.GRAY);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it（点击上面，会提示信息）
		MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
		mChart.setMarkerView(mv);

		// enable/disable highlight indicators (the lines that indicate the
		// highlighted Entry)
		mChart.setHighlightIndicatorEnabled(false);

		// add data
		// setData(45, 100);

		mChart.animateX(2500);

		// restrain the maximum scale-out factor
		mChart.setScaleMinima(1f, 1f);
		//
		// center the view to a specific position inside the chart
		// mChart.centerViewPort(100, 50);

		// get the legend (only possible after setting data)
		// Legend l = mChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		// l.setForm(LegendForm.LINE);

		// // dont forget to refresh the drawing
		// mChart.invalidate();
	}

	/**
	 * 子线程取得数据后，显示图表
	 */
	private void displayChart() {
		try {
			Log.d(TAG_ZONGJIEGOUCHART, String.format("get json info:%s", mJsonInfo));
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yAirs = new ArrayList<Entry>();
			ArrayList<Entry> yWaters = new ArrayList<Entry>();

			// 取得排序后的InputBean
			ArrayList<AllInputBean> arrInputs = new ArrayList<AllInputBean>();
			convertJsonToBean(arrInputs, mJsonInfo);

			// 设置x坐标轴和y的值
			int i = 0;
			for (AllInputBean bean : arrInputs) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yAirs.add(new Entry(bean.getAir(), i));
				yWaters.add(new Entry(bean.getWater(), i));
				++i;
			}

			LineDataSet setElecs = new LineDataSet(yElecs, INPUT_ELEC);
			// setElecs.enableDashedLine(10f, 5f, 0f);
			setElecs.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
			setElecs.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
			setElecs.setLineWidth(2.5f);
			setElecs.setCircleSize(4f);
			setElecs.setFillAlpha(65);
			setElecs.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);

			LineDataSet setAirs = new LineDataSet(yAirs, INPUT_AIR);
			// setAirs.enableDashedLine(10f, 5f, 0f);
			setAirs.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
			setAirs.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
			setAirs.setLineWidth(2.5f);
			setAirs.setCircleSize(4f);
			setAirs.setFillAlpha(65);
			setAirs.setFillColor(ColorTemplate.VORDIPLOM_COLORS[1]);

			LineDataSet setWaters = new LineDataSet(yWaters, INPUT_WATER);
			// setWaters.enableDashedLine(10f, 5f, 0f);
			setWaters.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
			setWaters.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
			setWaters.setLineWidth(2.5f);
			setWaters.setCircleSize(4f);
			setWaters.setFillAlpha(65);
			setWaters.setFillColor(ColorTemplate.VORDIPLOM_COLORS[2]);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setAirs);
			dataSets.add(setWaters);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			// 暂时不增加上下区域线
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
			// 取得output信息

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将传入的Json转化成AllInputBean数组
	 * 
	 * @param arrInputs
	 * @param json
	 */
	private void convertJsonToBean(ArrayList<AllInputBean> arrInputs, String json) {
		try {
			JSONObject jObject = new JSONObject(json);
			// 取得input信息
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
				arrInputs.add(bean);
			}

			Collections.sort(arrInputs);
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

	private void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChartLongPressed(MotionEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub

	}

}
