/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bjgas.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Uses a combination of a PageTransformer and onTouchEvent to create the
 * illusion of a vertically scrolling ViewPager.
 * 
 * Requires API 11+
 * 
 */
@SuppressLint("NewApi")
public class DirectionalViewPager extends ViewPager {

	public DirectionalViewPager(Context context) {
		super(context);
		init();
	}

	public DirectionalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// The majority of the magic happens here
		setPageTransformer(true, new VerticalPageTransformer());
		// The easiest way to get rid of the overscroll drawing that happens on
		// the left and right
		setOverScrollMode(OVER_SCROLL_NEVER);
	}

	private class VerticalPageTransformer implements ViewPager.PageTransformer {

		@Override
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				view.setAlpha(1);

				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);

				// set Y position to swipe in from top
				float yPosition = position * pageHeight;
				view.setTranslationY(yPosition);

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

	/**
	 * Swaps the X and Y coordinates of your touch event
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// swap the x and y coords of the touch event
		ev.setLocation(ev.getY(), ev.getX());

		return super.onTouchEvent(ev);
	}
}