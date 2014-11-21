package com.bjgas.gasapp.fadianji;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bjgas.adapter.PageAdapter;
import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.view.HeaderChartView;

public class FadianjiActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadianji_charts);
		headerChartView = (HeaderChartView) findViewById(R.id.headChartView);

		// 传入需要展示的fragments
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new FadianjiWeekFragment());
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
					fragments.add(new FadianjiWeekFragment());
				else if (sm == SearchMethod.Month) {
					fragments.add(new FadianjiMonthFragment());
				} else {
					fragments.add(new FadianjiWeekFragment());
				}
				PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
				pager.removeAllViews();
				pager.setAdapter(adapter);
			}
		});
	}


}
