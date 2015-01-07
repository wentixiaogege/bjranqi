package com.bjgas.gasapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjgas.bean.AllInputBean;
import com.bjgas.bean.AllOutPutBean;
import com.bjgas.common.BaseActivity;
import com.bjgas.common.BaseFragment;
import com.bjgas.util.InfoUtils;
import com.bjgas.util.NetUtils;
import com.bjgas.util.TagUtil;

public class MainActivityNew extends BaseActivity {

	private RelativeLayout rtlFenxi;
	private RelativeLayout rtlJiegou;
	private RelativeLayout rtlXiaolv;
	private ImageListener listener = new ImageListener();
	// llyWeather
	private RelativeLayout rtlParent;
	private static final String IMAGE_CLICK = "ImageClick";
	protected static final int GET_NENGYUAN_SUCCESSFUL = 1;
	protected static final int GET_XIAOLV_SUCCESSFUL = 2;
	protected static final int GET_FENXI_SUCCESSFUL = 3;
	protected static final int GET_WEATHER_SUCCESSFUL = 4;
	protected static final int DISPLAY_CHANGE = 5;
	protected static final int DISPLAY_NENGYUAN = 11;
	protected static final int DISPLAY_XIAOLV = 12;
	protected static final int DISPLAY_FENXI = 13;

	private String nengyuanWeb;
	private String xiaolvWeb1;
	private String xiaolvWeb2;
	private String fenxiWeb;
	private String weatherWeb;
	private Thread threadNengyuan;
	private Thread threadXiaolv;
	private Thread threadFenxi;
	private String nengyuanRes;
	private String xiaolvRes1;
	private String xiaolvRes2;
	private String fenxiRes;
	private String weatherRes;
	private ImageView ivWeather;
	private TextView tvDay;
	private TextView tvUpdateTime;
	private TextView tvWeek;
	private TextView tvAdress;
	private String address;

	private boolean mStop = false;
	private TextView tvXiaolvDisplay;
	private TextView tvFenxiDisplay;
	private TextView tvNengyuanDisplay;

	private static final String WEATHER_TEMPLATE_URL = "http://www.weather.com.cn/data/cityinfo/%s.html";
	private static final String NO_DATA = "暂无数据";

	// String[] testinfos = { "消耗水：100吨", "消耗气：100kj", "消耗电：100°" };
	// String[] testinfos2 = { "效率1", "效率2", "效率3", "效率4", "效率5", "效率6" };
	// int testindex = 0;
	// int testindex2 = 0;
	// int testindex3 = 0;

	LinkedHashMap<String, String> NowNengyuan = new LinkedHashMap<>();
	private static int nengyuanIndex = 0;
	private static int xiaolvIndex = 0;
	private static int fenxiIndex = 0;
	LinkedHashMap<String, String> NowXiaolv = new LinkedHashMap<>();
	LinkedHashMap<String, String> NowFenxi = new LinkedHashMap<>();

