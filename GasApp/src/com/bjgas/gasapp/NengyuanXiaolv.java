package com.bjgas.gasapp;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bjgas.adapter.CustomGridViewAdapter;
import com.bjgas.bean.GridItem;
import com.bjgas.common.BaseActivity;
import com.bjgas.gasapp.dianzhilengxiaolv.DianzhilengXiaolvActivity;
import com.bjgas.gasapp.fadianji.FadianjiActivity;
import com.bjgas.gasapp.fadianjixiaolv.FadianjiXiaolvActivity;
import com.bjgas.gasapp.guolu.GuoluActivity;
import com.bjgas.gasapp.guoluxiaolv.GuoluXiaolvActivity;
import com.bjgas.gasapp.lengdongtaxiaolv.LengdongtaXiaolvActivity;
import com.bjgas.gasapp.lengqutaxiaolv.LengqutaXiaolvActivity;
import com.bjgas.gasapp.yurexiaolv.YureXiaolvActivity;
import com.bjgas.gasapp.zhileng.ZhilengActivity;
import com.bjgas.gasapp.zhiranjixiaolv.ZhiranjiXiaolvActivity;
import com.bjgas.gasapp.zongjiegou.ZongjiegouCharts;
import com.bjgas.gasapp.zongxiaolv.ZongXiaolvActivity;

public class NengyuanXiaolv extends BaseActivity {

	private GridView gridView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_nengyuan_xiaolv);

			// 取得gridview对象
			gridView = (GridView) findViewById(R.id.gridView1);
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
						intent = new Intent(NengyuanXiaolv.this, ZongXiaolvActivity.class);
						startActivity(intent);
						break;
					case 1:
						Log.d("Click", "1");
						intent = new Intent(NengyuanXiaolv.this, FadianjiXiaolvActivity.class);
						startActivity(intent);
						break;
					case 2:
						Log.d("Click", "2");
						intent = new Intent(NengyuanXiaolv.this, DianzhilengXiaolvActivity.class);
						startActivity(intent);
						break;
					case 3:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, GuoluXiaolvActivity.class);
						startActivity(intent);
						break;
					case 4:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, ZhiranjiXiaolvActivity.class);
						startActivity(intent);
						break;
					case 5:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, LengqutaXiaolvActivity.class);
						startActivity(intent);
						break;
					case 6:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, LengdongtaXiaolvActivity.class);
						startActivity(intent);
						break;
					case 7:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, NengyuanXiaolv.class);
						startActivity(intent);
						break;
					case 8:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, YureXiaolvActivity.class);
						startActivity(intent);
						break;
					default:
						break;
					}
				}
			});
		}
	}
}
