package com.bjgas.gasapp.dianzhilengxiaolv;

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

public class DianzhilengXiaolvActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ZhilengActivity", "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaolv);
		headerChartView = (HeaderChartView) findViewById(R.id.headChartView);

		// ������Ҫչʾ��fragments
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new DianzhilengXiaolvNowFragment());
		PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
		pager.setAdapter(adapter);

		// ��ʼ��searchMethod
		searchMethod = SearchMethod.Week;

		// ��popupWindow��click�¼�
		headerChartView.setOnPopupWindowListItemClick(new HeaderChartView.OnPopupWindowListItemClick() {
			@Override
			public void changeFragments(SearchMethod sm) {
				if (searchMethod.equals(sm))
					return;
				searchMethod = sm;
				// ���¼���chart�е�����
				clearFragments();
				ArrayList<Fragment> fragments = new ArrayList<Fragment>();

				// ��ʼ��fragments
				if (sm == SearchMethod.Week)
					fragments.add(new DianzhilengXiaolvWeekFragment());
				else if (sm == SearchMethod.Month) {
					fragments.add(new DianzhilengXiaolvMonthFragment());
				} else {
					fragments.add(new DianzhilengXiaolvWeekFragment());
					fragments.add(new DianzhilengXiaolvMonthFragment());
				}
				PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
				pager.removeAllViews();
				pager.setAdapter(adapter);
			}
		});
	}

}
