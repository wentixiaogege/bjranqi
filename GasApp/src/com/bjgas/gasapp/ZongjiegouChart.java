package com.bjgas.gasapp;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.bjgas.common.BaseActivity;
import com.bjgas.common.MyMarkerView;
import com.bjgas.util.DateUtils;
import com.bjgas.util.NetUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.LimitLine.LimitLabelPosition;

public class ZongjiegouChart extends BaseActivity implements OnChartGestureListener, OnChartValueSelectedListener {

	private static final int GET_JSON_SUCCESSFUL = 1;
	private static final int GET_JSON_ERROR = 9;
	private static final String TAG_ZONGJIEGOUCHART = "ZongjiegouChart";

	private String mJsonInfo;
	private String mRequestUrl;
	private LineChart mChart;

	private double minValue = 0.0;
	private double maxValue = 100.0;
	public ZongjiegouChart() {
		super("construction", "all");
		mRequestUrl = String.format("%s?module=%s&type=%s&date=%s", BASE_URL, getModule(), getType(),
				DateUtils.getTodaySimplestr());

	}

	// ����һ��Handler�������߳�ͬ����
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zongjiegou_chart);

		mChart = (LineChart) findViewById(R.id.chart1);

		// �¿���һ���̣߳����Json����
		new Thread() {

			@Override
			public void run() {
				Log.d(TAG_ZONGJIEGOUCHART, String.format("mRequestUrl:%s", mRequestUrl));
				mJsonInfo = NetUtils.connServerForResult(ZongjiegouChart.this, mRequestUrl);
				if (StringUtils.isNotEmpty(mJsonInfo))
					mHandler.sendEmptyMessage(GET_JSON_SUCCESSFUL);
				else {
					mHandler.sendEmptyMessage(GET_JSON_ERROR);
				}
			};
		}.start();

		// ��ʼ��chart
		initChart();
	}

	/**
	 * ��ʼ��chart
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
		mChart.setNoDataTextDescription("��ʱû��ȡ������");

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
		mChart.setPinchZoom(true);

		// set an alternative background color
		// mChart.setBackgroundColor(Color.GRAY);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it��������棬����ʾ��Ϣ��
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

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
	 * ���߳�ȡ�����ݺ���ʾͼ��
	 */
	private void displayChart() {
		try {
			Log.d(TAG_ZONGJIEGOUCHART, String.format("get json info:%s", mJsonInfo));
			JSONObject jObject = new JSONObject(mJsonInfo);
			// ���ú�������
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yAirs = new ArrayList<Entry>();
			ArrayList<Entry> yWaters = new ArrayList<Entry>();

			// ȡ��input��Ϣ
			JSONArray inputs = jObject.getJSONArray("inputs");
			for (int i = 0; i < inputs.length(); i++) {
				try {
					JSONObject oneObject = inputs.getJSONObject(i);
					// Pulling items from the array
					xTimes.add(oneObject.getInt("time") + "");
					yElecs.add(new Entry(getProperData(oneObject, "elec"), i));
					yAirs.add(new Entry(getProperData(oneObject, "air"), i));
					yWaters.add(new Entry(getProperData(oneObject, "water"), i));
				} catch (JSONException e) {
					Log.d("Error", e.getMessage());
				}
			}

			LineDataSet setElecs = new LineDataSet(yElecs, "��");
			setElecs.enableDashedLine(10f, 5f, 0f);
			setElecs.setColor(0xFF7A1C1D);
			setElecs.setCircleColor(0xFF7A1C1D);
			setElecs.setLineWidth(2f);
			setElecs.setCircleSize(4f);
			setElecs.setFillAlpha(65);
			setElecs.setFillColor(0xFF7A1C1D);

			LineDataSet setAirs = new LineDataSet(yAirs, "��");
			setAirs.enableDashedLine(10f, 5f, 0f);
			setAirs.setColor(0xFF30597F);
			setAirs.setCircleColor(0xFF30597F);
			setAirs.setLineWidth(2f);
			setAirs.setCircleSize(4f);
			setAirs.setFillAlpha(65);
			setAirs.setFillColor(0xFF30597F);

			LineDataSet setWaters = new LineDataSet(yWaters, "ˮ");
			setWaters.enableDashedLine(10f, 5f, 0f);
			setWaters.setColor(0xFF675708);
			setWaters.setCircleColor(0xFF675708);
			setWaters.setLineWidth(2f);
			setWaters.setCircleSize(4f);
			setWaters.setFillAlpha(65);
			setWaters.setFillColor(0xFF675708);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setAirs);
			dataSets.add(setWaters);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			LimitLine ll1 = new LimitLine(100f);
			ll1.setLineWidth(1f);
			ll1.enableDashedLine(10f, 10f, 0f);
			ll1.setDrawValue(false);
			ll1.setLabelPosition(LimitLabelPosition.RIGHT);

			LimitLine ll2 = new LimitLine(0f);
			ll2.setLineWidth(1f);
			ll2.enableDashedLine(10f, 10f, 0f);
			ll2.setDrawValue(false);
			ll2.setLabelPosition(LimitLabelPosition.RIGHT);

			data.addLimitLine(ll1);
			data.addLimitLine(ll2);

			mChart.setData(data);
			mChart.invalidate();
			// ȡ��output��Ϣ

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
