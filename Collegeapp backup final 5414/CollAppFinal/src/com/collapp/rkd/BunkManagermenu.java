package com.collapp.rkd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class BunkManagermenu extends Activity 
{   static Button bunkmanageroptions[];
	Button trying;
    static int maxnoofsubjects;
    static LinearLayout layout1;
    static String filename;
    FileOutputStream fos;
	FileInputStream fis;
	File f;
	String[] sublist;
	int colorList[]=new int[10];
	int parentHeight;
	ObjectOutputStream oos;
	Intent startrespectivesubject;
	protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        parentHeight=getApplicationContext().getResources().getDisplayMetrics().heightPixels;
	        setContentView(R.layout.bunkmanagermenu);
	        fileread();
	        int i = 0;
	        colorList[0]=Color.RED;
	        colorList[1]=Color.YELLOW;
	        colorList[2]=Color.CYAN;
	        colorList[3]=Color.GREEN;
	        colorList[4]=Color.rgb(205,92,92);
	        colorList[5]=Color.BLUE;
	        colorList[5]=Color.MAGENTA;
	        colorList[6]=Color.rgb(255,69,0);
	        colorList[7]=Color.rgb(152,251,152);
	        colorList[8]=Color.rgb(218,112,214);
	        
		       maxnoofsubjects = sublist.length;
		       bunkmanageroptions = new Button[maxnoofsubjects];
			   layout1 = (LinearLayout)findViewById(R.id.linearlayoutbunkmanagermenu);
			   int k=maxnoofsubjects+2;
			   for(i=0;i<maxnoofsubjects;i++)
			   {
			    bunkmanageroptions[i]=new Button(this);
	           	bunkmanageroptions[i].setText(sublist[i]);
	           	bunkmanageroptions[i].setWidth(100);
	           	bunkmanageroptions[i].setHeight(parentHeight/k);
	           	bunkmanageroptions[i].setBackgroundColor(colorList[i]);
			    layout1.addView(bunkmanageroptions[i]);
			    bunkmanageroptions[i].setOnClickListener(getOnClickDoSomething(bunkmanageroptions[i]));
			   }
	  }
	
	void fileread()
	{
		try {
			File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"SubList.txt");
			fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			sublist=(String[])ois.readObject();
			ois.close();
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	View.OnClickListener getOnClickDoSomething(final Button button)  
	{
	    return new View.OnClickListener() 
	    {
	        public void onClick(View v) 
	        {
	    
	        	/*int temp[]={0,0,0,0,0,0};
	           filename = button.getText().toString();
	           try {
				fos=new FileOutputStream(filename+".txt");
				  oos=new ObjectOutputStream(fos);
		           oos.writeObject(temp);
		           oos.close();
		           fos.close();
		       } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	        	filename = button.getText().toString();
	        	startrespectivesubject=new Intent(BunkManagermenu.this,BunkManager.class);
	           startActivity(startrespectivesubject);
	        	   
	        }
	        	   
	           
	     };
	    
	}
	    
	    private void init() 
	    {
		  
		
	    }


		@Override
	    public boolean onCreateOptionsMenu(Menu menu) 
	    {
	        // Inflate the menu; this adds items to the action bar if it is present.
	      //  getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }


		
}
