package com.bjgas.gasapp.xiaolv.yurexiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class YureXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new YureXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new YureXiaolvMonthFragment());
		} else {
			fragments.add(new YureXiaolvWeekFragment());
			fragments.add(new YureXiaolvMonthFragment());
		}
	}


}
