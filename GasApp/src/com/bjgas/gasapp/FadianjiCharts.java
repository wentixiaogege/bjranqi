package com.bjgas.gasapp;

import android.os.Bundle;

import com.bjgas.common.BaseFragmentActivity;

public class FadianjiCharts extends BaseFragmentActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadianji_charts);

		// 初始化头部显示
		initHead();
	}


}
