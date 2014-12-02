package com.bjgas.gasapp.zongjiegou;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bjgas.adapter.PageAdapter;
import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.view.HeaderChartView;

public class ZongjiegouCharts extends BaseFragmentActivity {

	// VerticalViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zongjiegou_charts);
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

		// // 初始化searchMethod
		// searchMethod = headerChartView.getSearchMethod();
		//
		// // 传入需要展示的fragments
		// pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		// ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		// fragments.add(new InputsFragment(searchMethod));
		// fragments.add(new OutputsFragment(searchMethod));
		// PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),
		// fragments);
		// pager.setAdapter(adapter);
		//
		// // 绑定popupWindow的click事件
		// headerChartView.setOnPopupWindowListItemClick(new
		// HeaderChartView.OnPopupWindowListItemClick() {
		// @Override
		// public void changeFragments() {
		// SearchMethod sm = headerChartView.getSearchMethod();
		// if (searchMethod.equals(sm))
		// return;
		// searchMethod = sm;
		// // 重新加载chart中的数据
		// clearFragments();
		// ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		//
		// fragments.add(new InputsFragment(sm));
		// fragments.add(new OutputsFragment(sm));
		//
		// PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),
		// fragments);
		// pager.removeAllViews();
		// pager.setAdapter(adapter);
		// }
		// });
	}

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		fragments.add(new InputsFragment(sm));
		fragments.add(new OutputsFragment(sm));
	}

}
