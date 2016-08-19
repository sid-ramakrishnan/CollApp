package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class ViewReminder extends ReminderActivity {

	String dispfinals="";
	FileInputStream fis;
	ObjectInputStream ois;
	 protected void onCreate(Bundle savedInstanceState) 
	  {
		// System.out.println("REACHED REMINDER");
	        super.onCreate(savedInstanceState);
	      //System.out.println("REACHED REMINDER");
	        setContentView(R.layout.viewreminder);
	        TextView see = (TextView)findViewById(R.id.thasall2);
	        	        
	     		try {
	     			
	     		   String path = Environment.getExternalStorageDirectory()+File.separator+"CollApp";
	     			File f = new File(path);
	     		    f.mkdirs();
	     		    path+=File.separator+"msg2.txt";
	     		    f = new File(path); 
	     		    if(!f.exists())
	     		    	return;
	     		   FileInputStream fis = new FileInputStream(f);
	     		   BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
	     		   String red = "";
	     		   while((red=bfr.readLine())!=null)
	     		   {
	     			  dispfinals+=red+"\n";
	     		   }
	     		  // ObjectInputStream ois=new ObjectInputStream(fis);
	     		   //dispfinals=(String)ois.readObject();
	     		   bfr.close();
	     		   fis.close();
	     		  see.setText(dispfinals);
	     		} catch (FileNotFoundException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		} catch (StreamCorruptedException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		} catch (IOException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		}  catch (NullPointerException e){
					e.printStackTrace();
				}
	     		
	     		
	     	}	
	         
}	
	 