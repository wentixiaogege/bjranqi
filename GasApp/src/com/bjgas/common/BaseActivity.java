package com.bjgas.common;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {

	protected String BASE_URL = "http://gqqapp.sinaapp.com/ranqi.php";
	private String module;

	public String getBASE_URL() {
		return BASE_URL;
	}

	public void setBASE_URL(String bASE_URL) {
		BASE_URL = bASE_URL;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	private String type;

	public BaseActivity() {
		this(StringUtils.EMPTY, StringUtils.EMPTY);
	}

	public BaseActivity(String module, String type) {
		this.module = module;
		this.setType(type);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
