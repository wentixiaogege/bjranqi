package com.bjgas.common;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.bjgas.bean.AllInputBean;
import com.bjgas.common.MyMarkerView.LabelInterface;
import com.bjgas.gasapp.R;
import com.bjgas.util.NetUtils;
import com.bjgas.util.TagUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class BaseFragment<T> extends Fragment implements LabelInterface {

	protected final String REQUEST_WEBSITE = "http://gqqapp.sinaapp.com/ranqi.php";
	protected String act_module;
	protected String act_type;



	protected static final int GET_JSON_SUCCESSFUL = 1;
	protected static final int GET_JSON_ERROR = 9;

	protected LineChart mChart;

	public abstract void convertJsonToBean(String json);

	protected abstract void displayChart();

	protected abstract void displayErr();

	protected ArrayList<T> jsonResults = new ArrayList<T>();
	protected String mJsonInfo;

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

	/**
	 * 初始化chart
	 */
	public void initChart() {

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

	public LineDataSet getDefaultDataset(ArrayList<Entry> yValus, String label, int chartNum) {
		LineDataSet dataset = new LineDataSet(yValus, label);
		// setElecs.enableDashedLine(10f, 5f, 0f);
		if (1 == chartNum) {
			dataset.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
			dataset.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
			dataset.setLineWidth(2.5f);
			dataset.setCircleSize(4f);
			dataset.setFillAlpha(65);
			dataset.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
		} else if (2 == chartNum) {
			dataset.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
			dataset.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
			dataset.setLineWidth(2.5f);
			dataset.setCircleSize(4f);
			dataset.setFillAlpha(65);
			dataset.setFillColor(ColorTemplate.VORDIPLOM_COLORS[1]);
		} else {
			dataset.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
			dataset.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
			dataset.setLineWidth(2.5f);
			dataset.setCircleSize(4f);
			dataset.setFillAlpha(65);
			dataset.setFillColor(ColorTemplate.VORDIPLOM_COLORS[2]);
		}
		return dataset;
	}

	/**
	 * 开启一个新的线程，调用接口，取得数据
	 */
	public void getDataFromweb() {
		// 新开启一个线程，获得Json数据
		new Thread() {
			@Override
			public void run() {
				String mRequestUrl = getRequestUrl();
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("request url:%s", mRequestUrl));
				mJsonInfo = NetUtils.connServerForResult(getActivity(), mRequestUrl);
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("get json info:%s", mJsonInfo));
				if (StringUtils.isNotEmpty(mJsonInfo))
					mHandler.sendEmptyMessage(GET_JSON_SUCCESSFUL);
				else {
					mHandler.sendEmptyMessage(GET_JSON_ERROR);
				}
			}

		}.start();
	}

	public abstract String getRequestUrl();
}
