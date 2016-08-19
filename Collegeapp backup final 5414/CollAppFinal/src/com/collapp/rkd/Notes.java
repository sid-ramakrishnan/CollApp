package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Notes extends Activity implements OnClickListener{
	String name,path,path2;
	EditText et;
	String ettext ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle s = getIntent().getExtras();
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"Notes";
		path2= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp";
		File f = new File(path);
		if(!f.exists())
		f.mkdirs();
		name = s.getString("filename");
		// decode images postit.png, save.png, add.png
		InputStream strm = getResources().openRawResource(R.drawable.postit);
        Bitmap bmp = BitmapFactory.decodeStream(strm);
        InputStream strm2 = getResources().openRawResource(R.drawable.save);
         Bitmap bmp2 = BitmapFactory.decodeStream(strm2);
         InputStream strm3 = getResources().openRawResource(R.drawable.add);
         Bitmap bmp3 = BitmapFactory.decodeStream(strm3);
        setContentView(R.layout.notes);
         et = (EditText) findViewById(R.id.note);
         String temp = path+File.separator+name+".txt";
         f = new File(temp);
         if(f.exists())
         {
        	 load(); // load previous notes
         }
        ImageButton save = (ImageButton)findViewById(R.id.save);
        ImageButton add = (ImageButton) findViewById(R.id.add);
        save.setOnClickListener(this);
        add.setOnClickListener(this);
	}

	void load()
	{
	    // function to load notes previously saved
		File f = new File(path +File.separator+name+".txt");
		if(f.exists())
		{
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
				String data="";
				String s ="";
				while((s=bfr.readLine())!=null)
				{
				 data+=s+"\n";
				}
				bfr.close();
				fis.close();
				et.setText(data);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

	}

	@Override
	public void onBackPressed() {
	    // when backbutton pressed option to save
		// TODO Auto-generated method stub
		// super.onBackPressed();
		if(!ettext.equals(et.getText().toString()))
		{final AlertDialog hint2 =new AlertDialog.Builder(Notes.this).create();
        hint2.setTitle("Quit ?");
        hint2.setMessage("Do you want to save the note before you quit ?");
        hint2.setButton("Yes", new DialogInterface.OnClickListener()
        {
			@Override
			public void onClick(DialogInterface alert, int which)
			{
				save();
				hint2.dismiss();
				finish();

			}
		}
        );

        hint2.setButton2("No", new DialogInterface.OnClickListener()
        {
			@Override
			public void onClick(DialogInterface alert, int which)
			{
				hint2.dismiss();
			 finish();
			}
		}
        );
        hint2.setButton3("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				hint2.dismiss();
			}
		});
        hint2.show();

	}
		finish();
	}

	void save()
	{
	    // function that saves changes made to a note
		int todo =0,er=0;
		File f = new File(path);
	    if(!f.exists())
		f.mkdirs();
	    String temp= path +File.separator+name+".txt";
	    f = new File(temp);
	    try {
			if(!f.exists())
	    	{
				f.createNewFile();
			    todo =1;
	    	}
				FileOutputStream fos = new FileOutputStream (f);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				osw.write(et.getText().toString());
				osw.close();
				fos.close();
				ettext = et.getText().toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			er=1;
			Toast.makeText(getApplicationContext(), "Error 1",Toast.LENGTH_LONG).show();
		}

	    if(todo==1)
	    {
	    	String s = path2 +File.separator+"noteslog.txt";
		    File f2 = new File(s);
		    if(!f2.exists())
		    	{
		    	try {
					f2.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	}
			FileInputStream fis2;
			try {
				fis2 = new FileInputStream(f2);
				BufferedReader bfr2 = new BufferedReader(new InputStreamReader(fis2));
				String data2="";
				String s2 ="";
				while((s2=bfr2.readLine())!=null)
				{
				 data2+=s2+"\n";
				}
				data2+=name+"\n";
				bfr2.close();
				fis2.close();
				FileOutputStream fos2 = new FileOutputStream (f2);
				OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
				osw2.write(data2);
				osw2.close();
				fos2.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				er=1;
			} catch (IOException e) {
				er=1;
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "problem writin", Toast.LENGTH_LONG).show();
			}

	    }
	    if(er==0)
	    	Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// on click listeners for the two buttons to add note and to save
		switch(arg0.getId())
		{
		case R.id.save : save();
		                 break;
		case R.id.add :
						save();
						final AlertDialog hint2 =new AlertDialog.Builder(Notes.this).create();
				        hint2.setTitle("New Note");
				        hint2.setMessage("New Note description");
				        LinearLayout l = new LinearLayout(hint2.getContext());
				        l.setOrientation(LinearLayout.VERTICAL);
				        final EditText et2 = new EditText(hint2.getContext());
				        TextView tv = new TextView(hint2.getContext());
				        tv.setText("Enter the note name");
				        Button b = new Button(hint2.getContext());
				        b.setText("Save");
				        b.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if(et2.getText().toString()==null)
								{
									Toast.makeText(getApplicationContext(), "Please fill the note name", Toast.LENGTH_LONG).show();

								}
								else
								{
								Intent intent = new Intent(getApplicationContext(),Notes.class);
								String take=et2.getText().toString();
								intent.putExtra("filename", take);
								hint2.dismiss();
								startActivity(intent);
								}
							}
						});
				        l.addView(tv);
				        l.addView(et2);
				        l.addView(b);
				        hint2.setView(l);
				        hint2.show();
						break;

		}
	}

}


