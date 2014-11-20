package com.bjgas.test;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bjgas.gasapp.R;

public class PopupWindowTest extends Activity implements OnClickListener {

	private PopupWindow popupwindow;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_window_test);

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button1:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				initmPopupWindowView();
				popupwindow.showAsDropDown(v, 0, 5);
			}
			break;

		case R.id.button2:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			}
			break;
		default:
			break;
		}

	}

	public void initmPopupWindowView() {

		// // ��ȡ�Զ��岼���ļ�pop.xml����ͼ
		View customView = getLayoutInflater().inflate(R.layout.popview_item, null, false);
		// ����PopupWindowʵ��,200,150�ֱ��ǿ�Ⱥ͸߶�
		popupwindow = new PopupWindow(customView, 500, 680);
		popupwindow.showAsDropDown(button);
		// ���ö���Ч�� [R.style.AnimationFade ���Լ����ȶ���õ�]
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		// �Զ���view��Ӵ����¼�
		customView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}

				return false;
			}
		});

		/** ���������ʵ���Զ�����ͼ�Ĺ��� */
		Button btton2 = (Button) customView.findViewById(R.id.button2);
		Button btton3 = (Button) customView.findViewById(R.id.button3);
		Button btton4 = (Button) customView.findViewById(R.id.button4);
		btton2.setOnClickListener(this);
		btton3.setOnClickListener(this);
		btton4.setOnClickListener(this);

	}

}