	private DisplayThread mThNengyuan;
	private DisplayThread mThXiaolv;
	private DisplayThread mThFenxi;
	// 天气图片
	private HashMap<String, Integer> mWidgetWeatherIcon;

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
			case DISPLAY_NENGYUAN:
				if (NowNengyuan.size() > 0) {
					nengyuanIndex = (++nengyuanIndex) % NowNengyuan.size();
					String keyString = new ArrayList<String>(NowNengyuan.keySet()).get(nengyuanIndex);
					String value = new ArrayList<String>(NowNengyuan.values()).get(nengyuanIndex);
					tvNengyuanDisplay.setText(keyString + ":" + value);
				}
				break;
			case DISPLAY_XIAOLV:
				if (NowXiaolv.size() > 0) {
					xiaolvIndex = (++xiaolvIndex) % NowXiaolv.size();
					String keyString = new ArrayList<String>(NowXiaolv.keySet()).get(xiaolvIndex);
					String value = new ArrayList<String>(NowXiaolv.values()).get(xiaolvIndex);
					tvXiaolvDisplay.setText(keyString + ":" + value);
				}
				break;
			case DISPLAY_FENXI:
				if (NowFenxi.size() > 0) {
					fenxiIndex = (++fenxiIndex) % NowFenxi.size();
					String keyString = new ArrayList<String>(NowFenxi.keySet()).get(fenxiIndex);
					String value = new ArrayList<String>(NowFenxi.values()).get(fenxiIndex);
					tvFenxiDisplay.setText(keyString + ":" + value);
				}
				break;
			default:
				break;
			}
		}

	};

	// 自定义线程，通过Message传递消息给Handler，让Handler改变UI显示
	private class DisplayThread extends Thread {
		int id;

		public DisplayThread(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			while (!mStop) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Log.d("Thread", "running");
				Message msg = new Message();
				msg.what = this.id;
				// send message
				mHandler.sendMessage(msg);
			}
			if (mStop) {
				Log.d(InfoUtils.TAG_THREAD, "mStop interrupted");
				if (!this.isInterrupted())
					this.interrupt();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(InfoUtils.TAG_LIFECYCLE, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_main);

		tvNengyuanDisplay = (TextView) findViewById(R.id.tvNengyuanDisplay);
		tvXiaolvDisplay = (TextView) findViewById(R.id.tvXiaolvDisplay);
		tvFenxiDisplay = (TextView) findViewById(R.id.tvFenxiDisplay);
		tvDay = (TextView) findViewById(R.id.tvDay);
		tvUpdateTime = (TextView) findViewById(R.id.tvUpdateTime);
		tvWeek = (TextView) findViewById(R.id.tvWeek);
		tvAdress = (TextView) findViewById(R.id.tvAdress);
		ivWeather = (ImageView) findViewById(R.id.ivWeather);

		nengyuanWeb = String.format("%s?category=nengyuan&module=zongjiegou&type=Week", BaseFragment.REQUEST_WEBSITE);
		xiaolvWeb1 = String.format("%s?category=xiaolv&module=zongxiaolv&type=Week", BaseFragment.REQUEST_WEBSITE);
		xiaolvWeb2 = String.format("%s?category=xiaolv&module=yurexiaolv&type=Week", BaseFragment.REQUEST_WEBSITE);
		fenxiWeb = String.format("%s?category=jingying&module=zongxitong&type=Week", BaseFragment.REQUEST_WEBSITE);

		tvNengyuanDisplay.setText(NO_DATA);
		tvXiaolvDisplay.setText(NO_DATA);
		tvFenxiDisplay.setText(NO_DATA);

		NowNengyuan.clear();
		NowXiaolv.clear();
		NowFenxi.clear();
		initWidgetWeather();
		rtlParent = (RelativeLayout) findViewById(R.id.rtlParent);
		rtlFenxi = (RelativeLayout) findViewById(R.id.imgFenxi);
		rtlJiegou = (RelativeLayout) findViewById(R.id.imgJiegou);
		rtlXiaolv = (RelativeLayout) findViewById(R.id.imgXiaolv);
		//


		Intent intent = getIntent();
		if (intent.getExtras() != null)
			address = intent.getExtras().getString(InfoUtils.ADDRESS);
		else {
			address = InfoUtils.QINGHEYIYUAN;
		}
		switch (address) {
		case InfoUtils.GUORUNXINTONG:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_guorunxintong));
			break;
		case InfoUtils.ZHONGSHIYOU:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_zhongshiyou));
			break;
		case InfoUtils.JINYAN:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_jinyan));
			break;
		case InfoUtils.ZHONGGUANCUNRUANJIANYUAN:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_zhongguancunruanjianyuan));
			break;
		case InfoUtils.TONGZHOUZHONGYIYUAN:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tongzhouzhongyiyuan));
			break;
		case InfoUtils.HAIDIANYIYUAN:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_haidianyiyuan));
			break;
		case InfoUtils.JIAOHUACHANG:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_jiaohuachang));
			break;
		case InfoUtils.BEIQI:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_beiqi));
			break;
		case InfoUtils.ZHONGGUANCUNYIHAO:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_zhonguancunyihao));
			break;
		case InfoUtils.QINGHEYIYUAN:
			rtlParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_qingheyiyuan));
			// 绑定事件
			rtlFenxi.setOnClickListener(listener);
			rtlJiegou.setOnClickListener(listener);
			rtlXiaolv.setOnClickListener(listener);
			weatherWeb = String.format(WEATHER_TEMPLATE_URL, "101090914");
			// getDataFromweb();
			break;

		default:
			break;
		}

		getWeather();
		displayInfos();
	}

	private void displayInfos() {
		// tvDay;
		tvDay.setText(com.bjgas.util.DateUtils.getNowString("MM / d"));
		// tvUpdateTim
		tvUpdateTime.setText("更新:" + com.bjgas.util.DateUtils.getNowString("HH时"));
		// tvWeek;
		tvWeek.setText(com.bjgas.util.DateUtils.getWeek());
		// tvAdress;
		tvAdress.setText(address);
	}

	// @SuppressLint("NewApi")
	private void displayWeather() {
		try {
			JSONObject jo = new JSONObject(weatherRes);
			JSONObject joWeatherInfo = jo.getJSONObject("weatherinfo");
			String info = joWeatherInfo.getString("weather");
			// String temp = joWeatherInfo.getString("temp1");
			Integer id = mWidgetWeatherIcon.get(info);
			if (null == id)
				id = R.drawable.wth_sunny;
			if (Build.VERSION.SDK_INT >= 16) {
				ivWeather.setBackground(getResources().getDrawable(id));

			} else {

				ivWeather.setBackgroundDrawable(getResources().getDrawable(id));
			}
			// ivWeather.setBackground(getResources().getDrawable(id));
			// tvWeather.setText(info + " " + temp);
		} catch (JSONException e) {
			Log.d("Weather", e.getMessage());
		}
	}



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

			}

			NowFenxi.put(InfoUtils.JINGYING_TOURU_ELEC_SIMPLE, String.format("%.0f度", beanIn.getElec()));
			NowFenxi.put(InfoUtils.JINGYING_TOURU_GAS_SIMPLE, String.format("%.0fm³", beanIn.getAir()));
			NowFenxi.put(InfoUtils.JINGYING_TOURU_WATER_SIMPLE, String.format("%.0f吨", beanIn.getWater()));

			NowFenxi.put(InfoUtils.JINGYING_SHOURU_ELEC_SIMPLE, String.format("%.0f度", beanOut.getElec()));
			NowFenxi.put(InfoUtils.JINGYING_SHOURU_HOT_SIMPLE, String.format("%.0fj", beanOut.getHot()));
			NowFenxi.put(InfoUtils.JINGYING_SHOURU_COLD_SIMPLE, String.format("%.0fj", beanOut.getCold()));
			startThFenxi();
			// tvTouru.setText(String.format("%.0f, %.0f, %.0f",
			// beanIn.getWater(), beanIn.getElec(), beanIn.getAir()));
			// tvChanchu
			// .setText(String.format("%.0f, %.0f, %.0f", beanOut.getElec(),
			// beanOut.getCold(), beanOut.getHot()));

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

			NowXiaolv.put(InfoUtils.TIT_ZONGXITONGXIAOLV_SIMPLE, String.format("%.0f", zongxiaolv));
			NowXiaolv.put(InfoUtils.TIT_YICINENGYUANLIYONGXIAOLV_SIMPLE, String.format("%.0f", nengyuanliyonglv));

			startThXiaolv();

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}
	}

	/**
	 * 动态显示能源
	 */
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

			NowNengyuan.put(InfoUtils.INPUT_ELEC_SIMPLE, String.format("%.0f度", beanIn.getElec()));
			NowNengyuan.put(InfoUtils.INPUT_AIR_SIMPLE, String.format("%.0f立方米", beanIn.getAir()));
			NowNengyuan.put(InfoUtils.INPUT_WATER_SIMPLE, String.format("%.0f吨", beanIn.getWater()));

			NowNengyuan.put(InfoUtils.OUTPUT_ELEC_SIMPLE, String.format("%.0f度", beanOut.getElec()));
			NowNengyuan.put(InfoUtils.OUTPUT_HOT_SIMPLE, String.format("%.0fj", beanOut.getHot()));
			NowNengyuan.put(InfoUtils.OUTPUT_COLD_SIMPLE, String.format("%.0fj", beanOut.getCold()));

			startThNengyuan();
		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}
	}

	private void startThNengyuan() {
		mThNengyuan = new DisplayThread(DISPLAY_NENGYUAN);
		mThNengyuan.start();
	}

	private void startThXiaolv() {
		mThXiaolv = new DisplayThread(DISPLAY_XIAOLV);
		mThXiaolv.start();
	}

	private void startThFenxi() {
		mThFenxi = new DisplayThread(DISPLAY_FENXI);
		mThFenxi.start();
	}

	private void getWeather() {
		if (StringUtils.isEmpty(weatherWeb))
			weatherWeb = String.format(WEATHER_TEMPLATE_URL, "101010100");

		new Thread() {
			@Override
			public void run() {
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("weather url:%s", weatherWeb));
				weatherRes = NetUtils.connServerForResult(MainActivityNew.this, weatherWeb);
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
		Log.d(this.getClass().getSimpleName(), "getDataFromweb starting...");
		// 新开启一个线程，获得Json数据
		threadNengyuan = new Thread() {
			@Override
			public void run() {
				String mRequestUrl = nengyuanWeb;
				Log.i(TagUtil.TAG_BJGAS_SYSTEM, String.format("threadNengyuan request url:%s", mRequestUrl));
				nengyuanRes = NetUtils.connServerForResult(MainActivityNew.this, mRequestUrl);
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
				xiaolvRes1 = NetUtils.connServerForResult(MainActivityNew.this, xiaolvWeb1);
				xiaolvRes2 = NetUtils.connServerForResult(MainActivityNew.this, xiaolvWeb2);
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
				fenxiRes = NetUtils.connServerForResult(MainActivityNew.this, fenxiWeb);
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

	/**
	 * 检查线程状态，开启线程
	 */
	private void startThread() {
		// 由于实时数据的接口有误，暂时停止这个功能。等到实时数据接口好了之后再说。
		if (mThNengyuan != null && mThFenxi != null && mThXiaolv != null) {
			// Log.d(InfoUtils.TAG_THREAD, mThNengyuan.getState().toString());
			// Log.d(InfoUtils.TAG_THREAD, mThFenxi.getState().toString());
			// Log.d(InfoUtils.TAG_THREAD, mThXiaolv.getState().toString());
			// // Thread.State
			// if (mThXiaolv.getState() == Thread.State.TERMINATED &&
			// mThFenxi.getState() == Thread.State.TERMINATED
			// && mThNengyuan.getState() == Thread.State.TERMINATED) {
			// mStop = false;
			// startThFenxi();
			// startThNengyuan();
			// startThXiaolv();
			// }

		} else {
			Log.d(InfoUtils.TAG_THREAD, "thread is null");
		}
	}

	@Override
	protected void onPause() {
		Log.d(InfoUtils.TAG_LIFECYCLE, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(InfoUtils.TAG_LIFECYCLE, "onStop");
		mStop = true;
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(InfoUtils.TAG_LIFECYCLE, "destroy");
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		Log.d(InfoUtils.TAG_LIFECYCLE, "onStart");
		startThread();
		super.onStart();
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Log.d(InfoUtils.TAG_LIFECYCLE, "onDetachedFromWindow");
		super.onDetachedFromWindow();
	}

	@Override
	protected void onResume() {
		Log.d(InfoUtils.TAG_LIFECYCLE, "onResume");
		super.onResume();
	}

	class ImageListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.imgFenxi:
				Log.d(IMAGE_CLICK, "imgFenxi");
				intent = new Intent(MainActivityNew.this, JingyingfenxiActivity.class);
				startActivity(intent);
				break;
			case R.id.imgJiegou:
				Log.d(IMAGE_CLICK, "imgJiegou");
				intent = new Intent(MainActivityNew.this, NengyuanJiegouActivity.class);
				startActivity(intent);
				break;
			case R.id.imgXiaolv:
				Log.d(IMAGE_CLICK, "imgXiaolv");
				intent = new Intent(MainActivityNew.this, XitongxiaolvActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

}
