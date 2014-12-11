package com.bjgas.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.R.string;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

public class NetUtils {
	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	/**
	 * 获得http请求结果数据
	 * 
	 * @param context
	 * @param url
	 * @return http请求的结果
	 */
	public static String connServerForResult(Context context, String url) {
		HttpGet httpRequest = new HttpGet(url);
		String strResult = "";
		if (getNetworkState(context) != NETWORN_NONE) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					strResult = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				} else {
					// T.showLong(context, "网络请求返回值错误，错误代码为" +
					// httpResponse.getStatusLine().getStatusCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
				// T.showLong(context, "网络不给力啊，请配置网络！");
			}
		} else {
			// T.showLong(context, "网络不给力啊，请配置网络！");
			// Toast.makeText(context, "网络不给力啊，请配置网络！", Toast.LENGTH_LONG);
			Log.e("NetWork Error", "net work error!");
		}
		return strResult;
	}

	/**
	 * 读取文件，配置网络
	 */
	public static void configWebsite(Context context) {
		String[] configStrings = getWebConfigInfos(context);
		String website = configStrings[0];
		String port = configStrings[1];

		if (StringUtils.isEmpty(website) || StringUtils.isEmpty(port))
			T.showLong(context, "网络配置或许有问题，请重新配置！");
		else {
			// public static String REQUEST_WEBSITE =
			// "http://192.168.2.101:8090/Interface/AppAdapter.aspx";
			BaseFragment.REQUEST_WEBSITE = String.format("http://%s:%s/Interface/AppAdapter.aspx", website, port);
		}
	}

	public static String[] getWebConfigInfos(Context context) {
		String[] strs = { StringUtils.EMPTY, StringUtils.EMPTY };
		String config_xml_name = context.getResources().getString(R.string.config_xml_name);
		String interface_website = context.getResources().getString(R.string.interface_website);
		String interface_port = context.getResources().getString(R.string.interface_port);

		SharedPreferences appPrefs = context.getSharedPreferences(config_xml_name, context.MODE_PRIVATE);
		String website = appPrefs.getString(interface_website, StringUtils.EMPTY);
		String port = appPrefs.getString(interface_port, "80");
		strs[0] = website;
		strs[1] = port;
		return strs;
	}

	/**
	 * 获得网络的状态
	 * 
	 * @param context
	 * @return NETWORN_NONE NETWORN_WIFI NETWORN_MOBILE
	 */
	public static int getNetworkState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Wifi
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_WIFI;
		}

		// 3G
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_MOBILE;
		}
		return NETWORN_NONE;
	}
}
