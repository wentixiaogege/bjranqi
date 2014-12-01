package com.bjgas.gasapp.xiaolv.guoluxiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class GuoluXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new GuoluXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new GuoluXiaolvMonthFragment());
		} else {
			fragments.add(new GuoluXiaolvWeekFragment());
			fragments.add(new GuoluXiaolvMonthFragment());
		}
	}


}
