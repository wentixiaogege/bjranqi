package com.bjgas.gasapp;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.bjgas.bean.AllInputBean;
import com.bjgas.bean.AllOutPutBean;
import com.bjgas.bean.ScreenInfo;
import com.bjgas.common.BaseActivity;
import com.bjgas.common.BaseFragment;
import com.bjgas.util.InfoUtils;
import com.bjgas.util.NetUtils;
import com.bjgas.util.T;
import com.bjgas.util.TagUtil;

public class MainActivity extends BaseActivity {

	ImageView imgFenxi;
	ImageView imgJiegou;
	ImageView imgXiaolv;
	ImageListener listener = new ImageListener();
	private LinearLayout llyImage;
	private LinearLayout llyWeather;
	// llyWeather
	private RelativeLayout rtlParent;
	public static final String IMAGE_CLICK = "ImageClick";
	protected static final int GET_NENGYUAN_SUCCESSFUL = 1;
	protected static final int GET_XIAOLV_SUCCESSFUL = 2;
	protected static final int GET_FENXI_SUCCESSFUL = 3;
	protected static final int GET_WEATHER_SUCCESSFUL = 4;

	String nengyuanWeb;
	String xiaolvWeb1;
	String xiaolvWeb2;
	String fenxiWeb;
	String weatherWeb;
	Thread threadNengyuan;
	Thread threadXiaolv;
	Thread threadFenxi;

	String nengyuanRes;
	String xiaolvRes1;
	String xiaolvRes2;
	String fenxiRes;
	String weatherRes;

	TextView tvNengyuanxiaohao;
	TextView tvNengyuanchansheng;
	TextView tvZongxiaolv;
	TextView tvNengyuanliyonglv;
	TextView tvTouru;
	TextView tvChanchu;

	ImageView ivWeather;
	TextView tvWeather;
	TextView tvDay;
	TextView tvUpdateTime;
	TextView tvWeek;
	TextView tvAdress;
	String address;

	String WEATHER_TEMPLATE_URL = "http://www.weather.com.cn/data/cityinfo/%s.html";

	// 屏幕信息取得
	ScreenInfo si = new ScreenInfo();

