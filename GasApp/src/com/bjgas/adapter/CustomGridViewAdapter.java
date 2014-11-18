package com.bjgas.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjgas.bean.GridItem;
import com.bjgas.gasapp.R;

public class CustomGridViewAdapter extends ArrayAdapter<GridItem> {

	Context context;
	int resource;
	ArrayList<GridItem> items = new ArrayList<GridItem>();
	LinearLayout layout;

	public CustomGridViewAdapter(Context context, int resource, ArrayList<GridItem> items) {
		// һ��Ҫ�����ݴ���super��ȥ��������Ȼʲô����ʾ������
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
			// ���viewΪnull�����resource��ȡ��row
			// inflate������ʾ��һ��resourceת����view��Ȼ�����ͨ��findviewbyid�ķ�ʽ��ȡ�����еĿؼ�
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			// ����һ��new holder����ȡ�õ����ݷ���holder��
			holder = new Holder();
			holder.imgView = (ImageView) row.findViewById(R.id.item_image);
			holder.tvView = (TextView) row.findViewById(R.id.item_text);

			row.setTag(holder);
		} else {
			holder = (Holder) row.getTag();
		}

		// �����ݷ��뵽holder��
		GridItem item = items.get(position);
		holder.imgView.setImageBitmap(item.getImage());
		holder.tvView.setText(item.getTitle());

		return row;
	}

	/**
	 * Holder�࣬��Ӧ����grid_item�еĿؼ� ��ν��Adapter����ʵ���ǿؼ������Ӧ��ϵ�������� <br>
	 * row_grid.xml��griditem���е�������ζ�Ӧ����
	 * 
	 * @author gqq
	 *
	 */
	static class Holder {
		ImageView imgView;
		TextView tvView;
	}
}
