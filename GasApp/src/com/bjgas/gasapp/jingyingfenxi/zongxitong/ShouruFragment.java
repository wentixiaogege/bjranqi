package com.bjgas.gasapp.jingyingfenxi.zongxitong;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjgas.bean.AllOutPutBean;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.jingyingfenxi.JingyingFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public abstract class ShouruFragment extends JingyingFragments<AllOutPutBean> {

	private static final String TAG_ZONGJIEGOUCHART = "OutputsFragment";
	private static final String JINGYING_SHOURU_ELEC = InfoUtils.JINGYING_SHOURU_ELEC;
	private static final String JINGYING_SHOURU_HOT = InfoUtils.JINGYING_SHOURU_HOT;
	private static final String JINGYING_SHOURU_COLD = InfoUtils.JINGYING_SHOURU_COLD;

	// InfoUtils.OUTPUT_ELEC

	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("总产出");

	};

	/**
	 * 子线程取得数据后，显示图表
	 */
	@Override
	protected void displayChart() {
		try {
			Log.d(TAG_ZONGJIEGOUCHART, String.format("get json info:%s", mJsonInfo));
			// 设置横坐标轴
			ArrayList<String> xTimes = new ArrayList<String>();
			ArrayList<Entry> yElecs = new ArrayList<Entry>();
			ArrayList<Entry> yHots = new ArrayList<Entry>();
			ArrayList<Entry> yColds = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0) {
				return;
			}
			// 设置x坐标轴和y的值
			int i = 0;
			for (AllOutPutBean bean : jsonResults) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yHots.add(new Entry(bean.getHot(), i));
				yColds.add(new Entry(bean.getCold(), i));
				++i;
			}

			LineDataSet setElecs = getDefaultDataset(yElecs, JINGYING_SHOURU_ELEC, 1);
			LineDataSet setHots = getDefaultDataset(yHots, JINGYING_SHOURU_HOT, 3);
			LineDataSet setColds = getDefaultDataset(yColds, JINGYING_SHOURU_COLD, 2);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setColds);
			dataSets.add(setHots);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			mChart.setData(data);
			mChart.invalidate();
			// 取得output信息

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
				String key = jo.getString("name");
				JSONArray values = jo.getJSONArray("data");

				// 根据values的长度，初始化jsonResults，并初始化时间。
				// 如果是第一次循环
				if (0 == i)
					for (int k = 0; k < values.length(); k++) {
						AllOutPutBean bean = new AllOutPutBean();
						bean.setTime(getProperTime(k, values.length()));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					AllOutPutBean bean = jsonResults.get(j);
					// 如果是总耗电
					if (key.equals(JINGYING_SHOURU_ELEC)) {
						bean.setElec((float) values.getDouble(j));
					} else if (key.equals(JINGYING_SHOURU_COLD)) {
						bean.setCold((float) values.getDouble(j));
					} else if (key.equals(JINGYING_SHOURU_HOT)) {
						bean.setHot((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	@Override
	protected void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		String label = dataSetIndex == 0 ? JINGYING_SHOURU_ELEC : (dataSetIndex == 1 ? JINGYING_SHOURU_COLD
				: JINGYING_SHOURU_HOT);
		return label;
	}

	@Override
	protected String getModule() {
		return InfoUtils.ZONGXITONG;
	}

}
