package com.bjgas.gasapp.zongjiegou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.R.id;
import com.bjgas.gasapp.R.layout;
import com.bjgas.gasapp.test.SineCosineFragment;

public class ZongjiegouCharts extends BaseFragmentActivity {

	// VerticalViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zongjiegou_charts);

		pager = (VerticalViewPager) findViewById(R.id.pager);
		PageAdapter a = new PageAdapter(getSupportFragmentManager());
		pager.setAdapter(a);

	}

	private class PageAdapter extends FragmentPagerAdapter {

		public PageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			Log.d("PageAdapter", "getItem:" + pos);
			Fragment f = null;

			switch (pos) {
			case 0:
				f = OutputsFragment.newInstance();
				break;
			case 1:
				// f = ComplexityFragment.newInstance();
				f = InputsFragment.newInstance();
				break;
			}
			return f;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

}
