package com.bjgas.gasapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.bjgas.bean.ScreenInfo;
import com.bjgas.common.BaseActivity;
import com.bjgas.test.PopupWindowTest;

public class MainActivity extends BaseActivity {

	ImageView imgFenxi;
	ImageView imgJiegou;
	ImageView imgXiaolv;
	ImageListener listener = new ImageListener();
	private LinearLayout llyImage;
	public static final String IMAGE_CLICK = "ImageClick";

	// 屏幕信息取得
	ScreenInfo si = new ScreenInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获得屏幕信息
		com.bjgas.util.LocalUtils.getScreenWidthAndHeight(this, si);
		llyImage = (LinearLayout) findViewById(R.id.llyImage);

		// 取出三个图片
		imgFenxi = (ImageView) findViewById(R.id.imgFenxi);
		imgJiegou = (ImageView) findViewById(R.id.imgJiegou);
		imgXiaolv = (ImageView) findViewById(R.id.imgXiaolv);

		// 设置宽度和高度。
		// setWidthAndHeight();

		// 为了取得llyImage的高度，可真不容易。wrap content取得高度简直就是噩梦啊！！妹的！！！
		final ViewTreeObserver observer = llyImage.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				int llyHeight = llyImage.getHeight();
				RelativeLayout.LayoutParams llyParams = (LayoutParams) llyImage.getLayoutParams();
				int topMargin = (si.getHeight() - llyHeight) / 2;

				Log.d("Margin", String.format("left:%d,top:%d,right:%d,bottom:%d", llyParams.leftMargin, topMargin,
						llyParams.rightMargin, llyParams.bottomMargin));

				// 设置距离头部的距离为高度差的一半，保证图片居中。
				llyParams.setMargins(llyParams.leftMargin, topMargin, llyParams.rightMargin, llyParams.bottomMargin);
				llyImage.setLayoutParams(llyParams);
				llyImage.invalidate();

				// 取消事件注册
				ViewTreeObserver obs = llyImage.getViewTreeObserver();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					obs.removeOnGlobalLayoutListener(this);
				} else {
					obs.removeGlobalOnLayoutListener(this);
				}
			}
		});

		// 绑定事件
		imgFenxi.setOnClickListener(listener);
		imgJiegou.setOnClickListener(listener);
		imgXiaolv.setOnClickListener(listener);
	}

	class ImageListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.imgFenxi:
				Log.d(IMAGE_CLICK, "imgFenxi");
				intent = new Intent(MainActivity.this, PopupWindowTest.class);
				startActivity(intent);
				break;
			case R.id.imgJiegou:
				Log.d(IMAGE_CLICK, "imgJiegou");
				intent = new Intent(MainActivity.this, NengyuanJiegouActivity.class);
				startActivity(intent);
				break;
			case R.id.imgXiaolv:
				Log.d(IMAGE_CLICK, "imgXiaolv");
				intent = new Intent(MainActivity.this, NengyuanXiaolv.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

}
