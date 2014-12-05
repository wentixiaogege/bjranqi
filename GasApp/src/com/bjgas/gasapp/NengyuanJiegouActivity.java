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
import com.bjgas.gasapp.nengyuanjiegou.fadianji.FadianjiWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.guolu.GuoluWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.shengchanyongdian.ShengchanyongdianWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zhileng.ZhilengWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.InputsWeekFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsMonthFragment;
import com.bjgas.gasapp.nengyuanjiegou.zongjiegou.OutputsWeekFragment;
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
		setContentView(R.layout.activity_nengyuan_jiegou_new);

		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);

		// 取得图片对象
		Bitmap bmZongjiegou = BitmapFactory.decodeResource(this.getResources(), R.drawable.zongjiegou);
		Bitmap bmFadianji = BitmapFactory.decodeResource(this.getResources(), R.drawable.fadianji);
		Bitmap bmZhileng = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhileng);
		Bitmap bmGuolu = BitmapFactory.decodeResource(this.getResources(), R.drawable.guolu);
		Bitmap bmShengchanyongdian = BitmapFactory.decodeResource(this.getResources(), R.drawable.shengchanyongdian);

		items.add(new GridItem(bmZongjiegou, "总结构"));
		items.add(new GridItem(bmFadianji, "发电机"));
		items.add(new GridItem(bmZhileng, "制冷系统"));
		items.add(new GridItem(bmGuolu, "锅炉系统"));
		items.add(new GridItem(bmShengchanyongdian, "生产用电"));

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
			} else {
				fragments.add(new InputsWeekFragment());
				fragments.add(new InputsMonthFragment());
			}
			break;
		case 1:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new FadianjiWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new FadianjiMonthFragment());
			} else {
				fragments.add(new FadianjiWeekFragment());
				fragments.add(new FadianjiMonthFragment());
			}
			break;
		case 2:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ZhilengWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ZhilengMonthFragment());
			} else {
				fragments.add(new ZhilengWeekFragment());
				fragments.add(new ZhilengMonthFragment());
			}
			break;
		case 3:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new GuoluWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new GuoluMonthFragment());
			} else {
				fragments.add(new GuoluMonthFragment());
				fragments.add(new GuoluWeekFragment());
			}
			break;
		case 4:
			// 初始化fragments
			if (sm == SearchMethod.Week)
				fragments.add(new ShengchanyongdianWeekFragment());
			else if (sm == SearchMethod.Month) {
				fragments.add(new ShengchanyongdianMonthFragment());
			} else {
				fragments.add(new ShengchanyongdianMonthFragment());
				fragments.add(new ShengchanyongdianWeekFragment());
			}
			break;
		default:
			break;
		}

	}
}
