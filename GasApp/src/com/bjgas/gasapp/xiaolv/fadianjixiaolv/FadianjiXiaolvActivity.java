package com.bjgas.gasapp.xiaolv.fadianjixiaolv;

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
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvWeekFragment;
import com.bjgas.view.HeaderChartView;

public class FadianjiXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new FadianjiXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new FadianjiXiaolvMonthFragment());
		} else {
			fragments.add(new FadianjiXiaolvWeekFragment());
			fragments.add(new FadianjiXiaolvMonthFragment());
		}
	}


}
