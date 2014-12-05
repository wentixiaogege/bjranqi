package com.bjgas.gasapp;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bjgas.adapter.CustomGridViewAdapter;
import com.bjgas.adapter.ListNavigateAdapter;
import com.bjgas.bean.GridItem;
import com.bjgas.common.BaseActivity;
import com.bjgas.view.HeaderChartView;
import com.bjgas.view.HeaderChartViewNew;

public class NengyuanJiegouActivity extends BaseActivity {

	ListView lstView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();
	com.bjgas.view.HeaderChartViewNew headerChartView;
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
		HeaderChartViewNew headerChartViewNew = new HeaderChartViewNew(this, items);
		rtlParent.addView(headerChartViewNew);

	}
}
