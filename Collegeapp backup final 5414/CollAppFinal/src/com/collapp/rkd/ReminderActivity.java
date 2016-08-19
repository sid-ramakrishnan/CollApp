package com.collapp.rkd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.Calendar;



//import com.example.pickerdate.Pickerdate.DatePickerFragment;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class ReminderActivity extends Activity {

	NotificationManager NM;         //Notification Manager
	private TextView MainHead;
	static TextView mDateDisplay;
    private Button mPickDate;
    static TextView mTimeDisplay;
    private Button mPickTime;
    private Button make;
    private Button View;
    private PendingIntent pendingIntent;
    FileOutputStream fos;
    ObjectOutputStream oos;
    FileInputStream fis;
    ObjectInputStream ois;
    static int writeTime[]=new int[5];
    int time1[];

    int day,hour,min,month,year;
    //int[0]=min,[1]=hr,[2]=day,[3]=month,[4]=yr;
    // private TextView test;
    int time;

    private EditText eMessage;
    //Class to create the Time Picker Dialog Box
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
		public EditText editText;
		public static int h;
		public static int m;
        TimePicker dpResult;
        public Dialog onCreateDialog(Bundle savedInstanceState) {
        	final Calendar c = Calendar.getInstance();
        	int hour=c.get(Calendar.HOUR_OF_DAY);
        	int min=c.get(Calendar.MINUTE);
        	//Return's user selected Time
        	return new TimePickerDialog(getActivity(),this,hour,min,false);
        }

		public void onTimeSet(TimePicker view, int hour, int min) {
			// TODO Auto-generated method stub
			 writeTime[0]=min;
			 writeTime[1]=hour;
			mTimeDisplay.setText(String.valueOf(hour)+":"+String.valueOf(min));
		}
	}
    //Class to create Date Picker Dialog Box
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
		public static int dy;
		public static int mn;
		public static int yr;
		//public EditText editText;
        DatePicker dpResult;
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            //return's user selected date
            return new DatePickerDialog(getActivity(), this, year, month, day);
            }
        public void onDateSet(DatePicker view, int year, int month, int day) {

        	 writeTime[2]=day;
             writeTime[3]=month;
             writeTime[4]=year;
            mDateDisplay .setText(String.valueOf(day) + "/"
                    + String.valueOf(month+1) + "/" + String.valueOf(year));

        }

	}

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);

		//The Typeface class specifies the typeface and intrinsic style of a font
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Bangers.ttf");
		MainHead=(TextView)findViewById(R.id.textViewforappmenu);
		MainHead.setTypeface(typeface); //Sets the textview to the desired font
		mDateDisplay = (TextView) findViewById(R.id.ddmmyyyy);
		mDateDisplay.setTypeface(typeface);
		mPickDate = (Button) findViewById(R.id.bDate);
		mPickDate.setTypeface(typeface);
		mTimeDisplay = (TextView) findViewById(R.id.hhmm);
		mTimeDisplay.setTypeface(typeface);
		mPickTime = (Button) findViewById(R.id.bTime);
		mPickTime.setTypeface(typeface);
		eMessage = (EditText)findViewById(R.id.editMessage);

		View=(Button)findViewById(R.id.bView);
		View.setTypeface(typeface);
		make = (Button)findViewById(R.id.bCreate);
		make.setTypeface(typeface);

		// Class will be called when the Date button is pressed
		mPickDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		// Class will be called when the Time button is pressed
		mPickTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new TimePickerFragment();
				newFragment.show(getFragmentManager(),"timePicker");
			}
		});

		// Class will be called when Create Reminder is pressed
		make.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String Message = eMessage.getText().toString(); //Stores the Message of the reminder



				 Calendar cal = Calendar.getInstance();
					min=writeTime[0];
					hour=writeTime[1];
			        day=writeTime[2];
			        month=writeTime[3];
			        year=writeTime[4];
			        //System.out.println("MIN"+min);
			        //System.out.println(hour);
			        //System.out.println(day);
					cal.set(Calendar.DAY_OF_MONTH,day);
					cal.set(Calendar.MONTH,month);
					cal.set(Calendar.YEAR,year);
					cal.set(Calendar.HOUR_OF_DAY,hour);
					cal.set(Calendar.MINUTE,min);
					cal.set(Calendar.SECOND, 0);



				try {
					String filesy = Environment.getExternalStorageDirectory()+File.separator+"CollApp";
					File f= new File(filesy);
					f.mkdirs();
					filesy+=File.separator+"msg.txt";
					f = new File(filesy);
					if(!f.exists())
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					bw.write(Message);
					bw.close();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			//Making another file
			try {
					String filesy = Environment.getExternalStorageDirectory()+File.separator+"CollApp";
					File f= new File(filesy);
					f.mkdirs();
					filesy+=File.separator+"msg2.txt";
					f = new File(filesy);
					if(!f.exists())
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f,true);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					bw.write("\nDate:"+day+"/"+month+"/"+year);
					bw.write("\nTime:"+hour+":"+min);
					bw.write("\nMessage-" + Message);
					bw.close();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// TODO Auto-generated method stub
				Intent myIntent = new Intent(ReminderActivity.this, MyReceiver.class);
				SharedPreferences sp = getSharedPreferences("User's message",Context.MODE_PRIVATE);
				Editor spe = sp.edit();
				spe.putString("message", Message);
				//Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
				spe.commit();
				myIntent.putExtra("User's message", Message);
			    pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, myIntent,0);

			    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			    alarmManager.set(AlarmManager.RTC,cal.getTimeInMillis(), pendingIntent);

			    Toast.makeText(getApplicationContext(), "Event Created",Toast.LENGTH_SHORT).show();


			}
		});

		//Class will called when the View Reminder button is pressed
		View.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReminderActivity.this,ViewReminder.class);
				startActivity(intent);
			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
