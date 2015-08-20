package com.example.batterybindview.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

//usage:
//private BatteryRcvBindView batteryReceiver;
//batteryReceiver = new BatteryRcvBindView();
//
//IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
//registerReceiver(batteryReceiver, filter); 
//
//unregisterReceiver(batteryReceiver);

public class BatteryRcvBindView extends BroadcastReceiver {
	BatteryImgView mView = null;
	public BatteryRcvBindView(){	
	}
	
	public BatteryRcvBindView(BatteryImgView v){
		mView = v;
	}
	
	public void bindView(BatteryImgView v){
		if(mView == null)
			mView = v;
	}
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int status = intent.getIntExtra("status", 1); // v1
		int batteryLevel = intent.getIntExtra("level", 0); // v0

		if(mView == null)
			return;
		
		if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
			mView.setStatus(true);
		} 
		else{
			mView.setStatus(false);
		}

		if (batteryLevel != 0) {			
			mView.drawByScale(batteryLevel);
		}
		return;
	}
}