package com.bjgas.common;

import android.view.MotionEvent;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;

public class ChartDoNothing implements OnChartValueSelectedListener, OnChartGestureListener {

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

	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub

	}

}
