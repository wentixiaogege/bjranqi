package com.bjgas.gasapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bjgas.bean.ScreenInfo;
import com.bjgas.common.BaseActivity;
import com.bjgas.test.PopupWindowTest;
import com.bjgas.test.TestHeaderChartViewActivity;

public class MainActivity extends BaseActivity {

	ImageView imgGaikuang;
	ImageView imgJiegou;
	ImageView imgXiaolv;
	ImageListener listener = new ImageListener();
	public static final String IMAGE_CLICK = "ImageClick";

	// 屏幕信息取得
	ScreenInfo si = new ScreenInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 取出三个图片
		imgGaikuang = (ImageView) findViewById(R.id.imgGaikuang);
		imgJiegou = (ImageView) findViewById(R.id.imgJiegou);
		imgXiaolv = (ImageView) findViewById(R.id.imgXiaolv);

		// 设置宽度和高度。
		setWidthAndHeight();


		// 绑定事件
		imgGaikuang.setOnClickListener(listener);
		imgJiegou.setOnClickListener(listener);
		imgXiaolv.setOnClickListener(listener);
	}

	private void setWidthAndHeight() {
		// TODO Auto-generated method stub
		com.bjgas.util.LocalUtils.getScreenWidthAndHeight(this,si);
		int imgWidth = si.getWidth()/3;
		imgGaikuang.getLayoutParams().width = imgWidth;
		imgJiegou.getLayoutParams().width = imgWidth;
		imgXiaolv.getLayoutParams().width = imgWidth;
	}

	class ImageListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.imgGaikuang:
				Log.d(IMAGE_CLICK, "imgGaikuang");
				intent = new Intent(MainActivity.this, PopupWindowTest.class);
				startActivity(intent);
				break;
			case R.id.imgJiegou:
				Log.d(IMAGE_CLICK, "imgJiegou");
				intent = new Intent(MainActivity.this, NengyuanJiegou.class);
				startActivity(intent);
				break;
			case R.id.imgXiaolv:
				Log.d(IMAGE_CLICK, "imgXiaolv");
				intent = new Intent(MainActivity.this, TestHeaderChartViewActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

}
