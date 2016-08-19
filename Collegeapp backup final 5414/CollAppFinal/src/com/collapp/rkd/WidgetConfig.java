package com.collapp.rkd;

import java.io.InputStream;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class WidgetConfig extends Activity //implements OnClickListener {
{
	EditText et ;
	AppWidgetManager awm;
	Context c;
	int awid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		//setContentView(R.layout.configure);
		// decode image piggybank.c
		InputStream strm = getResources().openRawResource(R.drawable.piggybank);
        Bitmap bmp = BitmapFactory.decodeStream(strm);
		//Button b = (Button) findViewById(R.id.bwidgetconfig);
		//b.setOnClickListener(this);
		c = WidgetConfig.this;
		//et = (EditText) findViewById(R.id.etwidgetconfig);

		Intent i = getIntent();
		Bundle extras = i.getExtras();
		if(extras!=null)
		{
			awid = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		else
		{
			finish();
		}
		awm = AppWidgetManager.getInstance(c);
		RemoteViews rv = new RemoteViews(c.getPackageName(),R.layout.expense_main);
		//rv.setTextViewText(R.id.tvconfiginput, e);
		//Intent in;
		//PendingIntent pi;
		Intent in = new Intent(c,Splash.class);
		PendingIntent pi =  PendingIntent.getActivity(c,0,in,0);
		rv.setOnClickPendingIntent(R.id.pig, pi);
		awm.updateAppWidget(awid, rv);
		Intent result = new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,awid);
		setResult(RESULT_OK,result);
		finish();
	}

/*	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
	//String e = et.getText().toString();
	RemoteViews rv = new RemoteViews(c.getPackageName(),R.layout.activity_main);
	rv.setTextViewText(R.id.tvconfiginput, e);
	//Intent in;
	//PendingIntent pi;
	Intent in = new Intent(c,Splash.class);
	PendingIntent pi =  PendingIntent.getActivity(c,0,in,0);
	rv.setOnClickPendingIntent(R.id.bwidgetopen, pi);
	awm.updateAppWidget(awid, rv);
	Intent result = new Intent();
	result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,awid);
	setResult(RESULT_OK,result);
	finish();
	}
	*/

}
