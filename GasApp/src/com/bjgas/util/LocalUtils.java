package com.bjgas.util;

import com.bjgas.bean.ScreenInfo;

import android.app.Activity;
import android.util.DisplayMetrics;

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
}
