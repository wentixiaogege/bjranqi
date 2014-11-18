package com.bjgas.common;

import android.content.Context;
import android.widget.TextView;

import com.bjgas.gasapp.R;
import com.bjgas.gasapp.ZongjiegouChart;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MarkerView;
import com.github.mikephil.charting.utils.Utils;

public class MyMarkerView extends MarkerView {

	private TextView tvContent;

	public MyMarkerView(Context context, int layoutResource) {
		super(context, layoutResource);

		tvContent = (TextView) findViewById(R.id.tvContent);
	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content
	@Override
	public void refreshContent(Entry e, int dataSetIndex) {

		if (e instanceof CandleEntry) {

			CandleEntry ce = (CandleEntry) e;

			tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
		} else {
			String label = dataSetIndex == 0 ? ZongjiegouChart.INPUT_ELEC
					: (dataSetIndex == 1 ? ZongjiegouChart.INPUT_AIR : ZongjiegouChart.INPUT_WATER);
			String info = String.format("%s:%s", label, Utils.formatNumber(e.getVal(), 2, true));
			tvContent.setText(info);
		}
	}
}