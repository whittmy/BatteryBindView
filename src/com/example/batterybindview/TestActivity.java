package com.example.batterybindview;

import com.example.batterybindview.view.BatteryImgView;
import com.example.batterybindview.view.BatteryRcvBindView;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TestActivity extends Activity {

	//usage:1
	private BatteryRcvBindView batteryReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//usage:2
		batteryReceiver = new BatteryRcvBindView(
				(BatteryImgView) findViewById(R.id.battery));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//usage: 3
		if (batteryReceiver != null)
			registerReceiver(batteryReceiver, new IntentFilter(
					"android.intent.action.BATTERY_CHANGED"));
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (batteryReceiver != null)
			unregisterReceiver(batteryReceiver);
	}
}
