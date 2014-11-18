package com.bjgas.gasapp.test;

import android.test.ActivityInstrumentationTestCase2;

import com.bjgas.gasapp.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity alertaActivity;

	public MainActivityTest(Class<MainActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		// init variables
		alertaActivity = getActivity();
		testPreconditions();
	}

	// usually we test some pre-conditions. This method is provided
	// by the test framework and is called after setUp()
	public void testPreconditions() {
		assertNotNull("alertaActivity is null", alertaActivity);
	}

}
