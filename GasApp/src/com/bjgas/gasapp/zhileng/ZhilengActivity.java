package com.bjgas.gasapp.zhileng;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.view.HeaderChartView;

public class ZhilengActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ZhilengActivity", "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhileng_charts);
		headerChartView = (HeaderChartView) findViewById(R.id.headChartView);
		// ������Ҫչʾ��fragments
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		clearAndReplaceFragments(SearchMethod.Now);
		// ��popupWindow��click�¼�
		headerChartView.setOnPopupWindowListItemClick(new HeaderChartView.OnPopupWindowListItemClick() {
			@Override
			public void changeFragments() {
				SearchMethod sm = headerChartView.getSearchMethod();
				if (getSearchMethod().equals(sm))
					return;
				clearAndReplaceFragments(sm);
			}
		});
	}

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ��ʼ��fragments
		if (sm == SearchMethod.Week)
			fragments.add(new ZhilengWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new ZhilengMonthFragment());
		} else {
			fragments.add(new ZhilengWeekFragment());
			fragments.add(new ZhilengMonthFragment());
		}
	}

}
