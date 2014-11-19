package com.bjgas.common;

import java.util.ArrayList;

import com.bjgas.bean.AllInputBean;
import com.bjgas.common.MyMarkerView.LabelInterface;
import com.bjgas.gasapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.support.v4.app.Fragment;

public abstract class BaseFragment<T> extends Fragment implements LabelInterface {
	public static final String INPUT_ELEC = "电";
	public static final String INPUT_AIR = "气";
	public static final String INPUT_WATER = "水";

	public static final String OUTPUT_ELEC = "电";
	public static final String OUTPUT_COLD = "冷";
	public static final String OUTPUT_HOT = "热";

	protected static final int GET_JSON_SUCCESSFUL = 1;
	protected static final int GET_JSON_ERROR = 9;

	protected LineChart mChart;

	public abstract void convertJsonToBean(ArrayList<T> arrInputs, String json);

	protected abstract void displayChart();

	protected abstract void displayErr();

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


}
