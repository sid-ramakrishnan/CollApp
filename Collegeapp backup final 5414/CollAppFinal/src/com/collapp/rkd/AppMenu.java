package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppMenu extends Activity implements OnClickListener
{     ImageView accesstimetable,accessbunkmanager,accessexpensemanager,accessmemo,accessreminder;
      Intent switchtobunkmanagermenu,switchtotimetable; 
      TextView MHText,EText,TTText,BMText,RText,MText;
	  boolean firstTime=false;
	
	
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.appmenu);
	        
	        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Bangers.ttf");
	        MHText=(TextView)findViewById(R.id.textViewforappmenu);
	        MHText.setTypeface(typeface);
	        EText=(TextView)findViewById(R.id.textExpenseManager);
	        EText.setTypeface(typeface);
	        TTText=(TextView)findViewById(R.id.textTimeTable);
	        TTText.setTypeface(typeface);
	        BMText=(TextView)findViewById(R.id.textBunkManager);
	        BMText.setTypeface(typeface);
	        RText=(TextView)findViewById(R.id.textReminder);
	        RText.setTypeface(typeface);
	        MText=(TextView)findViewById(R.id.textMemos);
	        MText.setTypeface(typeface);
	        accesstimetable = (ImageView)findViewById(R.id.buttontoaccesstimetable);
	        accessbunkmanager = (ImageView)findViewById(R.id.buttontoaccessbunkmanager);
	        accessexpensemanager = (ImageView)findViewById(R.id.buttontoaccessexpressmanager);
	        accessmemo = (ImageView)findViewById(R.id.buttontoaccessmemo);
	        accessreminder=(ImageView)findViewById(R.id.buttontoaccessreminder);
	        
	        accessbunkmanager.setOnClickListener(new View.OnClickListener() 
	        {

				@Override
				public void onClick(View arg0) 
				{
					String sublist;
					try {
		        		FileInputStream	fis = new FileInputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"sListString.txt"));
		        		BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
		        		String sso ="";
		        		String read="";
		        		while((sso=bfr.readLine())!=null)
		        			read+=sso+"\n";
		        		bfr.close();
		        		fis.close();
		     		} catch (FileNotFoundException a) {
		     			// TODO Auto-generated catch block
		     			firstTime=true;
		     			System.out.println("File not found");
		     			a.printStackTrace();
		     		} catch (IOException b) {
		     			// TODO Auto-generated catch block
		     			b.printStackTrace();
		     		} 
					
					if(firstTime)
					{
						Intent questionAnswer=new Intent(AppMenu.this,StartingPoint.class);
						startActivity(questionAnswer);
					}
					else
					{
						switchtobunkmanagermenu = new Intent(AppMenu.this,BunkManagermenu.class);
						System.out.println("Intent created");
						startActivity(switchtobunkmanagermenu);
						System.out.println("Started Activity");
					}
					
				}
	        	
	        });
	        accessreminder.setOnClickListener(new View.OnClickListener() 
	        {

				@Override
				public void onClick(View arg0) 
				{
					Intent switchtoreminder = new Intent(AppMenu.this,ReminderActivity.class);
					System.out.println("Intent reminder created");
					startActivity(switchtoreminder);
					System.out.println("Started reminder Activity");
				}
	        	
	        });
	        
	        accessmemo.setOnClickListener(new View.OnClickListener() 
	        {

				@Override
				public void onClick(View arg0) 
				{
					Intent switchtomemo = new Intent(AppMenu.this,NotesList.class);
					System.out.println("Intent reminder created");
					startActivity(switchtomemo);
					System.out.println("Started reminder Activity");
				}
	        	
	        });
	        accessexpensemanager.setOnClickListener(new View.OnClickListener() 
	        {

				@Override
				public void onClick(View arg0) 
				{
					String path4=Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" ;
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
							final AlertDialog hint3 =new AlertDialog.Builder(AppMenu.this).create();
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
											Intent i23 = new Intent(AppMenu.this,ExpenseManager.class);
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
						Intent i2 = new Intent(AppMenu.this,ExpenseManager.class);
						startActivity(i2);
						
					}
				
				}
	        	
	        });

	        accesstimetable.setOnClickListener(new View.OnClickListener() 
	        {
	        	@Override
				public void onClick(View arg0) 
				{
					int days = 0;
					try {
						File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"Days.txt");
						
		        		FileInputStream	fis = new FileInputStream(f);
		        		BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
		        		String sew ="";
		        		String swe = "";
		        		while((sew=bfr.readLine())!=null)
		        			swe+=sew;
		        		bfr.close();
		        		fis.close();
		     			days=Integer.valueOf(swe);
		     			System.out.println("DAYS="+fis.read());
		     			//fis.close();
		     			System.out.println("file1 closed");
		     			
		     		} catch (FileNotFoundException a) {
		     			// TODO Auto-generated catch block
		     			firstTime=true;
		     			System.out.println("File not found");
		     			a.printStackTrace();
		     		} catch (IOException b) {
		     			// TODO Auto-generated catch block
		     			b.printStackTrace();
		     		}
					if(firstTime)
					{
						Intent questionAnswer=new Intent(AppMenu.this,StartingPoint.class);
						startActivity(questionAnswer);
					}
					if(days==5)
					{
						switchtotimetable = new Intent(AppMenu.this,OneTT5.class);
						startActivity(switchtotimetable);
					}
					if(days==6)
					{
						switchtotimetable = new Intent(AppMenu.this,OneTT6.class);
						startActivity(switchtotimetable);
					}
				}
	        	
	        });
	        
	  }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) 
	    {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        //getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }


		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
		}
