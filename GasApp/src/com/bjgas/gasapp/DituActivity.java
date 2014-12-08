package com.bjgas.gasapp;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bjgas.bean.ScreenInfo;
import com.bjgas.common.BaseActivity;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.config.ConfigActivity;
import com.bjgas.util.InfoUtils;
import com.bjgas.util.NetUtils;
import com.bjgas.util.T;

public class DituActivity extends BaseActivity {

	private static final int CONFIG_REQUEST_CODE = 0;
	private RelativeLayout rtlDitu;
	ScreenInfo si = new ScreenInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ditu);
		rtlDitu = (RelativeLayout) findViewById(R.id.rtlDitu);

		// 读取文件，配置网络
		NetUtils.configWebsite(this);

		rtlDitu.setBackgroundResource(R.drawable.ditu);
		com.bjgas.util.LocalUtils.getScreenWidthAndHeight(this, si);

		// 清河医院
		addNewView(R.drawable.tb_qingheyiyuan, InfoUtils.X_QINGHE, InfoUtils.Y_QINGHE, new Qinghe());
		// 金雁饭店
		addNewView(R.drawable.tb_jinyan, InfoUtils.JINGYANFANDIAN_X, InfoUtils.JINGYANFANDIAN_Y, null);
		// 北七家园
		addNewView(R.drawable.tb_beiqijiayuan, InfoUtils.BEIQIJIAYUAN_X, InfoUtils.BEIQIJIAYUAN_Y, null);
		// 中石油创新基地
		addNewView(R.drawable.tb_zhongshiyou, InfoUtils.ZHONGSHIYOU_X, InfoUtils.ZHONGSHIYOU_Y, null);
		// 中关村一号
		addNewView(R.drawable.tb_zhongguancunyihao, InfoUtils.ZHONGGUANCUNYIHAO_X, InfoUtils.ZHONGGUANCUNYIHAO_Y, null);
		// 中关村软件园
		addNewView(R.drawable.tb_zhongguancunruanjianyuan, InfoUtils.ZHONGGUANCUNRUANJIANYUAN_X,
				InfoUtils.ZHONGGUANCUNRUANJIANYUAN_Y, null);
		// 海淀医院
		addNewView(R.drawable.tb_haidianyiyuan, InfoUtils.HAIDIANYIYUAN_X, InfoUtils.HAIDIANYIYUAN_Y, null);
		// 焦化厂
		addNewView(R.drawable.tb_jiaohuachang, InfoUtils.JIAOHUACHANG_X, InfoUtils.JIAOHUACHANG_Y, null);
		// 通州中医院
		addNewView(R.drawable.tb_tongzhouzhongyiyuan, InfoUtils.TONGZHOUZHONGYIYUAN_X, InfoUtils.TONGZHOUZHONGYIYUAN_Y,
				null);
		// 国润新通酒店
		addNewView(R.drawable.tb_guorunxintong, InfoUtils.GUORUNXINTONG_X, InfoUtils.GUORUNXINTONG_Y, null);

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.config:
			intent = new Intent(this, ConfigActivity.class);
			// startActivity(intent);
			startActivityForResult(intent, CONFIG_REQUEST_CODE);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CONFIG_REQUEST_CODE && resultCode == ConfigActivity.CONFIG_SUCCESS) {
			NetUtils.configWebsite(this);
			T.showLong(this, "新的网站设置完成");
		} else if (requestCode == CONFIG_REQUEST_CODE && resultCode == ConfigActivity.CONFIG_CANCEL) {
			T.showLong(this, "新的网站设置被取消");
		}
	}

	/**
	 * 添加一张新的图片
	 * 
	 * @param resource
	 * @param x
	 *            在1280*800上的横坐标
	 * @param y
	 *            在1280*800上的纵坐标
	 */
	private void addNewView(int resource, int x, int y, View.OnClickListener mClicklistener) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(resource);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		// lp.addRule(RelativeLayout.BELOW, R.id.ButtonRecalculate);
		// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// 获得屏幕信息
		lp.setMargins(getX(si, x), getY(si, y), 0, 0);
		rtlDitu.addView(iv, lp);
		if (mClicklistener != null)
			iv.setOnClickListener(mClicklistener);
	}

	private int getX(ScreenInfo si, int x) {
		double d = (double) si.getWidth() / (double) InfoUtils.STANDARD_WIDTH * (double) x;
		return (int) d;
	}

	private int getY(ScreenInfo si2, int y) {
		double d = (double) si.getHeight() / (double) InfoUtils.STANDARD_HEIGHT * (double) y;
		return (int) d;
	}

	class Qinghe implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(DituActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}

	// class Qingheyiyuan implements View.OnTouchListener {
	//
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// ImageView iView = (ImageView) v;
	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// iView.setImageResource(R.drawable.tb_qingheyiyuan_down);
	// } else if (event.getAction() == MotionEvent.ACTION_UP) {
	// iView.setImageResource(R.drawable.tb_qingheyiyuan);
	// Intent intent = new Intent(DituActivity.this, MainActivity.class);
	// startActivity(intent);
	// }
	// return false;
	// }
	//
	// }
}
