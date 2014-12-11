package com.bjgas.gasapp;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bjgas.bean.GridItem;
import com.bjgas.common.NewBaseFragmentActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.jingyingfenxi.chanchu.ChanchuMonthFragment;
import com.bjgas.gasapp.jingyingfenxi.chanchu.ChanchuNowFragment;
import com.bjgas.gasapp.jingyingfenxi.chanchu.ChanchuSearchFragment;
import com.bjgas.gasapp.jingyingfenxi.chanchu.ChanchuWeekFragment;
import com.bjgas.gasapp.jingyingfenxi.touru.TouruDetailMonthFragment;
import com.bjgas.gasapp.jingyingfenxi.touru.TouruDetailSearchFragment;
import com.bjgas.gasapp.jingyingfenxi.touru.TouruDetailWeekFragment;
import com.bjgas.gasapp.jingyingfenxi.touru.TouruDetailNowFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.ShouruMonthFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.ShouruNowFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.ShouruSearchFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.ShouruWeekFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.TouruMonthFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.TouruNowFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.TouruSearchFragment;
import com.bjgas.gasapp.jingyingfenxi.zongxitong.TouruWeekFragment;
import com.bjgas.util.InfoUtils;
import com.bjgas.view.ListHeaderChartView;
import com.bjgas.view.ListHeaderChartView.OnNavigaterClick;
import com.bjgas.view.ListHeaderChartView.OnPopupWindowListItemClick;

public class JingyingfenxiActivity extends NewBaseFragmentActivity {

	ListView lstView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();
	RelativeLayout rtlParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_framework);

		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);

		// 取得图片对象
		Bitmap bmZongxitong = BitmapFactory.decodeResource(this.getResources(), R.drawable.zongxitongfenxi);
		Bitmap bmTouru = BitmapFactory.decodeResource(this.getResources(), R.drawable.tourufenxi);
		Bitmap bmChanchu = BitmapFactory.decodeResource(this.getResources(), R.drawable.chanchufenxi);

		items.add(new GridItem(bmZongxitong, InfoUtils.TIT_ZONGXITONGJINGYING));
		items.add(new GridItem(bmTouru, InfoUtils.TIT_TOURUFENXI));
		items.add(new GridItem(bmChanchu, InfoUtils.TIT_CHANCHUFENXI));

		// 新建一个headchartview
		headerChartView = new ListHeaderChartView(this, items);

		/**
		 * 绑定左侧导航栏
		 */
		headerChartView.setOnNavigaterClick(new OnNavigaterClick() {
			@Override
			public void addNewFragments(View view, int position, long id) {
				ListHeaderChartView v = (ListHeaderChartView) view;
				Log.d("HeaderChartViewNew", "Now search method is " + v.getSearchMethod() + " position is " + position);
				clearAndReplaceFragments(v.getSearchMethod(), position);
			}
		});

		/**
		 * 当日期等选择时，调用该回调函数
		 */
		headerChartView.setOnPopupWindowListItemClick(new OnPopupWindowListItemClick() {

			@Override
			public void changeFragments(View view) {
				ListHeaderChartView v = (ListHeaderChartView) view;
				Log.d("HeaderChartViewNew",
						"Now search method is " + v.getSearchMethod() + " position is " + v.getmDisplayedItem());
				clearAndReplaceFragments(v.getSearchMethod(), v.getmDisplayedItem());
			}

			@Override
			public void changeSearchFragments(View view, String startMonth, String endMonth) {
				startM = startMonth;
				endM = endMonth;
				ListHeaderChartView v = (ListHeaderChartView) view;
				clearAndReplaceFragments(v.getSearchMethod(), v.getmDisplayedItem());
			}
		});

		rtlParent.addView(headerChartView);
		pager = (VerticalViewPager) headerChartView.findViewById(R.id.pager);
	}

	@Override
	public void addNewFragments(SearchMethod sm, ArrayList<Fragment> fragments, int lstPostion) {
		// TODO Auto-generated method stub
		switch (lstPostion) {
		case 0:
			if (sm == SearchMethod.Week) {
				fragments.add(new TouruWeekFragment());
				fragments.add(new ShouruWeekFragment());
			} else if (sm == SearchMethod.Month) {
				fragments.add(new TouruMonthFragment());
				fragments.add(new ShouruMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new TouruSearchFragment(startM, endM));
				fragments.add(new ShouruSearchFragment(startM, endM));
			} else {
				fragments.add(new TouruNowFragment());
				fragments.add(new ShouruNowFragment());
			}
			break;
		case 1:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new TouruDetailWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new TouruDetailMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new TouruDetailSearchFragment(startM, endM));
			} else {
				fragments.add(new TouruDetailNowFragment());

			}
			break;
		case 2:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ChanchuWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ChanchuMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new ChanchuSearchFragment(startM, endM));
			} else {
				fragments.add(new ChanchuNowFragment());
			}
			break;

		default:
			break;
		}

	}
}
