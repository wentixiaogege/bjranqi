package com.bjgas.gasapp.guolu;

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
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new GuoluWeekFragment());
		PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
		pager.setAdapter(adapter);

		// 初始化searchMethod
		searchMethod = SearchMethod.Week;

		// 绑定popupWindow的click事件
		headerChartView.setOnPopupWindowListItemClick(new HeaderChartView.OnPopupWindowListItemClick() {
			@Override
			public void changeFragments(SearchMethod sm) {
				if (searchMethod.equals(sm))
					return;
				searchMethod = sm;
				// 重新加载chart中的数据
				clearFragments();
				ArrayList<Fragment> fragments = new ArrayList<Fragment>();

				// 初始化fragments
				if (sm == SearchMethod.Week)
					fragments.add(new GuoluWeekFragment());
				else if (sm == SearchMethod.Month) {
					fragments.add(new GuoluMonthFragment());
				} else {
					fragments.add(new GuoluMonthFragment());
					fragments.add(new GuoluWeekFragment());
				}
				PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
				pager.removeAllViews();
				pager.setAdapter(adapter);
			}
		});
	}

}
