package com.bjgas.view;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bjgas.adapter.ListNavigateAdapter;
import com.bjgas.adapter.PopupListArrayAdapter;
import com.bjgas.bean.GridItem;
import com.bjgas.bean.PopupItem;
import com.bjgas.common.SearchMethod;
import com.bjgas.common.VerticalViewPager;
import com.bjgas.gasapp.R;

public class ListHeaderChartView extends LinearLayout {

	View view;
	LinearLayout linearLayoutTopic;
	VerticalViewPager pager;
	ImageView imgRefresh;
	View popupView;
	ListView popupViewList;
	ListView lstNevigate;
	protected PopupWindow popupwindow;
	private ImageView ivTopic;
	private TextView textViewTopic;
	private SearchMethod searchMethod;
	ArrayList<GridItem> items;
	Animation operatingAnim;

	private String startMonth;
	private String endMonth;

	LayoutInflater layoutInflater;

	ArrayList<PopupItem> popupItems;

	/**
	 * 显示哪一个子项目
	 */
	private int mDisplayedItem;

	/**
	 * 当item改变是还的回调函数
	 */
	OnPopupWindowListItemClick mpoPopupWindowListItemClick;
	OnNavigaterClick mOnNavigaterClick;

	public interface OnPopupWindowListItemClick {
		void changeFragments(View view);

		void changeSearchFragments(View view, String startMonth, String endMonth);
	}

	public interface OnNavigaterClick {
		void addNewFragments(View view, int position, long id);
	}

	public void setOnNavigaterClick(OnNavigaterClick n) {
		this.mOnNavigaterClick = n;
	}

	public void setOnPopupWindowListItemClick(OnPopupWindowListItemClick k) {
		this.mpoPopupWindowListItemClick = k;
	}

	public ListHeaderChartView(Context context) {
		super(context);
		initView(context);
	}

	public ListHeaderChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ListHeaderChartView(Context context, ArrayList<GridItem> items) {
		super(context);
		this.items = items;
		initView(context);
	}

