package com.bjgas.gasapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bjgas.test.LineChartActivity;

public class MainActivity extends Activity {

	Button btnLineChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnLineChart = (Button) findViewById(R.id.btnLineChart);
		addEvent();
	}

	private void addEvent() {

		btnLineChart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, LineChartActivity.class);
				startActivity(i);
			}
		});
	}
}
