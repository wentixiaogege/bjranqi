package com.bjgas.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

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

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strResult;
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
