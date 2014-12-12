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
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsNowFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsSearchFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsWeekFragment;
import com.bjgas.util.InfoUtils;
import com.bjgas.view.ListHeaderChartView;
import com.bjgas.view.ListHeaderChartView.OnNavigaterClick;
import com.bjgas.view.ListHeaderChartView.OnPopupWindowListItemClick;

public class NengyuanJiegouActivity extends NewBaseFragmentActivity {

	ListView lstView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();
	RelativeLayout rtlParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_framework);

		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);

		// 取得图片对象
		Bitmap bmZongjiegou = BitmapFactory.decodeResource(this.getResources(), R.drawable.nav_zongjiegou);
		Bitmap bmFadianji = BitmapFactory.decodeResource(this.getResources(), R.drawable.nav_fadianji);
		Bitmap bmZhileng = BitmapFactory.decodeResource(this.getResources(), R.drawable.nav_zhileng);
		Bitmap bmGuolu = BitmapFactory.decodeResource(this.getResources(), R.drawable.nav_guolu);
		Bitmap bmShengchanyongdian = BitmapFactory
				.decodeResource(this.getResources(), R.drawable.nav_shengchanyongdian);

		items.add(new GridItem(bmZongjiegou, InfoUtils.TIT_ZONGJIEGOU));
		items.add(new GridItem(bmFadianji, InfoUtils.TIT_FADIANJIXITONG));
		items.add(new GridItem(bmZhileng, InfoUtils.TIT_ZHILENGXITONG));
		items.add(new GridItem(bmGuolu, InfoUtils.TIT_GUOLUXITONG));
		items.add(new GridItem(bmShengchanyongdian, InfoUtils.TIT_SHENGCHANYONGDIAN));

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
				fragments.add(new InputsWeekFragment());
				fragments.add(new OutputsWeekFragment());
			} else if (sm == SearchMethod.Month) {
				fragments.add(new InputsMonthFragment());
				fragments.add(new OutputsMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new InputsSearchFragment(startM, endM));
				fragments.add(new OutputsSearchFragment(startM, endM));
			} else {
				fragments.add(new InputsNowFragment());
				fragments.add(new OutputsNowFragment());
			}
			break;
		case 1:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new FadianjiWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new FadianjiMonthFragment());
				// fragments.add(new FadianjiSearchFragment("2014-10",
				// "2014-12"));
			} else if (sm == SearchMethod.Search) {
				// fragments.add(new FadianjiMonthFragment());
				fragments.add(new FadianjiSearchFragment(startM, endM));
			} else {
				fragments.add(new FadianjiNowFragment());
			}
			break;
		case 2:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ZhilengWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ZhilengMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new ZhilengSearchFragment(startM, endM));
			} else {
				fragments.add(new ZhilengNowFragment());
			}
			break;
		case 3:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new GuoluWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new GuoluMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new GuoluSearchFragment(startM, endM));
			} else {
				fragments.add(new GuoluNowFragment());
			}
			break;
		case 4:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ShengchanyongdianWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ShengchanyongdianMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new ShengchanyongdianSearchFragment(startM, endM));
			} else {
				fragments.add(new ShengchanyongdianNowFragment());
			}
			break;
		default:
			break;
		}

	}
}
