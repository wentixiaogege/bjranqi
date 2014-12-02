package com.bjgas.util;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.bjgas.bean.ScreenInfo;

public class LocalUtils {

	/**
	 * 获得屏幕的宽度和高度
	 * 
	 * @param activity
	 *            所在的Activity
	 * @return a[0]:宽度 a[1]:高度
	 */
	public static void getScreenWidthAndHeight(Activity activity, ScreenInfo si) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		si.setWidth(mDisplayMetrics.widthPixels);
		si.setHeight(mDisplayMetrics.heightPixels);
	}

	public static String convertDateindexToDate(int index, int total) {
		// TODO
		// LocalDate today = LocalDate.now();
		// LocalDate yesterday = today.minus(Period.days(1));
		// SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		// return df.format(today);
		return index + "";
	}
}
