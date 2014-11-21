package com.bjgas.gasapp;

import android.os.Bundle;
import android.view.View;

import com.bjgas.common.BaseFragmentActivity;
import com.bjgas.common.VerticalViewPager;

public class FadianjiCharts extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadianji_charts);

		// 初始化头部显示
		initHead();

		com.bjgas.common.VerticalViewPager pager = (VerticalViewPager) findViewById(R.id.pager);
		pager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismissPopviewWindow();
			}
		});
	}


}
