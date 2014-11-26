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
import com.bjgas.gasapp.fadianji.FadianjiActivity;
import com.bjgas.gasapp.guolu.GuoluActivity;
import com.bjgas.gasapp.zhileng.ZhilengActivity;
import com.bjgas.gasapp.zongjiegou.ZongjiegouCharts;

public class NengyuanXiaolv extends BaseActivity {

	private GridView gridView;
	private ArrayList<GridItem> items = new ArrayList<GridItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_nengyuan_xiaolv);

			// ȡ��gridview����
			gridView = (GridView) findViewById(R.id.gridView1);
			// ȡ��ͼƬ����
			Bitmap bmZongxitongXiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.zongxitongxiaolv);
			Bitmap bmfadianjixiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.fadianjixiaolv);
			Bitmap bmdianzhilengxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.dianzhilengxiaolv);
			Bitmap bmguoluxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.guoluxiaolv);
			Bitmap bmzhiranjixiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhiranjixiaolv);
			Bitmap bmlengquetaxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.lengquetaxiaolv);
			Bitmap bmlengdongtaxiaolv = BitmapFactory.decodeResource(this.getResources(), R.drawable.lengdongtaxiaolv);
			Bitmap bmnengyuanliyonglv = BitmapFactory.decodeResource(this.getResources(), R.drawable.nengyuanliyonglv);
			Bitmap bmyureliyonglv = BitmapFactory.decodeResource(this.getResources(), R.drawable.yureliyonglv);

			items.add(new GridItem(bmZongxitongXiaolv, "��ϵͳЧ��"));
			items.add(new GridItem(bmfadianjixiaolv, "�����Ч��"));
			items.add(new GridItem(bmdianzhilengxiaolv, "������Ч��"));
			items.add(new GridItem(bmguoluxiaolv, "��¯Ч��"));
			items.add(new GridItem(bmzhiranjixiaolv, "ֱȼ��Ч��"));
			items.add(new GridItem(bmlengquetaxiaolv, "��ȴ��Ч��"));
			items.add(new GridItem(bmlengdongtaxiaolv, "�䶳��Ч��"));
			items.add(new GridItem(bmnengyuanliyonglv, "��Դ������"));
			items.add(new GridItem(bmyureliyonglv, "����������"));

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
						intent = new Intent(NengyuanXiaolv.this, ZongjiegouCharts.class);
						startActivity(intent);
						break;

					case 1:
						Log.d("Click", "1");
						intent = new Intent(NengyuanXiaolv.this, FadianjiActivity.class);
						startActivity(intent);
						break;
					case 2:
						Log.d("Click", "2");
						intent = new Intent(NengyuanXiaolv.this, ZhilengActivity.class);
						startActivity(intent);
					case 3:
						Log.d("Click", "3");
						intent = new Intent(NengyuanXiaolv.this, GuoluActivity.class);
						startActivity(intent);
					default:
						break;
					}
				}
			});
		}
	}
}
