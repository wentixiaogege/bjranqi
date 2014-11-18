package com.bjgas.util;

import com.bjgas.bean.ScreenInfo;

import android.app.Activity;
import android.util.DisplayMetrics;

public class LocalUtils {

	/**
	 * �����Ļ�Ŀ�Ⱥ͸߶�
	 * 
	 * @param activity
	 *            ���ڵ�Activity
	 * @return a[0]:��� a[1]:�߶�
	 */
	public static void getScreenWidthAndHeight(Activity activity, ScreenInfo si) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		si.setWidth(mDisplayMetrics.widthPixels);
		si.setHeight(mDisplayMetrics.heightPixels);
	}
}
