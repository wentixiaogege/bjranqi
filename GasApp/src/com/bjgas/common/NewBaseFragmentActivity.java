package com.bjgas.common;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.pm.ActivityInfo;
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

/**
 * 由于任务骤然变化，所以只能在这个基础上修改，见谅
 * 
 * @author gqq
 *
 */
public abstract class NewBaseFragmentActivity extends FragmentActivity {

	protected com.bjgas.view.ListHeaderChartView headerChartView;
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
	 * 清除所有的Fragments
	 */
	protected void clearFragments() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		if (null != fragments)
			fragments.clear();
	}

	/**
	 * 根据sm的不同，安装不同的fragments
	 * 
	 * @param sm
	 * @param fragments
	 */
	public abstract void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments, int listPosition);

	/**
	 * 根据sm的不同，更新Adapter，改变chart的显示
	 * 
	 * @param sm
	 */
	protected void clearAndReplaceFragments(SearchMethod sm, int position) {
		// 重新加载chart中的数据
		clearFragments();
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();

		addNewFragments(sm, fragments, position);

		searchMethod = sm;

		PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragments);
		pager.removeAllViews();
		pager.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

}
