package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class NotesList extends Activity implements OnClickListener{

	ImageButton ib;
	ArrayList<String> listofnotes = new ArrayList();
	ImageButton del;
	TextView tv;
	String passed,path;
	LinearLayout l ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// decode image removenote.png
		InputStream strm = getResources().openRawResource(R.drawable.removenote);
        Bitmap bmp = BitmapFactory.decodeStream(strm);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noteslist);
		ib = (ImageButton)findViewById(R.id.add2);
		tv = (TextView) findViewById(R.id.nonotes);
	    l = (LinearLayout) findViewById(R.id.list);
	    path= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"Notes"+File.separator;
	    del = (ImageButton) findViewById(R.id.delnote);
	    del.setOnClickListener(this);
	    ib.setOnClickListener(this);
	}

	void doit()
	{
	    // function  called whenever on resume method called updates screen to hold current status
		l.removeAllViews();
		String path2= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"noteslog.txt";
	    File f = new File(path2);
	    if(!f.exists())
	    {
	    	tv.setVisibility(View.VISIBLE);
	    }
	    else
	    {
	    	del.setVisibility(View.VISIBLE);
	    	tv.setVisibility(View.INVISIBLE);
	    	try {
				FileInputStream fis = new FileInputStream(f);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
				ArrayList <String> str = new ArrayList();
				str.clear();
				String s ="";
				while((s=bfr.readLine())!=null)
				{
					str.add(s);
					listofnotes.add(s);
				}
				bfr.close();
				fis.close();
				final ArrayList<TextView> ets = new ArrayList();
				for(int i =0; i<str.size();i++)
				{
					LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					lp.setMargins(60, 20, 0, 0);
					ets.add(new TextView(l.getContext()));
					ets.get(i).setText(str.get(i));
					ets.get(i).setTextSize(30);
					ets.get(i).setLayoutParams(lp);
					ets.get(i).setId(i);
					ets.get(i).setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int ss = v.getId();
							passed = ets.get(ss).getText().toString();
							loads();
						}
					});
					l.addView(ets.get(i));
				}

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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		doit();
			}

	void loads()
	{
	     // load contents of the note opened
		File f = new File(path+passed+".txt");
		if(f.exists())
		{
			Intent i = new Intent(getApplicationContext(),Notes.class);
			i.putExtra("filename", passed);
			startActivity(i);
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// on click listeners for the two buttons to delete a note and to add new note
		switch(arg0.getId())
		{
		case R.id.delnote:  final AlertDialog hint22 =new AlertDialog.Builder(NotesList.this).create();
        hint22.setTitle("Delete Note");
        hint22.setMessage("Select Note to delete");
        LinearLayout l2 = new LinearLayout(hint22.getContext());
        l.setOrientation(LinearLayout.VERTICAL);
        final EditText et22 = new EditText(hint22.getContext());
        TextView tv2 = new TextView(hint22.getContext());
        String sse = "";
        for(int kl=0;kl<listofnotes.size();kl++)
        {
        	sse+=String.valueOf(kl+1)+". "+listofnotes.get(kl)+"\n";
        }
        tv2.setText(sse);
        Button b2 = new Button(hint22.getContext());
        b2.setText("Delete");
        b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et22.getText().toString()==null)
				{

					Toast.makeText(getApplicationContext(), "Please fill the note name", Toast.LENGTH_LONG).show();

				}
				else
				{
					Integer x;
					try{
						 x = Integer.valueOf(et22.getText().toString());
						 if(x<listofnotes.size())
						 {
							 String remove = listofnotes.get(x-1);
							 listofnotes.remove(x-1);
							 String ssl ="";
							 for(int is=0;is<listofnotes.size();is++)
							 {
								 ssl+=listofnotes.get(is)+"\n";
							 }
							 File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"noteslog.txt");
							 FileOutputStream fos = new FileOutputStream(f);
							 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
							 bw.write(ssl);
							 bw.close();
							 fos.close();
							 f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"Notes"+File.separator+remove+".txt");
							 f.delete();
							 doit();

						 }
						 else
						 {
							 Toast.makeText(getApplicationContext(), "Enter valid index", Toast.LENGTH_LONG).show();
						 }
					}
					catch(NumberFormatException e)
					{
						Toast.makeText(getApplicationContext(), "Enter a number only",Toast.LENGTH_LONG).show();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
        l2.addView(tv2);
        l2.addView(et22);
        l2.addView(b2);
        hint22.setView(l2);
        hint22.show();
							break;
		case R.id.add2 : final AlertDialog hint2 =new AlertDialog.Builder(NotesList.this).create();
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

