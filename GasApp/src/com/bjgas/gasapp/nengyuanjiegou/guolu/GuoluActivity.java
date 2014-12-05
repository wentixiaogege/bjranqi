package com.bjgas.gasapp.nengyuanjiegou.guolu;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bjgas.adapter.PageAdapter;
import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.view.HeaderChartView;

public class GuoluActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getSimpleName(), "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guolu);
		headerChartView = (HeaderChartView) findViewById(R.id.headChartView);

		// 传入需要展示的fragments
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		clearAndReplaceFragments(SearchMethod.Now);
		// 绑定popupWindow的click事件
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
		// 初始化fragments
		if (sm == SearchMethod.Week)
			fragments.add(new GuoluWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new GuoluMonthFragment());
		} else {
			fragments.add(new GuoluMonthFragment());
			fragments.add(new GuoluWeekFragment());
		}
	}

}
