package com.collapp.rkd;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;


public class ExpenseActivity extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub

		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Random r = new Random();
		int randomint = r.nextInt(100000);
		String rand = String.valueOf(randomint);

		final int n = appWidgetIds.length;
		for(int i=0;i<n;i++)
		{
			int awid = appWidgetIds[i]; // \get the id of the widget
			RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.expense_main); // set layout of the widget
			//rv.setTextViewText(R.id.tvwidgetupdate, rand);
			appWidgetManager.updateAppWidget(awid, rv);
		}
		}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Bye-Bye!!", Toast.LENGTH_LONG).show(); // message when widget is deleted

	}
}
