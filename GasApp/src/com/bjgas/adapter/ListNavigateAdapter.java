package com.bjgas.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjgas.bean.GridItem;
import com.bjgas.gasapp.R;

public class ListNavigateAdapter extends ArrayAdapter<GridItem> {

	Context context;
	int resource;
	ArrayList<GridItem> items = new ArrayList<GridItem>();
	LinearLayout layout;

	public int curSelected = 0;

	public ListNavigateAdapter(Context context, int resource, ArrayList<GridItem> items) {
		// 一定要把数据传到super中去！！！不然什么都显示不出来
		super(context, resource, items);
		this.context = context;
		this.resource = resource;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		Holder holder = null;
		if (row == null) {
			// 如果view为null，则从resource中取出row
			// inflate方法表示把一个resource转换成view，然后可以通过findviewbyid的方式来取得所有的控件
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			// 申请一个new holder，将取得的内容放在holder中
			holder = new Holder();
			holder.imgView = (ImageView) row.findViewById(R.id.item_image);
			holder.tvView = (TextView) row.findViewById(R.id.item_text);

			row.setTag(holder);
		} else {
			holder = (Holder) row.getTag();
		}

		// 将内容放入到holder中
		GridItem item = items.get(position);
		holder.imgView.setImageBitmap(item.getImage());
		holder.tvView.setText(item.getTitle());
		holder.tvView.setBackgroundColor(R.drawable.fontcolor);
		// row.setBackgroundColor(0xffff0000);
		row.setBackgroundColor((position == curSelected) ? Color.argb(0x80, 0x20, 0xa0, 0x40) : Color.argb(0,
				0, 0, 0));
		// ((TextView) convertView).setText((String) getItem(position));


		return row;
	}

	/**
	 * Holder类，对应的是grid_item中的控件 所谓的Adapter，其实就是控件和类对应关系的适配器 <br>
	 * row_grid.xml和griditem类中的数据如何对应起来
	 * 
	 * @author gqq
	 *
	 */
	static class Holder {
		ImageView imgView;
		TextView tvView;
	}
}
