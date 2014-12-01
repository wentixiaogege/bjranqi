package com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class NengyuanliyongXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new NengyuanliyongXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new NengyuanliyongXiaolvMonthFragment());
		} else {
			fragments.add(new NengyuanliyongXiaolvWeekFragment());
			fragments.add(new NengyuanliyongXiaolvMonthFragment());
		}
	}


}
