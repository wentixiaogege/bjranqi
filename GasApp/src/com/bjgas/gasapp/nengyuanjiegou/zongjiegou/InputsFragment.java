package com.bjgas.gasapp.nengyuanjiegou.zongjiegou;

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

import com.bjgas.bean.AllInputBean;
import com.bjgas.common.BaseFragment;
import com.bjgas.gasapp.R;
import com.bjgas.gasapp.nengyuanjiegou.NengyuanFragments;
import com.bjgas.util.InfoUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

@SuppressLint("NewApi")
public abstract class InputsFragment extends NengyuanFragments<AllInputBean> {

	// private SearchMethod searchMethod;

	public InputsFragment() {
	}

	// public void setSearchMethod(SearchMethod sm) {
	// searchMethod = sm;
	// }

	// public InputsFragment(SearchMethod sm) {
	// this.searchMethod = sm;
	// }
	//
	// public static InputsFragment Instance(SearchMethod sm) {
	// return new InputsFragment(sm);
	// }

	private static final String TAG_ZONGJIEGOUCHART = "InputsFragment";

	private double minValue = 0.0;
	private double maxValue = 100.0;

	/**
	 * 初始化视图
	 */
	@Override
	public void initChart() {
		super.initChart();
		mChart.setDescription("输入能源");
	};

	// /**
	// * 创建视图时的回调
	// */
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View v = inflater.inflate(R.layout.inputs_fragment, container, false);
	//
	// mChart = (LineChart) v.findViewById(R.id.chart1);
	//
	// getDataFromweb();
	//
	// // 初始化chart
	// initChart();
	// return v;
	// }

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
			ArrayList<Entry> yAirs = new ArrayList<Entry>();
			ArrayList<Entry> yWaters = new ArrayList<Entry>();

			// 取得排序后的InputBean
			convertJsonToBean(mJsonInfo);
			if (jsonResults.size() == 0)
				return;
			// 设置x坐标轴和y的值
			int i = 0;
			for (AllInputBean bean : jsonResults) {
				xTimes.add(bean.getTime() + "");
				yElecs.add(new Entry(bean.getElec(), i));
				yAirs.add(new Entry(bean.getAir(), i));
				yWaters.add(new Entry(bean.getWater(), i));
				++i;
			}

			LineDataSet setElecs = getDefaultDataset(yElecs, InfoUtils.INPUT_ELEC, 1);

			LineDataSet setAirs = getDefaultDataset(yAirs, InfoUtils.INPUT_AIR, 2);
			LineDataSet setWaters = getDefaultDataset(yWaters, InfoUtils.INPUT_WATER, 3);

			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			dataSets.add(setElecs); // add the datasets
			dataSets.add(setAirs);
			dataSets.add(setWaters);

			// create a data object with the datasets
			LineData data = new LineData(xTimes, dataSets);

			// 暂时不增加上下区域线
			// LimitLine ll1 = new LimitLine(100f);
			// ll1.setLineWidth(1f);
			// ll1.enableDashedLine(10f, 10f, 0f);
			// ll1.setDrawValue(false);
			// ll1.setLabelPosition(LimitLabelPosition.RIGHT);
			//
			// LimitLine ll2 = new LimitLine(0f);
			// ll2.setLineWidth(1f);
			// ll2.enableDashedLine(10f, 10f, 0f);
			// ll2.setDrawValue(false);
			// ll2.setLabelPosition(LimitLabelPosition.RIGHT);
			//
			// data.addLimitLine(ll1);
			// data.addLimitLine(ll2);

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
						AllInputBean bean = new AllInputBean();
						bean.setTime(getProperTime(k));
						jsonResults.add(bean);
					}

				for (int j = 0; j < values.length(); j++) {
					AllInputBean bean = jsonResults.get(j);
					// 如果是总耗电
					if (key.equals(InfoUtils.INPUT_ELEC)) {
						bean.setElec((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.INPUT_AIR)) {
						bean.setAir((float) values.getDouble(j));
					} else if (key.equals(InfoUtils.INPUT_WATER)) {
						bean.setWater((float) values.getDouble(j));
					}
				}
			}

		} catch (JSONException e) {
			Log.d("Error", e.getMessage());
		}

	}

	/**
	 * 如果取得的数据比理论上最大值大，或者比理论上最小值小，则变为最大值或最小值。
	 * 
	 * @param jo
	 * @param string
	 * @return
	 * @throws JSONException
	 */
	private float getProperData(JSONObject jo, String string) throws JSONException {
		double value = jo.getDouble(string);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return (float) value;
	}

	@Override
	protected void displayErr() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarkViewDesc(int dataSetIndex) {
		String label = dataSetIndex == 0 ? InfoUtils.INPUT_ELEC : (dataSetIndex == 1 ? InfoUtils.INPUT_AIR
				: InfoUtils.INPUT_WATER);
		return label;
	}

	// /**
	// * 请求页面的url
	// */
	// @Override
	// public String getRequestUrl() {
	// // String searchFilter = SearchMethod.Now.toString();
	// // if (searchMethod.equals(SearchMethod.Week))
	// // searchFilter = SearchMethod.Week.toString();
	// // else if (searchMethod.equals(SearchMethod.Month)) {
	// // searchFilter = SearchMethod.Month.toString();
	// // }
	// // String mRequestUrl = String.format(FORMAT_URL, REQUEST_WEBSITE,
	// // NENTYUAN_CATEGORY, "zongjiegou", searchFilter);
	// // return mRequestUrl;
	// }

	@Override
	protected String getModule() {
		return "zongjiegou";
	}

}

