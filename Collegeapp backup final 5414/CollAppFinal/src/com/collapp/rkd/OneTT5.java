package com.collapp.rkd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class OneTT5 extends Activity {
	
	
	EditText ip[][];
	TextView days[]=new TextView[5];
	String tt[][];
	FileInputStream fis;
	ObjectInputStream ois;
	FileOutputStream fos;
	ObjectOutputStream oos;
	Button edit,save;
	int numperiods;
	int parentWidth;
	int parentHeight;
	AlertDialog.Builder dialogforinvalidsubject;
	String subList="";
	static boolean written=false;
	boolean proceed=false;
	int colorlist[];
	String[] subs;
	boolean firstTime=false;
	TableRow.LayoutParams params; 
	TableLayout table;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onett5);
        edit=(Button)findViewById(R.id.editBut);
        save=(Button)findViewById(R.id.saveBut);
        setListeners();
        parentWidth=getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        
        System.out.println("Confirm Initialised");
        params = new TableRow.LayoutParams(parentWidth/7, 100);
        table =(TableLayout)findViewById(R.id.tableLayout1);
        fileread();
   }

    void setListeners()
    {
    	save.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			
 				 public void onClick(View view)
 		            {
 						System.out.println("SAVE");
 						proceed=true;
 						System.out.println(1);
 						int i,j;
 						for(i=0;i<numperiods;i++)
 						{
 							for(j=0;j<=5;j++)
 								tt[i][j]="";
 						}
 						System.out.println(2);
 						for( i=0;i<numperiods;i++)
 						{
 							
 							for(j=0;j<=5;j++)
 							{
 								tt[i][j]=ip[i][j].getText().toString();
 								if(j!=0&&tt[i][j].equals("")){tt[i][j]="FREE:)";}
 								System.out.println(3);
 								if(j!=0&&!tt[i][j].equals("FREE:)")&&!subList.contains(tt[i][j]))
 								{
 									proceed=false;
 									
 									//Insert P's code for the warning dialog box with message
 									//tt[i][j] is not present in the list of course titles
 									invalidCourseTitleError(i,j);
 								}
 								System.out.print(tt[i][j]+i+j+" ");
 								
 							}
 							System.out.println();
 						}
 						System.out.println(4);
 						if(proceed)
 						{
 							System.out.println(2);
 							writeTimeTable();
 							System.out.println(3);
 							updateDisplay();System.out.println(4);
 						}
 					}
			});
    	edit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		
			 public void onClick(View view)
	         {
				table.removeAllViews();
				
				for (int i = 0; i < numperiods; i++) {
					TableRow row = new TableRow(OneTT5.this);
					for (int j = 0; j <=5; j++) {
						ip[i][j]=new EditText(OneTT5.this);
						ip[i][j].setLayoutParams(params);
						ip[i][j].setText(tt[i][j]);
						ip[i][j].setTextSize(10);
						//ip[i][j].setClickable(true);
						//ip[i][j].setCursorVisible(true);
						//ip[i][j].setFocusable(true);
						//ip[i][j].setFocusableInTouchMode(true);
						row.addView(ip[i][j]);                
					}
					table.addView(row);
				}
			}
		});
    }
    
    void writeColors()
    {
    	colorlist=new int[subs.length+1];
        
        for(int i=0;i<=subs.length;i++)
        {
        	if(i==0)
        		colorlist[i]=Color.YELLOW;
        	if(i==1)
        		colorlist[i]=Color.CYAN;
        	if(i==2)
        		colorlist[i]=Color.GRAY;
        	if(i==3)
        		colorlist[i]=Color.LTGRAY;
        	if(i==4)
        		colorlist[i]=Color.RED;
        	if(i==5)
        		colorlist[i]=Color.MAGENTA;
        	if(i==6)
        		colorlist[i]=Color.GREEN;
        	if(i==7)
        		colorlist[i]=Color.BLUE;
        	if(i<subs.length)
        	System.out.println(subs[i]);
        }
    
    }
    
    void invalidCourseTitleError(int i,int j)
    {
    		dialogforinvalidsubject=new AlertDialog.Builder(OneTT5.this);
			dialogforinvalidsubject.setTitle("ERROR");
			System.out.println("DAYS"+days);
			dialogforinvalidsubject.setMessage(tt[i][j]+" is not a valid course title"+"\nList of Valid Courses\n"+subList);
			dialogforinvalidsubject.setCancelable(false);
			dialogforinvalidsubject.setNegativeButton("Close", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int arg1) 
				{
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			AlertDialog alertDialog = dialogforinvalidsubject.create();
			alertDialog.show();
			System.out.println("ERROR i= "+i+" j= "+j);
    }
    
    void updateDisplay()
    {
    	table.removeAllViews();
    	for (int i = 0; i < numperiods; i++) {
            TableRow row = new TableRow(this);
            
            for (int j = 0; j <=5; j++) {
            	ip[i][j]=new EditText(OneTT5.this);
            	ip[i][j].setLayoutParams(params);
            	ip[i][j].setText(tt[i][j]);
            	ip[i][j].setTextSize(10);
            	ip[i][j].setClickable(false);
            	ip[i][j].setCursorVisible(false);
            	ip[i][j].setFocusable(false);
            	ip[i][j].setFocusableInTouchMode(false);
            	int flag=0;
            	for(int p=0;p<subs.length;p++)
            	{
            		if(tt[i][j].equals(subs[p]))
            		{
            			flag=1;
            			ip[i][j].setBackgroundColor(colorlist[p]);
            			break;
            		}
            	}
            	if(flag==0)
        		{
        			ip[i][j].setBackgroundColor(Color.BLACK);
        			ip[i][j].setTextColor(Color.WHITE);
        		}
            	row.addView(ip[i][j]);                
            }
            table.addView(row);
        }
    	
    }
    
    void writeTimeTable()
    {
    	try {
    		
    		File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"timetable.txt");
        	f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"timetable.txt");
    		fos=new FileOutputStream(f);
    		oos = new ObjectOutputStream(fos);
			System.out.println("ObjectOPStream created");
			oos.writeObject(tt);
			System.out.println("timetable Written");
			oos.close();
			fos.close();
			System.out.println("file1 closed");
			written=true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    void fileread()
    {
    	try {
			File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
			if(!f.exists())
				f.mkdirs();
			f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"Periods.txt");
    		fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
    		String rkd = "";
			while((rkd=bfr.readLine())!=null)
			numperiods=Integer.valueOf(rkd);
			bfr.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ip=new EditText[numperiods][6];
        
        try {
        	File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table");
			if(!f.exists())
				f.mkdirs();
			f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"sListString.txt");
    		fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
    		String rkd = "";
			while((rkd=bfr.readLine())!=null)
			subList+=rkd;
			bfr.close();
			fis.close();
			/*
			fis = openFileInput("sListString.txt");
			ois=new ObjectInputStream(fis);
			subList=(String)ois.readObject();
			fis.close();
			System.out.println("file1 closed");
			*/
			System.out.println("SubList"+subList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"SubList.txt");
        	f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"SubList.txt");
    		fis = new FileInputStream(f);
			ois=new ObjectInputStream(fis);
	        subs=(String[])ois.readObject();
	        ois.close();
	        fis.close();
	        System.out.println("READ String Array");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        writeColors();
        
        try {
        	File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"timetable.txt");
        	f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"timetable.txt");
    		fis = new FileInputStream(f);
			ois=new ObjectInputStream(fis);
			tt=(String[][])ois.readObject();
			ois.close();
			fis.close();
			System.out.println("file1 closed");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FIRST TIME");
			tt=new String[numperiods][6];
			firstTime=true;
			firstTime();
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(firstTime!=true)
        {
        	displayTT();
        }
        
    }
    
    void displayTT()
    {
    	 System.out.println("both layouts initialised");
        
    	 for (int i = 0; i < numperiods; i++) {
            TableRow row = new TableRow(this);
            
            for (int j = 0; j <=5; j++) {
            	ip[i][j]=new EditText(this);
            	ip[i][j].setLayoutParams(params);
            	ip[i][j].setText(tt[i][j]);
            	ip[i][j].setTextSize(10);
            	ip[i][j].setClickable(false);
            	ip[i][j].setCursorVisible(false);
            	ip[i][j].setFocusable(false);
            	ip[i][j].setFocusableInTouchMode(false);
            	int flag=0;
            	for(int p=0;p<subs.length;p++)
            	{
            		if(tt[i][j].equals(subs[p]))
            		{
            			flag=1;
            			ip[i][j].setBackgroundColor(colorlist[p]);
            			break;
            		}
            	}
            	if(flag==0)
        		{
        			ip[i][j].setBackgroundColor(Color.BLACK);
        			ip[i][j].setTextColor(Color.WHITE);
        		}
            	row.addView(ip[i][j]);                
            }
            table.addView(row);
        }
    	
    }
    
    void firstTime()
    {
    	 

         for (int i = 0; i < numperiods; i++) {
             TableRow row = new TableRow(this);
             
             for (int j = 0; j <=5; j++) {
             	if(j==0)
             	{
             		ip[i][j]=new EditText(this);
                 	ip[i][j].setLayoutParams(params);
                 	ip[i][j].setHint("Time"+i);
                 	ip[i][j].setTextSize(10);
                 	//row.addView(ip[i][j]);
                 }
             	else
             	{
             	ip[i][j]=new EditText(this);
             	ip[i][j].setLayoutParams(params);
             	ip[i][j].setHint("class"+i);
             	ip[i][j].setTextSize(10);
             	}
             	row.addView(ip[i][j]);                
             }
             table.addView(row);
         }
         
    }
    
}
