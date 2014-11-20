package com.bjgas.common;

import java.util.ArrayList;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.bjgas.adapter.PopupListArrayAdapter;
import com.bjgas.bean.PopupItem;
import com.bjgas.bean.ScreenInfo;
import com.bjgas.gasapp.R;
import com.bjgas.util.LocalUtils;

public class BaseFragmentActivity extends FragmentActivity {

	LinearLayout linearLayoutTopic;
	View popupView;
	ListView popupViewList;
	protected PopupWindow popupwindow;
	private ImageView ivTopic;

	protected String[] mMonths = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt",
			"Nov", "Dec" };

	protected String[] mParties = new String[] { "Party A", "Party B", "Party C", "Party D", "Party E", "Party F",
			"Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O",
			"Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
			"Party Y", "Party Z" };

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
	}

	/**
	 * 初始化头部显示，由于所有的chart都具有相同的头部，所以该方法写入基类中。
	 */
	protected void initHead() {

		linearLayoutTopic = (LinearLayout) findViewById(R.id.linearLayoutTopic);
		ivTopic = (ImageView) findViewById(R.id.imageViewTopic);
		// // 获取自定义布局文件pop.xml的视图
		popupView = getLayoutInflater().inflate(R.layout.popview_list, null, false);
		popupViewList = (ListView) popupView.findViewById(R.id.lstPopView);

		ArrayList<PopupItem> popupItems = new ArrayList<PopupItem>();
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_near_days)));
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_near_month)));
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_month)));
		PopupListArrayAdapter adapter = new PopupListArrayAdapter(this, R.layout.popview_list_item, popupItems);

		popupViewList.setAdapter(adapter);

		linearLayoutTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					ivTopic.setImageResource(R.drawable.ic_menu_trangle_down);
					return;
				} else {
					showPopupWindowView();
					ivTopic.setImageResource(R.drawable.ic_menu_trangle_up);
					// popupwindow.showAsDropDown(v, 0, 5);
				}

			}
		});

	}

	/**
	 * dissmiss popview window
	 */
	protected void dismissPopviewWindow() {
		if (popupwindow != null && popupwindow.isShowing()) {
			popupwindow.dismiss();
			ivTopic.setImageResource(R.drawable.ic_menu_trangle_down);
			return;
		}
	}


	/**
	 * 显示popupWindow
	 */
	protected void showPopupWindowView() {
		ScreenInfo screenInfo = new ScreenInfo();
		LocalUtils.getScreenWidthAndHeight(this, screenInfo);
		int width = screenInfo.getWidth() / 4;
		int heigth = screenInfo.getHeight() / 2;
		popupwindow = new PopupWindow(popupView, width, heigth);
		// popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.showAsDropDown(linearLayoutTopic);
		// 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
	}

	@Override
	protected void onStop() {
		Log.d("Life Cycle", "BaseFragmentActivity on stop");
		dismissPopviewWindow();
		super.onStop();
	}
}
