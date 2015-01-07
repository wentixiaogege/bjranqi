package com.bjgas.common;

import org.apache.commons.lang3.StringUtils;

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
	 * 通过调用getMarkViewDesc方法，返回对不同图表的描述
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
			String info = StringUtils.EMPTY;
			String desc = baseFragment.getMarkViewDesc(dataSetIndex);
			if(!StringUtils.isEmpty(desc))
			info = String.format("%s:%s", desc,
					Utils.formatDecimal(e.getVal(), 2));
			else {
				info = Utils.formatDecimal(e.getVal(), 2);
			}
			tvContent.setText(info);
		}
	}
}