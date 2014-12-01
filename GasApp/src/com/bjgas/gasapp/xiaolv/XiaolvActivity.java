package com.bjgas.gasapp.xiaolv;

import android.os.Bundle;
import android.util.Log;

import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.view.HeaderChartView;

public abstract class XiaolvActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ZhilengActivity", "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaolv);
		headerChartView = (HeaderChartView) findViewById(R.id.headChartView);

		// 传入需要展示的fragments
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		clearAndReplaceFragments(SearchMethod.Now);

		// ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		// fragments.add(new DianzhilengXiaolvNowFragment());
		// PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),
		// fragments);
		// pager.setAdapter(adapter);

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
}
