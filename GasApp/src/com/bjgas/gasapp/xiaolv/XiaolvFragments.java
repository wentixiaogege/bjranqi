package com.bjgas.gasapp.xiaolv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bjgas.bean.XiaolvBean;
import com.bjgas.common.BaseFragment;

public abstract class XiaolvFragments<T extends XiaolvBean> extends BaseFragment<XiaolvBean> {
	/**
	 * �������Jsonת����AllInputBean����
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
				// ���������������iת�������ڡ�
				JSONObject jo = jArray.getJSONObject(i);
				JSONArray values = jo.getJSONArray("data");

				// ����values�ĳ��ȣ���ʼ��jsonResults������ʼ��ʱ�䡣
				// ����ǵ�һ��ѭ��
				if (0 == i)
					for (int k = 0; k < values.length(); k++) {
						XiaolvBean bean = new XiaolvBean();
						bean.setTime("ǰ" + k + "��");
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
}
