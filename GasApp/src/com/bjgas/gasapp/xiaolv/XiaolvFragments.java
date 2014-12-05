package com.bjgas.gasapp.xiaolv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bjgas.bean.XiaolvBean;
import com.bjgas.common.BaseFragment;

public abstract class XiaolvFragments<T extends XiaolvBean> extends BaseFragment<XiaolvBean> {
	/**
	 * 将传入的Json转化成AllInputBean数组
	 * 
	 * @param arrInputs
	 * @param json
	 */
	@Override
	public void convertJsonToBean(String json) {
		try {
			JSONArray jArray = new JSONArray(json);
			jsonResults.clear();

			for (int i = 0; i < jArray.length(); i++) {
				// 利用这个函数，将i转化成日期。
				JSONObject jo = jArray.getJSONObject(i);
				JSONArray values = jo.getJSONArray("data");

				// 根据values的长度，初始化jsonResults，并初始化时间。
				// 如果是第一次循环
				if (0 == i)
					for (int k = 0; k < values.length(); k++) {
						XiaolvBean bean = new XiaolvBean();
						bean.setTime(getProperTime(k));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					XiaolvBean bean = jsonResults.get(j);
					bean.setXiaolv((float) values.getDouble(j));
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	protected String getSearchRequestUrl(String startMonth, String endMonth) {
		String mRequestUrl = String.format(FORMAT_URL_WITHDATE, REQUEST_WEBSITE, XIAOLV_CATEGORY, getModule(),
				startMonth, endMonth);
		return mRequestUrl;
	}
}
