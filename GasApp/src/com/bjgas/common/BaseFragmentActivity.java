package com.bjgas.common;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bjgas.bean.PopupItem;
import com.bjgas.gasapp.R;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected com.bjgas.view.HeaderChartView headerChartView;
	protected com.bjgas.common.VerticalViewPager pager;
	protected SearchMethod searchMethod;
	protected ArrayList<PopupItem> popupItems;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
	}

	@Override
	protected void onStop() {
		Log.d("Life Cycle", "BaseFragmentActivity on stop");
		if (null != headerChartView)
			headerChartView.dismissPopviewWindow();
		super.onStop();
	}

	/**
	 * 清除所有的Fragments
	 */
	public void clearFragments() {
		getSupportFragmentManager().getFragments().clear();
	}

}
