package com.bjgas.common;

import android.content.Context;
import android.widget.TextView;

import com.bjgas.gasapp.R;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MarkerView;
import com.github.mikephil.charting.utils.Utils;

public class GasMarkerView extends MarkerView {

	private TextView tvContent;
	BaseFragment<?> baseFragment;

	/**
	 * ͨ������getMarkViewDesc���������ضԲ�ͬͼ��������
	 * 
	 * @author gqq
	 *
	 */
	interface LabelInterface {
		public String getMarkViewDesc(int dataSetIndex);
	}

	public GasMarkerView(Context context, int layoutResource, BaseFragment<?> base) {
		super(context, layoutResource);

		tvContent = (TextView) findViewById(R.id.tvContent);
		baseFragment = base;
	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content
	@Override
	public void refreshContent(Entry e, int dataSetIndex) {

		if (e instanceof CandleEntry) {

			CandleEntry ce = (CandleEntry) e;

			tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
		} else {
			String info = String.format("%s:%s", baseFragment.getMarkViewDesc(dataSetIndex),
					Utils.formatNumber(e.getVal(), 2, true));
			tvContent.setText(info);
		}
	}
}