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
import com.bjgas.common.BaseActivity;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.fadianji.FadianjiActivity;
import com.bjgas.gasapp.zhileng.ZhilengActivity;
import com.bjgas.gasapp.zongjiegou.ZongjiegouCharts;

public class NengyuanJiegou extends BaseActivity {

	private GridView gridView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nengyuan_jiegou);

		// ȡ��gridview����
		gridView = (GridView) findViewById(R.id.gridView1);
		// ȡ��ͼƬ����
		Bitmap bmZongjiegou = BitmapFactory.decodeResource(this.getResources(), R.drawable.zongjiegou);
		Bitmap bmFadianji = BitmapFactory.decodeResource(this.getResources(), R.drawable.fadianji);
		Bitmap bmZhileng = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhileng);
		Bitmap bmGuolu = BitmapFactory.decodeResource(this.getResources(), R.drawable.guolu);
		Bitmap bmShengchanyongdian = BitmapFactory.decodeResource(this.getResources(), R.drawable.shengchanyongdian);

		items.add(new GridItem(bmZongjiegou, "�ܽṹ"));
		items.add(new GridItem(bmFadianji, "�����"));
		items.add(new GridItem(bmZhileng, "����ϵͳ"));
		items.add(new GridItem(bmGuolu, "��¯ϵͳ"));
		items.add(new GridItem(bmShengchanyongdian, "�����õ�"));

		// row_grid��һ��xml����Adapter�������xmlΪ�����ġ�
		// Adapter�������£����ǰ�items��row_grid����������
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
				default:
					break;
				}
			}
		});
	}
}