	// 定义一个Handler，用于线程同步。
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_NENGYUAN_SUCCESSFUL:
				displayNengyuan();
				break;
			case GET_XIAOLV_SUCCESSFUL:
				displayXiaolv();
				break;
			case GET_FENXI_SUCCESSFUL:
				displayFenxi();
				break;
			case GET_WEATHER_SUCCESSFUL:
				displayWeather();
				break;
			default:
				break;
			}
		}



	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nengyuanWeb = String.format("%s?category=nengyuan&module=zongjiegou&type=Week", BaseFragment.REQUEST_WEBSITE);
		xiaolvWeb1 = String.format("%s?category=xiaolv&module=zongxiaolv&type=Week", BaseFragment.REQUEST_WEBSITE);
		xiaolvWeb2 = String.format("%s?category=xiaolv&module=yurexiaolv&type=Week", BaseFragment.REQUEST_WEBSITE);
		fenxiWeb = String.format("%s?category=jingying&module=zongxitong&type=Week", BaseFragment.REQUEST_WEBSITE);

		initWidgetWeather();
		// 获得屏幕信息
		com.bjgas.util.LocalUtils.getScreenWidthAndHeight(this, si);
		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);
		llyImage = (LinearLayout) findViewById(R.id.llyImage);
		llyWeather = (LinearLayout) findViewById(R.id.llyWeather);
		// 取出三个图片
		imgFenxi = (ImageView) findViewById(R.id.imgFenxi);
		imgJiegou = (ImageView) findViewById(R.id.imgJiegou);
		imgXiaolv = (ImageView) findViewById(R.id.imgXiaolv);

		tvNengyuanxiaohao = (TextView) findViewById(R.id.tvNengyuanxiaohao);
		tvNengyuanchansheng = (TextView) findViewById(R.id.tvNengyuanchansheng);
		tvZongxiaolv = (TextView) findViewById(R.id.tvZongxiaolv);
		tvNengyuanliyonglv = (TextView) findViewById(R.id.tvNengyuanliyonglv);
		tvTouru = (TextView) findViewById(R.id.tvTouru);
		tvChanchu = (TextView) findViewById(R.id.tvChanchu);
		tvWeather = (TextView) findViewById(R.id.tvWeather);
		tvDay = (TextView) findViewById(R.id.tvDay);
		tvUpdateTime = (TextView) findViewById(R.id.tvUpdateTime);
		tvWeek = (TextView) findViewById(R.id.tvWeek);
		tvAdress = (TextView) findViewById(R.id.tvAdress);
		ivWeather = (ImageView) findViewById(R.id.ivWeather);

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

		Intent intent = getIntent();
		address = intent.getExtras().getString(InfoUtils.ADDRESS);
		// String address = InfoUtils.QINGHEYIYUAN;
		switch (address) {
		case InfoUtils.GUORUNXINTONG:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_guorunxintong));
			break;
		case InfoUtils.ZHONGSHIYOU:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_zhongshiyou));
			break;
		case InfoUtils.JINYAN:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_jinyan));
			break;
		case InfoUtils.ZHONGGUANCUNRUANJIANYUAN:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_zhongguancunruanjianyuan));
			break;
		case InfoUtils.TONGZHOUZHONGYIYUAN:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_tongzhouzhongyiyuan));
			break;
		case InfoUtils.HAIDIANYIYUAN:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_haidianyiyuan));
			break;
		case InfoUtils.JIAOHUACHANG:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_jiaohuachang));
			break;
		case InfoUtils.BEIQI:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_beiqi));
			break;
		case InfoUtils.ZHONGGUANCUNYIHAO:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_zhonguancunyihao));
			break;
		case InfoUtils.QINGHEYIYUAN:
			rtlParent.setBackground(getResources().getDrawable(R.drawable.bg_qingheyiyuan));
			// 绑定事件
			imgFenxi.setOnClickListener(listener);
			imgJiegou.setOnClickListener(listener);
			imgXiaolv.setOnClickListener(listener);
			weatherWeb = String.format(WEATHER_TEMPLATE_URL, "101090914");
			getDataFromweb();
			break;

		default:
			break;
		}

		llyWeather.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getWeather();
			}
		});


		getWeather();
		displayInfos();
	}

	private void displayInfos() {
		// tvDay;
		tvDay.setText(com.bjgas.util.DateUtils.getNowString("MM / d"));
		// tvUpdateTim
		tvUpdateTime.setText("更新:" + com.bjgas.util.DateUtils.getNowString("MM月dd日 HH时"));
		// tvWeek;
		tvWeek.setText(com.bjgas.util.DateUtils.getWeek());
		// tvAdress;
		tvAdress.setText(address);
	}

	@SuppressLint("NewApi")
	private void displayWeather() {
		try {
			JSONObject jo = new JSONObject(weatherRes);
			JSONObject joWeatherInfo = jo.getJSONObject("weatherinfo");
			String info = joWeatherInfo.getString("weather");
			String temp = joWeatherInfo.getString("temp1");
			Integer id = mWidgetWeatherIcon.get(info);
			if (null == id)
				id = R.drawable.wth_sunny;
			ivWeather.setBackground(getResources().getDrawable(id));
			tvWeather.setText(info + " " + temp);
		} catch (JSONException e) {
			Log.d("Weather", e.getMessage());
		}
	}

	private HashMap<String, Integer> mWidgetWeatherIcon;

	private HashMap<String, Integer> initWidgetWeather() {
		if (mWidgetWeatherIcon != null && !mWidgetWeatherIcon.isEmpty())
			return mWidgetWeatherIcon;
		mWidgetWeatherIcon = new HashMap<String, Integer>();
		mWidgetWeatherIcon.put("暴雪", R.drawable.wth_heavy_snow);
		mWidgetWeatherIcon.put("暴雨", R.drawable.wth_heavy_rain);
		mWidgetWeatherIcon.put("大暴雨", R.drawable.wth_heavy_rain);
		mWidgetWeatherIcon.put("大雪", R.drawable.wth_heavy_snow);
		mWidgetWeatherIcon.put("大雨", R.drawable.wth_heavy_rain);

		mWidgetWeatherIcon.put("多云", R.drawable.wth_cloudy);
		mWidgetWeatherIcon.put("雷阵雨", R.drawable.wth_thundershowers_sunny);
		mWidgetWeatherIcon.put("雷阵雨冰雹", R.drawable.wth_thunderstorm);
		mWidgetWeatherIcon.put("晴", R.drawable.wth_sunny);
		mWidgetWeatherIcon.put("沙尘暴", R.drawable.wth_wind);

		mWidgetWeatherIcon.put("特大暴雨", R.drawable.wth_heavy_rain);
		mWidgetWeatherIcon.put("雾", R.drawable.wth_fog);
		mWidgetWeatherIcon.put("小雪", R.drawable.wth_snow);
		mWidgetWeatherIcon.put("小雨", R.drawable.wth_rain);
		mWidgetWeatherIcon.put("阴", R.drawable.wth_cloudy_day);

		mWidgetWeatherIcon.put("雨夹雪", R.drawable.wth_rain_snow);
		mWidgetWeatherIcon.put("阵雪", R.drawable.wth_snow);
		mWidgetWeatherIcon.put("阵雨", R.drawable.wth_rain);
		mWidgetWeatherIcon.put("中雪", R.drawable.wth_snow);
		mWidgetWeatherIcon.put("中雨", R.drawable.wth_rain);
		return mWidgetWeatherIcon;
	}

	private void displayFenxi() {
		try {
			JSONArray jArray = new JSONArray(fenxiRes);
			// jsonResults.clear();
			AllInputBean beanIn = new AllInputBean();
			AllOutPutBean beanOut = new AllOutPutBean();

			int index = 0;
			for (int i = 0; i < jArray.length(); i++) {
				// 利用这个函数，将i转化成日期。
				JSONObject jo = jArray.getJSONObject(i);
				String key = jo.getString("name");
				JSONArray values = jo.getJSONArray("data");

				if (key.equals(InfoUtils.JINGYING_TOURU_ELEC)) {
					beanIn.setElec((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.JINGYING_TOURU_GAS)) {
					beanIn.setAir((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.JINGYING_TOURU_WATER)) {
					beanIn.setWater((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.JINGYING_SHOURU_ELEC)) {
					beanOut.setElec((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.JINGYING_SHOURU_COLD)) {
					beanOut.setCold((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.JINGYING_SHOURU_HOT)) {
					beanOut.setHot((float) values.getDouble(index));
				}

				// if (key.equals(InfoUtils.INPUT_ELEC)) {
				// beanIn.setElec((float) values.getDouble(index));
				// } else if (key.equals(InfoUtils.INPUT_AIR)) {
				// beanIn.setAir((float) values.getDouble(index));
				// } else if (key.equals(InfoUtils.INPUT_WATER)) {
				// beanIn.setWater((float) values.getDouble(index));
				// } else if (key.equals(InfoUtils.OUTPUT_ELEC)) {
				// beanOut.setElec((float) values.getDouble(index));
				// } else if (key.equals(InfoUtils.OUTPUT_COLD)) {
				// beanOut.setCold((float) values.getDouble(index));
				// } else if (key.equals(InfoUtils.OUTPUT_HOT)) {
				// beanOut.setHot((float) values.getDouble(index));
				// }
			}

			tvTouru.setText(String.format("投入:水 %.2f, 电 %.2f, 气 %.2f", beanIn.getWater(), beanIn.getElec(),
					beanIn.getAir()));
			tvChanchu.setText(String.format("产出:电 %.2f, 冷 %.2f, 热 %.2f", beanOut.getElec(), beanOut.getCold(),
					beanOut.getHot()));

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	private void displayXiaolv() {
		double zongxiaolv;
		double nengyuanliyonglv;
		try {
			// 总效率
			JSONArray jaZongxiaolv = new JSONArray(xiaolvRes1);
			// 能源利用率
			JSONArray jaNengyuanliyongXiaolv = new JSONArray(xiaolvRes2);

			int index = 0;
			zongxiaolv = jaZongxiaolv.getJSONObject(0).getJSONArray("data").getDouble(index);
			nengyuanliyonglv = jaNengyuanliyongXiaolv.getJSONObject(0).getJSONArray("data").getDouble(index);

			tvZongxiaolv.setText(String.format("总效率： %.2f", zongxiaolv));
			tvNengyuanliyonglv.setText(String.format("能源利用效率： %.2f", nengyuanliyonglv));

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}
	}

	private void displayNengyuan() {
		try {
			JSONArray jArray = new JSONArray(nengyuanRes);
			// jsonResults.clear();
			AllInputBean beanIn = new AllInputBean();
			AllOutPutBean beanOut = new AllOutPutBean();

			int index = 0;
			for (int i = 0; i < jArray.length(); i++) {
				// 利用这个函数，将i转化成日期。
				JSONObject jo = jArray.getJSONObject(i);
				String key = jo.getString("name");
				JSONArray values = jo.getJSONArray("data");

				if (key.equals(InfoUtils.INPUT_ELEC)) {
					beanIn.setElec((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.INPUT_AIR)) {
					beanIn.setAir((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.INPUT_WATER)) {
					beanIn.setWater((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.OUTPUT_ELEC)) {
					beanOut.setElec((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.OUTPUT_COLD)) {
					beanOut.setCold((float) values.getDouble(index));
				} else if (key.equals(InfoUtils.OUTPUT_HOT)) {
					beanOut.setHot((float) values.getDouble(index));
				}
			}

			tvNengyuanxiaohao.setText(String.format("耗:水 %.2f, 电 %.2f, 气 %.2f", beanIn.getWater(), beanIn.getElec(),
					beanIn.getAir()));
			tvNengyuanchansheng.setText(String.format("产:电 %.2f, 冷 %.2f, 热 %.2f", beanOut.getElec(), beanOut.getCold(),
					beanOut.getHot()));

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}
	}

	private void getWeather() {
		if (StringUtils.isEmpty(weatherWeb))
			weatherWeb = String.format(WEATHER_TEMPLATE_URL, "101010100");

		new Thread() {
			@Override
			public void run() {
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("weather url:%s", weatherWeb));
				weatherRes = NetUtils.connServerForResult(MainActivity.this, weatherWeb);
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("weatherRes:%s", weatherRes));
				if (StringUtils.isNotEmpty(weatherRes))
					mHandler.sendEmptyMessage(GET_WEATHER_SUCCESSFUL);
				else {
					// T.showLong(MainActivity.this, "天气获取失败");
				}
			};
		}.start();

	}

	/**
	 * 开启一个新的线程，调用接口，取得数据
	 */
	public void getDataFromweb() {
		// 新开启一个线程，获得Json数据
		threadNengyuan = new Thread() {
			@Override
			public void run() {
				String mRequestUrl = nengyuanWeb;
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadNengyuan request url:%s", mRequestUrl));
				nengyuanRes = NetUtils.connServerForResult(MainActivity.this, mRequestUrl);
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadNengyuan get json info:%s", nengyuanRes));
				if (StringUtils.isNotEmpty(nengyuanRes))
					mHandler.sendEmptyMessage(GET_NENGYUAN_SUCCESSFUL);
				else {
					// T.showLong(MainActivity.this, "能源获取失败");
				}
			}

		};
		// 新开启一个线程，获得Json数据
		threadXiaolv = new Thread() {
			@Override
			public void run() {
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadXiaolv request url1:%s", xiaolvWeb1));
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadXiaolv request url2:%s", xiaolvWeb2));
				xiaolvRes1 = NetUtils.connServerForResult(MainActivity.this, xiaolvWeb1);
				xiaolvRes2 = NetUtils.connServerForResult(MainActivity.this, xiaolvWeb2);
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadXiaolv get json info1:%s", xiaolvRes1));
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadXiaolv get json info2:%s", xiaolvRes2));
				if (StringUtils.isNotEmpty(xiaolvRes1) && StringUtils.isNotEmpty(xiaolvRes2))
					mHandler.sendEmptyMessage(GET_XIAOLV_SUCCESSFUL);
				else {
					// T.showLong(MainActivity.this, "效率获取失败");
				}
			}

		};

		threadFenxi = new Thread() {
			@Override
			public void run() {
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadFenxi request url1:%s", fenxiWeb));
				fenxiRes = NetUtils.connServerForResult(MainActivity.this, fenxiWeb);
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadFenxi get json info:%s", fenxiRes));
				if (StringUtils.isNotEmpty(fenxiRes))
					mHandler.sendEmptyMessage(GET_FENXI_SUCCESSFUL);
				else {
					// T.showLong(MainActivity.this, "经营分析获取失败");
				}
			}

		};

		threadNengyuan.start();
		threadFenxi.start();
		threadXiaolv.start();
	}

	class ImageListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.imgFenxi:
				Log.d(IMAGE_CLICK, "imgFenxi");
				intent = new Intent(MainActivity.this, JingyingfenxiActivity.class);
				startActivity(intent);
				break;
			case R.id.imgJiegou:
				Log.d(IMAGE_CLICK, "imgJiegou");
				intent = new Intent(MainActivity.this, NengyuanJiegouActivity.class);
				startActivity(intent);
				break;
			case R.id.imgXiaolv:
				Log.d(IMAGE_CLICK, "imgXiaolv");
				intent = new Intent(MainActivity.this, XitongxiaolvActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

}
