package com.bjgas.gasapp.xiaolv.dianzhilengxiaolv;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bjgas.adapter.PageAdapter;
import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;
import com.bjgas.view.HeaderChartView;

public class DianzhilengXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// 初始化fragments
		if (sm == SearchMethod.Week)
			fragments.add(new DianzhilengXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new DianzhilengXiaolvMonthFragment());
		} else {
			fragments.add(new DianzhilengXiaolvWeekFragment());
			fragments.add(new DianzhilengXiaolvMonthFragment());
		}
	}

}
