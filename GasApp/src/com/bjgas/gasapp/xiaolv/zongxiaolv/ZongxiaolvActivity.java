package com.bjgas.gasapp.xiaolv.zongxiaolv;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.xiaolv.XiaolvActivity;

public class ZongxiaolvActivity extends XiaolvActivity {

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments) {
		// 初始化fragments
		if (sm == SearchMethod.Week)
			fragments.add(new ZongxiaolvWeekFragment());
		else if (sm == SearchMethod.Month) {
			fragments.add(new ZongxiaolvMonthFragment());
		} else {
			fragments.add(new ZongxiaolvWeekFragment());
			fragments.add(new ZongxiaolvMonthFragment());
		}
	}

}
