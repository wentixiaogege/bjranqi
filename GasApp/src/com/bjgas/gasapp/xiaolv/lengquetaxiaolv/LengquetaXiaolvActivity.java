package com.bjgas.gasapp.xiaolv.lengquetaxiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class LengquetaXiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// ≥ı ºªØfragments
		if (sm == SearchMethod.Week)
			fragments.add(new LengquetaXiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new LengquetaXiaolvMonthFragment());
		} else {
			fragments.add(new LengquetaXiaolvWeekFragment());
			fragments.add(new LengquetaXiaolvMonthFragment());
		}
	}

}
