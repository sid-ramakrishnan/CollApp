package com.collapp.rkd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		//String s = getIntent().getExtras().getString("User's message");
		 Intent service1 = new Intent(context, MyAlarmService.class);
		 context.startService(service1);
	}

}
