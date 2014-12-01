package com.bjgas.gasapp.xiaolv.zhiranjixiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;

public class ZhiranjiXiaolvActivity extends BaseFragmentActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new ZhiranjiXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new ZhiranjiXiaolvMonthFragment());
		} else {
			fragments.add(new ZhiranjiXiaolvWeekFragment());
			fragments.add(new ZhiranjiXiaolvMonthFragment());
		}
	}

}
