package com.bjgas.common;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bjgas.adapter.PageAdapter;
import com.bjgas.bean.PopupItem;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvWeekFragment;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected com.bjgas.view.HeaderChartView headerChartView;
	protected com.bjgas.common.VerticalViewPager pager;
	private SearchMethod searchMethod;

	protected SearchMethod getSearchMethod() {
		return searchMethod;
	}

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
	 * ������е�Fragments
	 */
	protected void clearFragments() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		if (null != fragments)
			fragments.clear();
	}

	/**
	 * ����sm�Ĳ�ͬ����װ��ͬ��fragments
	 * 
	 * @param sm
	 * @param fragments
	 */
	public abstract void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments);

	/**
	 * ����sm�Ĳ�ͬ������Adapter���ı�chart����ʾ
	 * 
	 * @param sm
	 */
	protected void clearAndReplaceFragments(SearchMethod sm) {
		// ���¼���chart�е�����
		clearFragments();
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();

		addNewFragments(sm, fragments);

		searchMethod = sm;

		PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
		pager.removeAllViews();
		pager.setAdapter(adapter);
	}

}
