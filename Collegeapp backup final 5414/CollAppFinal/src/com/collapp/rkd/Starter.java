package com.collapp.rkd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
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

public class Starter extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InputStream strm = getResources().openRawResource(R.drawable.cash);
        Bitmap bmp = BitmapFactory.decodeStream(strm);  
        InputStream strm2 = getResources().openRawResource(R.drawable.notescreen);
        Bitmap bmp2 = BitmapFactory.decodeStream(strm);  
        setContentView(R.layout.start);
        ImageButton ib = (ImageButton) findViewById(R.id.casher);
        ImageButton ib2 = (ImageButton) findViewById(R.id.noter);
        ib.setOnClickListener(this);
        ib2.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.noter: Intent i = new Intent(this,NotesList.class);
						 startActivity(i);
						 break;
		case R.id.casher : String path4=Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" ;
							final File f2 = new File(path4);
							if(!f2.exists())
							{
							  f2.mkdirs();
							}
							path4+=File.separator+"amountleft.txt";
							final File f = new File(path4);
							if(!f.exists())
							{
								try {
									f.createNewFile();
									final AlertDialog hint3 =new AlertDialog.Builder(Starter.this).create();
							        hint3.setTitle("Enter Detail ");  
							        hint3.setMessage("Since this is the first time, we would like to know the balance left");
							        LinearLayout ls = new LinearLayout(hint3.getContext());
							        ls.setOrientation(LinearLayout.VERTICAL);
							        final EditText et4 = new EditText(hint3.getContext());
							        TextView tv4 = new TextView(hint3.getContext());
							        tv4.setText("Enter Available balance ");
							        tv4.setTextSize(30);
							        Button b2 = new Button(hint3.getContext());
							        b2.setText("Update");
							        b2.setOnClickListener(new View.OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											if(et4.getText().toString()!=null )
											{
												 
												try {
													FileOutputStream fos = new FileOutputStream(f);
													OutputStreamWriter osw = new OutputStreamWriter(fos);
													osw.write(et4.getText().toString()+"\n");
													osw.close();
													fos.close();
													Intent i23 = new Intent(Starter.this,ExpenseManager.class);
													startActivity(i23);
													hint3.dismiss();
													
													
												} catch (FileNotFoundException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
											}
											else
												Toast.makeText(getApplicationContext(), "Fill field", Toast.LENGTH_LONG).show();
										}
									});
							        ls.addView(tv4);
							        ls.addView(et4);
							        ls.addView(b2);
							        hint3.setView(ls);
							        hint3.show();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							else
							{
								Intent i2 = new Intent(this,ExpenseManager.class);
								startActivity(i2);
								
							}
							break;
		}
	}

}
