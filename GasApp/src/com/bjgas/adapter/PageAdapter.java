package com.bjgas.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bjgas.common.SearchMethod;

public class PageAdapter extends FragmentPagerAdapter {
	ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	public PageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments.clear();
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int pos) {
		return fragments.get(pos);
	}


	@Override
	public int getCount() {
		return fragments.size();
	}
}
