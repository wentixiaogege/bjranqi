package com.bjgas.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjgas.bean.PopupItem;
import com.bjgas.gasapp.R;

public class PopupListArrayAdapter extends ArrayAdapter<PopupItem> {
	private int[] colors = new int[] { 0xff3cb371, 0xffa0a0a0 };
	private Context mContext;
	private int resource;

	public PopupListArrayAdapter(Context context, int resource, List<PopupItem> data) {
		super(context, resource, data);
		this.mContext = context;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();

			// ��resource�ļ��еõ�һ��view
			convertView = LayoutInflater.from(mContext).inflate(resource, parent, false);
			holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
			holder.text = (TextView) convertView.findViewById(R.id.ItemText);
			// ��holder�󶨵�convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// ��ViewHolder�����������
		holder.image.setImageResource(getItem(position).getImage());
		holder.text.setText(getItem(position).getText());

		int colorPos = position % colors.length;
		// convertView.setBackgroundColor(colors[colorPos]);

		return convertView;
	}

	/**
	 * ViewHolder�����Դ���item�пؼ�������
	 */
	final class ViewHolder {
		ImageView image;
		TextView text;
	}
}