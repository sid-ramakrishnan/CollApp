package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.Calendar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

public class MyAlarmService extends Service {
	//Calendar c=Calendar.getInstance();
	String msg;
	FileInputStream fis;
	ObjectInputStream ois;
	int time[];
	int day,hour,min,month,year;
	String sss;
	private NotificationManager mManager;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate()
	{

		super.onCreate();
		readFile();
		SharedPreferences sp = getSharedPreferences("User's message",Context.MODE_PRIVATE);
		 sss = sp.getString("message"," " );
		 Toast.makeText(getApplicationContext(), sss, Toast.LENGTH_LONG).show();
	}

	String finals ="";
	public void readFile()
	{
		try {

		   String path = Environment.getExternalStorageDirectory()+File.separator+"CollApp";
			File f = new File(path);
		    f.mkdirs();
		    path+=File.separator+"msg.txt";
		    f = new File(path);
		    if(!f.exists())
		    	return;
		   FileInputStream fis = new FileInputStream(f);
		   BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
		   String mes;
		   while((mes=bfr.readLine())!=null)
		   {
			   finals+=mes+"\n";
		   }
		   bfr.close();
		   fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@SuppressWarnings("deprecation")
	public void onStart(Intent intent, int startId)
	   {
	       super.onStart(intent, startId);
	       finals=" ";
	       readFile();


	       this.getApplicationContext();
	       mManager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
	       Intent intent1 = new Intent(this.getApplicationContext(),ReminderActivity.class);
	       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

	       NotificationCompat.Builder mBuilder =
	    		    new NotificationCompat.Builder(this)
	    		    .setSmallIcon(R.drawable.ic_launcher)
	    		    .setAutoCancel(true).setContentTitle("Your Reminder")
	    		    .setStyle(new NotificationCompat.BigTextStyle().bigText(finals))
	    		    .setContentText(finals)
	       			.setContentIntent(pendingNotificationIntent)
	       			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
	       			.setDeleteIntent(pendingNotificationIntent);
	       mManager.notify(0, mBuilder.build());
	    }

	    @Override
	    public void onDestroy()
	    {
	        // TODO Auto-generated method stub
	        super.onDestroy();
	    }

}
