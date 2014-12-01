package com.bjgas.gasapp.xiaolv.lengdongtaxiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class LengdongtaXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
				if (sm == SearchMethod.Week)
			fragments.add(new LengdongtaXiaolvWeekFragment());
				else if (sm == SearchMethod.Month) {
			fragments.add(new LengdongtaXiaolvMonthFragment());
				} else {
			fragments.add(new LengdongtaXiaolvWeekFragment());
			fragments.add(new LengdongtaXiaolvMonthFragment());
		}
	}

}
