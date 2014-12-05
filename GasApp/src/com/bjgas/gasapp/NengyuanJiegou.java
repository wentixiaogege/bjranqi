package com.bjgas.gasapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.bjgas.adapter.CustomGridViewAdapter;
import com.bjgas.bean.GridItem;
import com.bjgas.bean.GuoluBean;
import com.bjgas.common.BaseActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.fadianji.FadianjiActivity;
import com.bjgas.gasapp.guolu.GuoluActivity;
import com.bjgas.gasapp.shengchanyongdian.ShengchanyongdianActivity;
import com.bjgas.gasapp.zhileng.ZhilengActivity;
import com.bjgas.gasapp.zongjiegou.ZongjiegouCharts;

public class NengyuanJiegou extends BaseActivity {

	private GridView gridView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nengyuan_jiegou);

		// 取得gridview对象
		gridView = (GridView) findViewById(R.id.gridView1);
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

		// row_grid是一个xml，而Adapter是以这个xml为基础的。
		// Adapter所做的事，就是把items和row_grid给适配起来
		CustomGridViewAdapter adapter = new CustomGridViewAdapter(this, R.layout.row_grid, items);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					Log.d("Click", "0");
					intent = new Intent(NengyuanJiegou.this, ZongjiegouCharts.class);
					startActivity(intent);
					break;

				case 1:
					Log.d("Click", "1");
					intent = new Intent(NengyuanJiegou.this, FadianjiActivity.class);
					startActivity(intent);
					break;
				case 2:
					Log.d("Click", "2");
					intent = new Intent(NengyuanJiegou.this, ZhilengActivity.class);
					startActivity(intent);
					break;
				case 3:
					Log.d("Click", "3");
					intent = new Intent(NengyuanJiegou.this, GuoluActivity.class);
					startActivity(intent);
					break;
				case 4:
					Log.d("Click", "4");
					intent = new Intent(NengyuanJiegou.this, ShengchanyongdianActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}
}
