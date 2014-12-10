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
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiSearchFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.dianzhilengxiaolv.DianzhilengXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.fadianjixiaolv.FadianjiXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.fadianjixiaolv.FadianjiXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.fadianjixiaolv.FadianjiXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.guoluxiaolv.GuoluXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.guoluxiaolv.GuoluXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.guoluxiaolv.GuoluXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.guoluxiaolv.GuoluXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.lengdongtaxiaolv.LengdongtaXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.lengdongtaxiaolv.LengdongtaXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.lengdongtaxiaolv.LengdongtaXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.lengdongtaxiaolv.LengdongtaXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.lengquetaxiaolv.LengquetaXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.lengquetaxiaolv.LengquetaXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.lengquetaxiaolv.LengquetaXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.lengquetaxiaolv.LengquetaXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv.NengyuanliyongXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv.NengyuanliyongXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv.NengyuanliyongXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.nengyuanliyongxiaolv.NengyuanliyongXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.yurexiaolv.YureXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.yurexiaolv.YureXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.yurexiaolv.YureXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.yurexiaolv.YureXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.zhiranjixiaolv.ZhiranjiXiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.zhiranjixiaolv.ZhiranjiXiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.zhiranjixiaolv.ZhiranjiXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.zhiranjixiaolv.ZhiranjiXiaolvWeekFragment;
import com.bjgas.gasapp.xiaolv.zongxiaolv.ZongXiaolvSearchFragment;
import com.bjgas.gasapp.xiaolv.zongxiaolv.ZongxiaolvMonthFragment;
import com.bjgas.gasapp.xiaolv.zongxiaolv.ZongxiaolvNowFragment;
import com.bjgas.gasapp.xiaolv.zongxiaolv.ZongxiaolvWeekFragment;
import com.bjgas.view.ListHeaderChartView;
import com.bjgas.view.ListHeaderChartView.OnNavigaterClick;
import com.bjgas.view.ListHeaderChartView.OnPopupWindowListItemClick;

public class NengyuanxiaolvActivity extends NewBaseFragmentActivity {

	ListView lstView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();
	RelativeLayout rtlParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_framework);

		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);

		// 取得图片对象
		Bitmap bmZongxitongXiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.zongxitongxiaolv);
		Bitmap bmfadianjixiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.fadianjixiaolv);
		Bitmap bmdianzhilengxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.dianzhilengxiaolv);
		Bitmap bmguoluxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.guoluxiaolv);
		Bitmap bmzhiranjixiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhiranjixiaolv);
		Bitmap bmlengquetaxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.lengquetaxiaolv);
		Bitmap bmlengdongtaxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.lengdongtaxiaolv);
		Bitmap bmnengyuanliyonglv = BitmapFactory.decodeResource(this.getResources(), R.drawable.nengyuanliyonglv);
		Bitmap bmyureliyonglv = BitmapFactory.decodeResource(this.getResources(), R.drawable.yureliyonglv);

		items.add(new GridItem(bmZongxitongXiaolv, "总系统效率"));
		items.add(new GridItem(bmfadianjixiaolv, "发电机效率"));
		items.add(new GridItem(bmdianzhilengxiaolv, "电制冷效率"));
		items.add(new GridItem(bmguoluxiaolv, "锅炉效率"));
		items.add(new GridItem(bmzhiranjixiaolv, "直燃机效率"));
		items.add(new GridItem(bmlengquetaxiaolv, "冷却塔效率"));
		items.add(new GridItem(bmlengdongtaxiaolv, "冷冻塔效率"));
		items.add(new GridItem(bmnengyuanliyonglv, "能源利用率"));
		items.add(new GridItem(bmyureliyonglv, "余热利用率"));

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
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ZongxiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ZongxiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new ZongXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new ZongxiaolvNowFragment());
			}
			break;
		case 1:
			if (sm == SearchMethod.Week)
				fragments.add(new FadianjiXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new FadianjiXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new FadianjiSearchFragment(startM, endM));
			} else {
				fragments.add(new FadianjiXiaolvNowFragment());
			}
			break;
		case 2:
			if (sm == SearchMethod.Week)
				fragments.add(new DianzhilengXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new DianzhilengXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new DianzhilengXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new DianzhilengXiaolvNowFragment());
			}
			break;
		case 3:
			if (sm == SearchMethod.Week)
				fragments.add(new GuoluXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new GuoluXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new GuoluXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new GuoluXiaolvNowFragment());
			}
			break;
		case 4:
			if (sm == SearchMethod.Week)
				fragments.add(new ZhiranjiXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ZhiranjiXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new ZhiranjiXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new ZhiranjiXiaolvNowFragment());
			}
			break;
		case 5:
			if (sm == SearchMethod.Week)
				fragments.add(new LengquetaXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new LengquetaXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new LengquetaXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new LengquetaXiaolvNowFragment());
			}
			break;
		case 6:
			if (sm == SearchMethod.Week)
				fragments.add(new LengdongtaXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new LengdongtaXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new LengdongtaXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new LengdongtaXiaolvNowFragment());
			}
			break;
		case 7:
			if (sm == SearchMethod.Week)
				fragments.add(new NengyuanliyongXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new NengyuanliyongXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new NengyuanliyongXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new NengyuanliyongXiaolvNowFragment());
			}
			break;
		case 8:
			if (sm == SearchMethod.Week)
				fragments.add(new YureXiaolvWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new YureXiaolvMonthFragment());
			} else if (sm == SearchMethod.Search) {
				fragments.add(new YureXiaolvSearchFragment(startM, endM));
			} else {
				fragments.add(new YureXiaolvNowFragment());
			}
			break;
		default:
			break;
		}

	}
}