	private void initView(Context context) {
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.header_chart_new, this);
		lstNevigate = (ListView) findViewById(R.id.lstNevigate);
		pager = (VerticalViewPager) findViewById(R.id.pager);
		imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
		operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.refresh);
		// 首先显示第0条
		mDisplayedItem = 0;
		// 首先显示当前状态
		setSearchMethod(SearchMethod.Now);
		initHead();
		if (items != null)
			updateListView();

		// 刷新按钮
		imgRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mpoPopupWindowListItemClick != null)
					if (searchMethod == SearchMethod.Now || searchMethod == SearchMethod.Week
							|| searchMethod == SearchMethod.Month) {
						mpoPopupWindowListItemClick.changeFragments(ListHeaderChartView.this);
					} else if (searchMethod == SearchMethod.Search) {
						mpoPopupWindowListItemClick.changeSearchFragments(ListHeaderChartView.this, startMonth,
								endMonth);
					}

			}
		});
	}

	public void rotateIcon() {
		// 旋转更新按钮
		if (operatingAnim != null) {
			// findViewById(R.id.footer).setVisibility(View.VISIBLE);
			// mUpdate.setImageResource(R.drawable.base_loading_large_icon);
			imgRefresh.startAnimation(operatingAnim);
		}

	}

	/**
	 * 恢复，转圈停止，rooter消失
	 */
	public void stopRotateIcon() {
		// mUpdate.setImageResource(R.drawable.title_update);
		// findViewById(R.id.footer).setVisibility(View.GONE);
		// mUpdate.clearAnimation();
		imgRefresh.clearAnimation();
	}

	/**
	 * 更新左侧的导航栏
	 */
	ListNavigateAdapter adapter;

	private void updateListView() {
		adapter = new ListNavigateAdapter(getContext(), R.layout.row_navigate, items);
		lstNevigate.setAdapter(adapter);
		lstNevigate.setFocusable(true);
		lstNevigate.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ListView lv = (ListView) parent;

				adapter.curSelected = position;
				lv.setAdapter(adapter);
				lv.invalidate();
				lv.setSelection(position);
				if (null != mOnNavigaterClick) {
					setmDisplayedItem(position);

					mOnNavigaterClick.addNewFragments(ListHeaderChartView.this, position, id);
				}
			}
		});
	}

	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	/**
	 * 初始化头部显示，由于所有的chart都具有相同的头部，所以该方法写入基类中。
	 */
	@SuppressLint("InflateParams")
	protected void initHead() {

		linearLayoutTopic = (LinearLayout) findViewById(R.id.linearLayoutTopic);
		ivTopic = (ImageView) findViewById(R.id.imageViewTopic);
		textViewTopic = (TextView) findViewById(R.id.textViewTopic);
		// // 获取自定义布局文件pop.xml的视图
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

		// 设置listView的click事件
		popupViewList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("parent_class", parent.getClass().getSimpleName());
				Log.d("view_class", view.getClass().getSimpleName());
				if (null != mpoPopupWindowListItemClick) {
					if (1 == position) {
						setSearchMethod(SearchMethod.Week);
						mpoPopupWindowListItemClick.changeFragments(ListHeaderChartView.this);
					} else if (2 == position) {
						setSearchMethod(SearchMethod.Month);
						mpoPopupWindowListItemClick.changeFragments(ListHeaderChartView.this);
					} else if (3 == position) {
						setSearchMethod(SearchMethod.Search);
						Calendar c = Calendar.getInstance();
						// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
						new DoubleDatePickerDialog(getContext(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
									int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
									int endDayOfMonth) {
								Log.d("DoubleDatePickerDialog", "onDateset");
								startMonth = String.format("%d-%d", startYear, (startMonthOfYear + 1));
								endMonth = String.format("%d-%d", endYear, (endMonthOfYear + 1));
								mpoPopupWindowListItemClick.changeSearchFragments(ListHeaderChartView.this, startMonth,
										endMonth);
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
					} else {
						setSearchMethod(SearchMethod.Now);
						mpoPopupWindowListItemClick.changeFragments(ListHeaderChartView.this);
					}
				}
				changeTopicLabel(position);
				dismissPopviewWindow();
			}

		});

		// 设置linearLayoutTopic的click事件
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

	private DatePickerDialog createDialogWithoutDateField() {

		DatePickerDialog dpd = new DatePickerDialog(this.getContext(), null, 2014, 1, 24);
		dpd.setTitle("");
		try {
			java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
			for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
					java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
					for (java.lang.reflect.Field datePickerField : datePickerFields) {
						Log.i("test", datePickerField.getName());
						if ("mDaySpinner".equals(datePickerField.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							// datePicker.getCalendarView().setVisibility(View.GONE);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}

			}
		} catch (Exception ex) {
			Log.e("createDialogWithoutDateField error!", ex.getMessage());
		}
		return dpd;

	}

	/**
	 * 修改顶部的查询条件显示
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
	 * 显示popupWindow
	 */
	protected void showPopupWindowView() {
		popupwindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setTouchable(true);

		// 设置背景为null，按返回键PopupWindow就会隐藏
		popupwindow.setBackgroundDrawable(null);

		popupView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissPopviewWindow();
			}
		});

		// popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.showAsDropDown(linearLayoutTopic);
		// popupwindow.sh
		// 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
	}

	public SearchMethod getSearchMethod() {
		return searchMethod;
	}

	private void setSearchMethod(SearchMethod searchMethod) {
		this.searchMethod = searchMethod;
	}

	public VerticalViewPager getPager() {
		return this.pager;
	}

	public int getmDisplayedItem() {
		return mDisplayedItem;
	}

	private void setmDisplayedItem(int mDisplayedItem) {
		this.mDisplayedItem = mDisplayedItem;
	}
}
