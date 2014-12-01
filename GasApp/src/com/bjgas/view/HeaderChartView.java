package com.bjgas.view;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.bjgas.adapter.PopupListArrayAdapter;
import com.bjgas.bean.PopupItem;
import com.bjgas.common.SearchMethod;
import com.bjgas.gasapp.R;

public class HeaderChartView extends LinearLayout {

	View view;
	LinearLayout linearLayoutTopic;
	View popupView;
	ListView popupViewList;
	protected PopupWindow popupwindow;
	private ImageView ivTopic;
	private TextView textViewTopic;

	LayoutInflater layoutInflater;

	ArrayList<PopupItem> popupItems;

	/**
	 * ��item�ı��ǻ��Ļص�����
	 */
	OnPopupWindowListItemClick mpoPopupWindowListItemClick;

	public interface OnPopupWindowListItemClick {
		void changeFragments(SearchMethod sm);
	}

	public void setOnPopupWindowListItemClick(OnPopupWindowListItemClick k) {
		this.mpoPopupWindowListItemClick = k;
	}

	public HeaderChartView(Context context) {
		super(context);
		initView(context);
	}

	public HeaderChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.header_chart, this);
		initHead();
	}

	/**
	 * ��ʼ��ͷ����ʾ���������е�chart��������ͬ��ͷ�������Ը÷���д������С�
	 */
	@SuppressLint("InflateParams")
	protected void initHead() {

		linearLayoutTopic = (LinearLayout) findViewById(R.id.linearLayoutTopic);
		ivTopic = (ImageView) findViewById(R.id.imageViewTopic);
		textViewTopic = (TextView) findViewById(R.id.textViewTopic);
		// // ��ȡ�Զ��岼���ļ�pop.xml����ͼ
		popupView = layoutInflater.inflate(R.layout.popview_list, null, false);
		popupViewList = (ListView) popupView.findViewById(R.id.lstPopView);

		popupItems = new ArrayList<PopupItem>();
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_today)));
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_near_days)));
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_near_month)));
		popupItems.add(new PopupItem(android.R.drawable.ic_menu_info_details, getResources().getString(
				R.string.head_month)));

		PopupListArrayAdapter adapter = new PopupListArrayAdapter(getContext(), R.layout.popview_list_item, popupItems);

		popupViewList.setAdapter(adapter);

		// ����listView��click�¼�
		popupViewList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("parent_class", parent.getClass().getSimpleName());
				Log.d("view_class", view.getClass().getSimpleName());
				if (1 == position && mpoPopupWindowListItemClick != null)
					mpoPopupWindowListItemClick.changeFragments(SearchMethod.Week);
				else if (2 == position && mpoPopupWindowListItemClick != null) {
					mpoPopupWindowListItemClick.changeFragments(SearchMethod.Month);
				} else if (3 == position && mpoPopupWindowListItemClick != null) {
					mpoPopupWindowListItemClick.changeFragments(SearchMethod.ExactMonth);
				} else {
					mpoPopupWindowListItemClick.changeFragments(SearchMethod.Now);

				}
				changeTopicLabel(position);
				dismissPopviewWindow();
			}

		});

		// ����linearLayoutTopic��click�¼�
		linearLayoutTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("setOnClickListener", "onclick");

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
	 * �޸Ķ����Ĳ�ѯ������ʾ
	 * 
	 * @param position
	 */
	private void changeTopicLabel(int position) {
		textViewTopic.setText(popupItems.get(position).getText());
	}

	/**
	 * dissmiss popview window
	 */
	public void dismissPopviewWindow() {
		if (popupwindow != null && popupwindow.isShowing()) {
			popupwindow.dismiss();
			ivTopic.setImageResource(R.drawable.ic_menu_trangle_down);
			return;
		}
	}

	/**
	 * ��ʾpopupWindow
	 */
	protected void showPopupWindowView() {
		popupwindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setTouchable(true);

		// ���ñ���Ϊnull�������ؼ�PopupWindow�ͻ�����
		popupwindow.setBackgroundDrawable(null);

		popupView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissPopviewWindow();
			}
		});

		// popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.showAsDropDown(linearLayoutTopic);
		// ���ö���Ч�� [R.style.AnimationFade ���Լ����ȶ���õ�]
	}

}
