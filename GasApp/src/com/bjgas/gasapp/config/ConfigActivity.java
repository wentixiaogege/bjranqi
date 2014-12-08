package com.bjgas.gasapp.config;

import org.apache.commons.lang3.StringUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjgas.common.BaseActivity;
import com.bjgas.gasapp.R;
import com.bjgas.util.NetUtils;
import com.bjgas.util.T;

public class ConfigActivity extends BaseActivity {

	public static final int CONFIG_SUCCESS = 0;
	public static final int CONFIG_CANCEL = 9;
	EditText etIp;
	EditText etPort;

	Button btnConfirm;
	Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);

		etIp = (EditText) findViewById(R.id.edtIp);
		etPort = (EditText) findViewById(R.id.edtPort);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		String[] configStrings = NetUtils.getWebConfigInfos(this);
		etIp.setText(configStrings[0]);
		etPort.setText(configStrings[1]);

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(CONFIG_CANCEL);
				ConfigActivity.this.finish();
			}
		});

		btnConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String config_xml_name = getResources().getString(R.string.config_xml_name);
				String interface_website = getResources().getString(R.string.interface_website);
				String interface_port = getResources().getString(R.string.interface_port);

				String webSite = etIp.getText().toString();
				String port= etPort.getText().toString();
				if (StringUtils.isEmpty(webSite)||StringUtils.isEmpty(port)) {
					T.showLong(ConfigActivity.this, "网站或端口为空，请重新设置！");
					return;
				}
				
				try {
					// BaseFragment.REQUEST_WEBSITE = String.format(format,
					// args);
					SharedPreferences appPrefs = getSharedPreferences(config_xml_name, MODE_PRIVATE);
					SharedPreferences.Editor prefsEditor = appPrefs.edit();
					prefsEditor.clear();
					prefsEditor.putString(interface_website, webSite);
					prefsEditor.putString(interface_port, port);
					prefsEditor.commit();

					setResult(CONFIG_SUCCESS);
					ConfigActivity.this.finish();
				} catch (Exception e) {
					T.showLong(ConfigActivity.this, String.format("error! info is :", e.getMessage()));
				}
			}
		});

	}
}
