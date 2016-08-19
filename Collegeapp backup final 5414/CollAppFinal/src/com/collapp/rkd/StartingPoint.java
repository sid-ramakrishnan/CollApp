package com.collapp.rkd;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.*;
public class StartingPoint extends Activity 
{

	EditText nDaysIP;//the textbox to input no. of days
	EditText nPeriodsIP;//the textbox to input no. of periods in a day
	EditText noSubIP;//the textbox to input no. of subjects in the current semester
	EditText subListIP;//the textbox to input the list of subjects
	Button next;//the button used to go to next page
	EditText noCourseDays;//the total no. of days for the course
	EditText maxAttPercentage;
	
	FileOutputStream fos;
	FileInputStream fis;
	ObjectOutputStream oos;
	AlertDialog.Builder dialogformaxperiods;
	AlertDialog.Builder dialogfornoofdays;
	AlertDialog.Builder dialogfornoofsubs;
	AlertDialog.Builder dialogformaxcourse;
	AlertDialog.Builder dialogfornumformat;
	static int notify=10;
	int flag=-99;
	boolean proceed=true;
	
	public static int days,periods,noSubs,noCrsDays,attPercentage;
	static String temp;
	static String subList[];
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_point);
        next=(Button)findViewById(R.id.nextBut);
        nDaysIP   = (EditText)findViewById(R.id.noDayInput);
        nPeriodsIP   = (EditText)findViewById(R.id.noPeriodInput);
        noSubIP= (EditText)findViewById(R.id.noSubInput);
        subListIP=(EditText)findViewById(R.id.subListInput);
        noCourseDays=(EditText)findViewById(R.id.maxClassIP);
        maxAttPercentage=(EditText)findViewById(R.id.ipAttPer);
        next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			
				 public void onClick(View view)
		            {
						proceed=true;
						try
						{
							days=Integer.valueOf(nDaysIP.getText().toString());
						    periods=Integer.valueOf(nPeriodsIP.getText().toString());
						    noSubs=Integer.valueOf(noSubIP.getText().toString());
						    noCrsDays=Integer.valueOf(noCourseDays.getText().toString());
						    temp=subListIP.getText().toString();
						    attPercentage=Integer.valueOf(maxAttPercentage.getText().toString());
						}  catch (Exception e) 
						{
							proceed=false;
							dialogfornumformat=new AlertDialog.Builder(StartingPoint.this);
					        dialogfornumformat.setTitle("ERROR");
							dialogfornumformat.setMessage("Number format exception,please enter a number");
			        		dialogfornumformat.setCancelable(false);
			        		dialogfornumformat.setNegativeButton("Close", new DialogInterface.OnClickListener() 
							{
								@Override
								public void onClick(DialogInterface dialog, int arg1) 
								{
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
						
						 AlertDialog alertDialog = dialogfornumformat.create();
						 alertDialog.show();	// TODO Auto-generated catch block
						}
						
				    if(proceed)
				    {		
						dialogformaxperiods=new AlertDialog.Builder(StartingPoint.this);
				        dialogformaxperiods.setTitle("ERROR");
				    
				    	if(periods>8)
						{
				        	//INsert P's code with msg max 8 periods 
							dialogformaxperiods.setMessage("Max 8 periods");
							dialogformaxperiods.setCancelable(false);
							dialogformaxperiods.setNegativeButton("Close", new DialogInterface.OnClickListener() 
							{
								@Override
								public void onClick(DialogInterface dialog, int arg1) 
								{
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
						
						 AlertDialog alertDialog = dialogformaxperiods.create();
						 alertDialog.show();
						}
				        else
				        {
				        	subList=new String[noSubs];
				        	int j=0;
				        	for(j=0;j<noSubs;j++)
				        		subList[j]="";
				        	
				        	
				        	j=0;int count=0;
				        	for(int i=0;i<temp.length();i++)
				        	{
				        		Character a=temp.charAt(i);
				        		if(a.toString().equals(" "))
				        		{
				        			count++;
				        		}
				        	}
				        	
				        	dialogfornoofsubs=new AlertDialog.Builder(StartingPoint.this);
				        	dialogfornoofsubs.setTitle("ERROR");
					        
				        	if(count!=noSubs-1)
				        	{
				        		//Insert P's code with message no. of subjects dont match entries
				        		proceed=false;
				        		dialogfornoofsubs.setMessage("No. of Subjects dont match entries");
				        		dialogfornoofsubs.setCancelable(false);
				        		dialogfornoofsubs.setNegativeButton("Close", new DialogInterface.OnClickListener() 
								{
									@Override
									public void onClick(DialogInterface dialog, int arg1) 
									{
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
							
							 AlertDialog alertDialog = dialogfornoofsubs.create();
							 alertDialog.show();
							}
				
				        	if(!(noCrsDays>0&&noCrsDays<=60))
				        	{
				        		proceed=false;
				        		dialogformaxcourse=new AlertDialog.Builder(StartingPoint.this);
				        		dialogformaxcourse.setTitle("ERROR");
								System.out.println("DAYS"+days);
								dialogformaxcourse.setMessage("Total Course Days should lie in 0-60");
								dialogformaxcourse.setCancelable(false);
								dialogformaxcourse.setNegativeButton("Close", new DialogInterface.OnClickListener() 
								{
									@Override
									public void onClick(DialogInterface dialog, int arg1) 
									{
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
				        		AlertDialog alertDialog = dialogformaxcourse.create();
				        		alertDialog.show();
						
				        	}
				        	if(proceed)
				        	{
				            	if(days==5||days==6)
					        	{
					        		j=0;
						        	for(int i=0;i<temp.length();i++)
						        	{
						        		Character a=temp.charAt(i);
						        		if(a.toString().equals(" "))
						        		{
						        			j++;System.out.println(subList[j]);
						        			continue;
						        		}
						        		subList[j]+=a.toString();
						        	}
						        	
					        		
					        		System.out.println("PRINTING SUBLIST");
									for(j=0;j<subList.length;j++)
										System.out.println(subList[j]);
					
									
									
								
									try {
										File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
										if(!f.exists())
											f.mkdirs();
										f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"AttPer.txt");
										if(!f.exists())
											f.createNewFile();
										fos = new FileOutputStream(f);
										//fos.write(periods);
										BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(fos));
										bfr.write(String.valueOf(attPercentage));
										bfr.close();
										fos.close();
										
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									try {
									File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
									if(!f.exists())
										f.mkdirs();
									f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"Periods.txt");
									if(!f.exists())
										f.createNewFile();
									fos = new FileOutputStream(f);
									//fos.write(periods);
									BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(fos));
									bfr.write(String.valueOf(periods));
									bfr.close();
									fos.close();
									/*fos = openFileOutput("Periods.txt",Context.MODE_PRIVATE);
									System.out.println("file1 created");
									fos.write(periods);
									System.out.println("Data1 Written");
									fos.close();
									System.out.println("file1 closed");
									*/
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					
									try {
									File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
									if(!f.exists())
										f.mkdirs();
									f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"Days.txt");
									if(!f.exists())
										f.createNewFile();
									fos = new FileOutputStream(f);
									//fos.write(days);
									BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(fos));
									bfr.write(String.valueOf(days));
									bfr.close();
									fos.close();
									/*
									fos = openFileOutput("Days.txt",Context.MODE_PRIVATE);
									System.out.println("file1 created");
									fos.write(days);
									System.out.println("Data1 Written");
									fos.close();
									System.out.println("file1 closed");
									*/
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
											
								try {
									File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
									if(!f.exists())
										f.mkdirs();
									f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"MaxCourseIP.txt");
									if(!f.exists())
										f.createNewFile();
									fos = new FileOutputStream(f);
									//fos.write(noCrsDays);
									BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(fos));
									bfr.write(String.valueOf(noCrsDays));
									bfr.close();
									fos.close();
									/*fos = openFileOutput("MaxCourseIP.txt",Context.MODE_PRIVATE);
									System.out.println("file1 created");
									fos.write(noCrsDays);
									System.out.println("Data1 Written");
									fos.close();
									System.out.println("file1 closed");
									*/
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
								
								try {
									File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
									if(!f.exists())
										f.mkdirs();
									f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"sListString.txt");
									if(!f.exists())
										f.createNewFile();
									fos = new FileOutputStream(f);
									BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(fos));
									bfr.write(temp);
									bfr.close();
									fos.close();
									/*fos = openFileOutput("sListString.txt",Context.MODE_PRIVATE);
									oos = new ObjectOutputStream(fos);
									System.out.println("ObjectOPStream created");
									oos.writeObject(temp);
									System.out.println("temp Written");
									oos.close();
									fos.close();
									System.out.println("file1 closed");
									*/
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								try {
									File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
									if(!f.exists())
										f.mkdirs();
									f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"SubList.txt");
									if(!f.exists())
										f.createNewFile();
									fos = new FileOutputStream(f);
									oos = new ObjectOutputStream(fos);
									System.out.println("ObjectOPStream created");
									oos.writeObject(subList);
									System.out.println("sublist Written");
									oos.close();
									fos.close();
									System.out.println("sublist closed");
									
									
									/*fos = openFileOutput("SubList.txt",Context.MODE_PRIVATE);
									oos = new ObjectOutputStream(fos);
									System.out.println("ObjectOPStream created");
									oos.writeObject(subList);
									System.out.println("sublist Written");
									oos.close();
									fos.close();
									System.out.println("sublist closed");
									*/
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
								Intent appMenu=new Intent(StartingPoint.this,AppMenu.class);
								startActivity(appMenu);
								
								/*if(days==5)
								{
									notify=10;
									Intent tt5=new Intent(StartingPoint.this,TimeTable5day.class);
									startActivity(tt5);
								}
								else if(days==6)
								{
									notify=10;
									Intent tt6=new Intent(StartingPoint.this,TimeTable6day.class);
									startActivity(tt6);
								}*/
					}
					        	
					        	
					        	
								
					        	else
								{   
					        		dialogfornoofdays=new AlertDialog.Builder(StartingPoint.this);
					        		dialogfornoofdays.setTitle("ERROR");
									System.out.println("DAYS"+days);
									dialogfornoofdays.setMessage("No. of days should be 5 or 6");
					        		dialogfornoofdays.setCancelable(false);
					        		dialogfornoofdays.setNegativeButton("Close", new DialogInterface.OnClickListener() 
									{
										@Override
										public void onClick(DialogInterface dialog, int arg1) 
										{
											// TODO Auto-generated method stub
											dialog.cancel();
										}
									});
					        		AlertDialog alertDialog = dialogfornoofdays.create();
					        		alertDialog.show();
								}
				        	}
				
						
				
				       }
		            }
		            }    
		            		
		});
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starting_point, menu);
        return true;
    }
    
}
