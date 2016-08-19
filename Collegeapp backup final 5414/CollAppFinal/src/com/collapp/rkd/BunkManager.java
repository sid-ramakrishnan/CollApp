package com.collapp.rkd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class BunkManager extends Activity implements OnClickListener 
{
	
	public String coursename;
	int attcounter=0;
    int bunkcounter=0;
    int tallycounter = 1;
    int btallycounter=1;
    float attpercentage = 0.0f;
    int conductedcounter=0;
    int bflag = 0;
    int undocount = 0;
    Button attended;
    Button bunked;
    Button bundo;
    Button breset;
    TextView displayattended;
    TextView displaybunked;
    TextView displayconducted;
    TextView displaySub;
    TextView displaypercentage;
    ImageView[] numberattended;
    ImageView[] numberbunked;
    int maxclasses;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    Scanner scan = new Scanner(System.in);
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog.Builder clickedundomorethanonce;
    FileOutputStream fos;
    FileInputStream fis;
    BufferedWriter writer;
    int extractedfromfile[];
    int importtofile[];
    BufferedReader reader;
    int flag=-999;
    int atally,btally;
    int inittallies;
    String filePath="";
    int requiredAtt;
    
    void init() throws IOException
    {
    	attended = (Button)findViewById(R.id.bAttended);
        bunked = (Button)findViewById(R.id.bBunked);
        bundo = (Button)findViewById(R.id.bundo);
        breset = (Button)findViewById(R.id.buttontoreset);
        displayattended=(TextView)findViewById(R.id.textviewfornoofclassesattended);
        displaySub=(TextView)findViewById(R.id.textviewforsubject);
        displaybunked=(TextView)findViewById(R.id.textviewfornoofclassesbunked);
        displayconducted=(TextView)findViewById(R.id.textviewfornoofclassesconducted);
        displaypercentage=(TextView)findViewById(R.id.textviewforattendedpercentage);
        numberattended = new ImageView[20];        
        numberbunked = new ImageView[20];
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayoutattended);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayoutbunked);
        displaySub.setText(BunkManagermenu.filename);
        displayconducted.setText("No of classes conducted :"+ conductedcounter);
		displayattended.setText("No of classes attended :"+ attcounter);
		displaybunked.setText("No of classes bunked : "+bunkcounter);
		attpercentage = 0 ;
		displaypercentage.setText("Attendance percentage :"+ attpercentage);
		
		try {
			File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"MaxCourseIP.txt");
			
    		FileInputStream	fis = new FileInputStream(f);
    		BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
    		String sew ="";
    		String swe = "";
    		while((sew=bfr.readLine())!=null)
    			swe+=sew;
    		bfr.close();
    		fis.close();
    		maxclasses=Integer.valueOf(swe);
 			System.out.println("MAXCLASSES="+maxclasses);
 			//fis.close();
 			System.out.println("file1 closed");
 			
 		} catch (FileNotFoundException a) {
 			// TODO Auto-generated catch block
 			System.out.println("File not found");
 			a.printStackTrace();
 		} catch (IOException b) {
 			// TODO Auto-generated catch block
 			b.printStackTrace();
 		}
		
		try {
			File f = new File(Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"Time Table"+File.separator+"AttPer.txt");
			
    		FileInputStream	fis = new FileInputStream(f);
    		BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
    		String sew ="";
    		String swe = "";
    		while((sew=bfr.readLine())!=null)
    			swe+=sew;
    		bfr.close();
    		fis.close();
    		requiredAtt=Integer.valueOf(swe);
 			System.out.println("MAXCLASSES="+maxclasses);
 			//fis.close();
 			System.out.println("file1 closed");
 			
 		} catch (FileNotFoundException a) {
 			// TODO Auto-generated catch block
 			System.out.println("File not found");
 			a.printStackTrace();
 		} catch (IOException b) {
 			// TODO Auto-generated catch block
 			b.printStackTrace();
 		}
		
		if(maxclasses%5==0)
		inittallies = maxclasses/5;
		else
		inittallies = maxclasses/5 + 1;
        for( int i=0;i<inittallies;i++)
        {
         numberattended[i] = new ImageView(BunkManager.this);
        
         numberattended[i].setImageResource(R.drawable.ic_launcher);
         numberattended[i].setVisibility(View.INVISIBLE);
         numberattended[i].setMaxHeight(50);
         numberattended[i].setMaxWidth(50);
         linearLayout1.addView( numberattended[i]);
         
        }
        for( int i=0;i<inittallies;i++)
        {
         numberbunked[i] = new ImageView(BunkManager.this);
         numberbunked[i].setVisibility(View.INVISIBLE);
         numberbunked[i].setImageResource(R.drawable.ic_launcher);
         numberbunked[i].setMaxHeight(50);
         numberbunked[i].setMaxWidth(50);
         linearLayout2.addView( numberbunked[i]);
        }
        
        filePath = Environment.getExternalStorageDirectory()+File.separator+"CollApp"+File.separator+"BunkManager";
        File f = new File(filePath);
    	if(!f.exists())
    		f.mkdirs();
    	
           	 f=new File(filePath+File.separator+BunkManagermenu.filename+".txt");
    	   	if(f.exists())
    	   	{
    	   		System.out.println("F exists");
    	   		fileread();
    	   	}	
    	   	else
    	   	{
    	   		f.createNewFile();
    	   		
    	   	}
              attended.setOnClickListener(new View.OnClickListener() 
            {
    			
    			@Override
    			public void onClick(View arg0) 
    			{
    				// TODO Auto-generated method stub
    				 bflag =1;
    				 undocount = 1;
    				 conductedcounter++;
    				 alertDialogBuilder = new AlertDialog.Builder(BunkManager.this);
     				 alertDialogBuilder.setTitle("ERROR!!!!");
    			  if(conductedcounter>maxclasses)
    			  {
    				  conductedcounter = maxclasses;
    				     alertDialogBuilder.setMessage("Max classes have already been conducted");
     					 alertDialogBuilder.setCancelable(false);
     					 alertDialogBuilder.setNegativeButton("Close",new DialogInterface.OnClickListener() {
     						public void onClick(DialogInterface dialog,int id) {
     							// if this button is clicked, just close
     							// the dialog box and do nothing
     							dialog.cancel();
     						}
     					});
     					AlertDialog alertDialog =  alertDialogBuilder.create();
     					 
     					// show it
     					alertDialog.show();
     					
     					
     				}
    				  
    			  
    			  else
    			  {		  
    				 
    			     attcounter++;
    				 displayconducted.setText("No of classes conducted :"+ conductedcounter);
    				 displayattended.setText("No of classes attended :"+ attcounter);
    				 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    				 displaypercentage.setText("Attendance percentage :"+ attpercentage);
    				
    			  
    				if( attcounter%5==0)
    				{   
    					 
    					 numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    					 numberattended[ tallycounter-1].setImageResource(R.drawable.download);
    					 tallycounter++;
    					
    				}
    			   else if( attcounter%5==1)
    				{
    				   
    				    numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    				    numberattended[ tallycounter-1].setImageResource(R.drawable.tallymark1);
    					
    					
    				}
    			   else if( attcounter%5==2)
    			   {
    				   
    				    numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    				    numberattended[ tallycounter-1].setImageResource(R.drawable.tally2);
    				   
    			   }
    			   else if( attcounter%5==3)
    			   {
    				    numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    				    numberattended[ tallycounter-1].setImageResource(R.drawable.tally3);
    				   
    			   }
    			   else if( attcounter%5==4)
    			   {
    				 
    				    numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    				    numberattended[ tallycounter-1].setImageResource(R.drawable.tally4);
    				   
    			   }
    			}
    			}	  
    		});
             bunked.setOnClickListener(new View.OnClickListener() 
            {
    			
    			@Override
    			public void onClick(View arg0) 
    			{
    				// TODO Auto-generated method stub
    				 bflag =2;
    				 undocount = 1;
    				 conductedcounter++;
    				 if(conductedcounter>maxclasses)
       			     {
       				     conductedcounter = maxclasses;
       				     alertDialogBuilder.setMessage("Max classes have already been conducted");
        				 alertDialogBuilder.setCancelable(false);
        				 alertDialogBuilder.setNegativeButton("Close",new DialogInterface.OnClickListener() 
        				 {
        						public void onClick(DialogInterface dialog,int id) 
        						{
        							// if this button is clicked, just close
        							// the dialog box and do nothing
        							dialog.cancel();
        						}
        					});
        					AlertDialog alertDialog =  alertDialogBuilder.create();
        					 
        					// show it
        					alertDialog.show();
        					
        					
        	      }
    				 else
    				 {		 
    				 
    				 
    				 bunkcounter++;
    				 displayconducted.setText("No of classes conducted :"+ conductedcounter);
    				 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    				 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    				 displaypercentage.setText("Attendance percentage :"+ attpercentage);
    				 alertDialogBuilder = new AlertDialog.Builder(BunkManager.this);
    				 alertDialogBuilder.setTitle("WARNING!!!!");
    				if( attpercentage<=requiredAtt)
    				{
    					
    					 alertDialogBuilder.setMessage("You're below your minimum attendance requirement!!");
    					 alertDialogBuilder.setCancelable(false);
    					 alertDialogBuilder.setNegativeButton("Close",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {
    							// if this button is clicked, just close
    							// the dialog box and do nothing
    							dialog.cancel();
    						}
    					});
    					AlertDialog alertDialog =  alertDialogBuilder.create();
    					 
    					// show it
    					alertDialog.show();
    					
    					
    				}
    				
    				if( bunkcounter%5==0)
    				{   
    					 numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    					 numberbunked[ btallycounter-1].setImageResource(R.drawable.download);
    					 btallycounter++;
    					
    				}
    			   else if( bunkcounter%5==1)
    				{
    				    numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    				    numberbunked[ btallycounter-1].setImageResource(R.drawable.tallymark1);
    					
    					
    				}
    			   else if( bunkcounter%5==2)
    			   {
    				    numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    				    numberbunked[ btallycounter-1].setImageResource(R.drawable.tally2);
    				   
    			   }
    			   else if( bunkcounter%5==3)
    			   {
    				    numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    				    numberbunked[ btallycounter-1].setImageResource(R.drawable.tally3);
    				   
    			   }
    			   else if( bunkcounter%5==4)
    			   {
    				    numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    				    numberbunked[ btallycounter-1].setImageResource(R.drawable.tally4);
    				   
    			   }
    			}
    			}		 
    		});
        
    	
            bundo.setOnClickListener(new View.OnClickListener() 
            {
                
    			@Override
    			public void onClick(View arg0) 
    			{   
    				 clickedundomorethanonce = new AlertDialog.Builder(BunkManager.this);
    			     clickedundomorethanonce.setTitle("Can't undo anymore");
                     if( bflag == 1)
    				{
    				 // this means that the attended button had been pressed last
    				 if( undocount==1)
    				 { undocount = 0;
    				  if( attcounter%5==0)
    				  {
    					 tallycounter--;
    					 attcounter--;
    					 conductedcounter--;
                         numberattended[ tallycounter-1].setVisibility(View.INVISIBLE);
    					 numberattended[ tallycounter-1].setVisibility(View.VISIBLE);
    					 numberattended[ tallycounter-1].setImageResource(R.drawable.tally4);
    				     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    					 displayattended.setText("No of classes attended :"+ attcounter);
    					 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    					 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
    				  }
    				  else if( attcounter%5==1)
    				  {
    					 attcounter--;
    					 conductedcounter--;
    					 numberattended[ tallycounter-1].setVisibility(View.INVISIBLE);
    					 displayconducted.setText("No of classes conducted :"+ conductedcounter);
    					 displayattended.setText("No of classes attended :"+ attcounter);
    					 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    					 displaypercentage.setText("Attendance percentage :"+ attpercentage);
    					  
    					  
    				  }
    				  else if( attcounter%5==2)
    				  {
    					     attcounter--;
    						 conductedcounter--;
    						//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    						 numberattended[ tallycounter-1].setImageResource(R.drawable.tallymark1);
    					     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    						 displayattended.setText("No of classes attended :"+ attcounter);
    						 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    						 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
    					  
    					  
    				  }
    				  else if( attcounter%5==3)
    				  {
    					   attcounter--;
    						 conductedcounter--;
    						//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    						 numberattended[ tallycounter-1].setImageResource(R.drawable.tally2);
    					    displayconducted.setText("No of classes conducted :"+ conductedcounter);
    						 displayattended.setText("No of classes attended :"+ attcounter);
    						 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    						 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
    					  
    					  
    				  }
    				  else if( attcounter%5==4)
    				  {
    					     attcounter--;
    						 conductedcounter--;
    						//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    						 numberattended[ tallycounter-1].setImageResource(R.drawable.tally3);
    					     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    						 displayattended.setText("No of classes attended :"+ attcounter);
    						 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    						 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
    					  
    					  
    				  }
    				  
    				 }
    				 else
    				 {
    					  clickedundomorethanonce.setMessage("Can't undo any further");
                          clickedundomorethanonce.setCancelable(false);
    				      clickedundomorethanonce.setNegativeButton("Close",new DialogInterface.OnClickListener() {
    							public void onClick(DialogInterface dialog,int id) {
    								// if this button is clicked, just close
    								// the dialog box and do nothing
    								dialog.cancel();
    							}
    						});
    						AlertDialog alertDialog =  clickedundomorethanonce.create();
    						 
    						// show it
    						alertDialog.show();
    							 
    				 }
    				
    				
    				
    				}
    				else if( bflag == 2)
    				{
    					if( undocount==1)
    					 {
    						 undocount = 0;
    					  if( bunkcounter%5==0)
    					  {
    						 btallycounter--;
    						 bunkcounter--;
    						 conductedcounter--;
    	                     numberbunked[ btallycounter-1].setVisibility(View.INVISIBLE);
    					 numberbunked[ btallycounter-1].setVisibility(View.VISIBLE);
    						 numberbunked[ btallycounter-1].setImageResource(R.drawable.tally4);
    					     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    						 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    						 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    						 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
    					  }
    					  else if( bunkcounter%5==1)
    					  {
    						 bunkcounter--;
    					     conductedcounter--;
    						 numberbunked[ btallycounter-1].setVisibility(View.INVISIBLE);
    						 displayconducted.setText("No of classes conducted :"+ conductedcounter);
    						 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    						 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    						 displaypercentage.setText("Attendance percentage :"+ attpercentage);
    						  
    						  
    					  }
    					  else if( bunkcounter%5==2)
    					  {
    						     bunkcounter--;
    							 conductedcounter--;
    							//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    							 numberbunked[ btallycounter-1].setImageResource(R.drawable.tallymark1);
    						     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    							 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    							 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    							 displaypercentage.setText("Attendance percentage :"+ attpercentage);			  
       					  }
    					  else if( bunkcounter%5==3)
    					  {
    						    bunkcounter--;
    						   conductedcounter--;
    							//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    							 numberbunked[ btallycounter-1].setImageResource(R.drawable.tally2);
    						     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    							 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    							 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    							 displaypercentage.setText("Attendance percentage :"+ attpercentage);  
    					  }
    					  else if( bunkcounter%5==4)
    					  {
    						   bunkcounter--;
    							 conductedcounter--;
    							//numberattended[tallycounter-1].setVisibility(View.VISIBLE);
    							 numberbunked[ btallycounter-1].setImageResource(R.drawable.tally3);
    						     displayconducted.setText("No of classes conducted :"+ conductedcounter);
    							 displaybunked.setText("No of classes bunked :"+ bunkcounter);
    							 attpercentage = (float) attcounter/ conductedcounter * 100 ;
    							 displaypercentage.setText("Attendance percentage :"+ attpercentage);
    						  
    					  }
    					  
    					 }
    					 else
    					 {
    						Toast.makeText(getApplicationContext(), "Can't undo more than once", Toast.LENGTH_LONG).show();		 
    					 }
    	
    				}
    	
    }
            });
            breset.setOnClickListener(new View.OnClickListener() 
            {
				
				@Override
				public void onClick(View arg0) 
				{
					
			        
				conductedcounter = 0;
				attcounter = 0;
				bunkcounter = 0 ;
				tallycounter = 1;
				btallycounter = 1;
				bflag = 0;
				undocount = 0;
				importtofile = new int[7];
				//File f2 = new File(BunkManagermenu.filename+".txt");
				System.out.println(BunkManagermenu.filename+".txt");
				displayconducted.setText("No of classes conducted :"+ conductedcounter);
				displayattended.setText("No of classes attended :"+ attcounter);
				displaybunked.setText("No of classes bunked : "+bunkcounter);
				attpercentage = 0 ;
				displaypercentage.setText("Attendance percentage :"+ attpercentage);
				for( int i=0;i<inittallies;i++)
		        {
					numberattended[i].setVisibility(View.INVISIBLE);
		         
		        }
		        for( int i=0;i<inittallies;i++)
		        {
		        	numberbunked[i].setVisibility(View.INVISIBLE);
		        }
		        
				
				
				}
				
			});
            
     
       
}

	
	
    
    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	

		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}	      		
	
	
	private void fileread() throws IOException 
	{    int i = 0;
		int  buff[]=null;
		try
		{
			File f = new File(filePath+File.separator+BunkManagermenu.filename+".txt");
			FileInputStream fis= new FileInputStream(f);
			ObjectInputStream ois =new ObjectInputStream(fis);
			buff=(int[])ois.readObject();
		    ois.close();
		    fis.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attcounter=buff[0];
		bunkcounter=buff[1];
		tallycounter=buff[2];
		btallycounter=buff[3];
		conductedcounter=buff[4];
		bflag=buff[5];
		undocount=buff[6];
		
		
		displayconducted.setText("No of classes conducted :"+ conductedcounter);
		displayattended.setText("No of classes attended :"+ attcounter);
		displaybunked.setText("No of classes bunked : "+bunkcounter);
		attpercentage = (float) attcounter/ conductedcounter * 100 ;
		displaypercentage.setText("Attendance percentage :"+ attpercentage);
		
       System.out.println("now creating the image from previous");
       int numatt=0;
       int numbun=0;
       
       for( i=1;i<tallycounter;i++)
       { 
    	numberattended[i-1].setVisibility(View.VISIBLE);
		numberattended[i-1].setImageResource(R.drawable.download);
		numatt+=5;
       }
       numatt = attcounter - numatt;
       switch(numatt)
       {
       case 1:
    	numberattended[tallycounter-1].setVisibility(View.VISIBLE);
   		numberattended[tallycounter-1].setImageResource(R.drawable.tallymark1);   
       break;
       case 2:
    	numberattended[tallycounter-1].setVisibility(View.VISIBLE);
        numberattended[tallycounter-1].setImageResource(R.drawable.tally2);   
    	break;
       case 3:
    	numberattended[tallycounter-1].setVisibility(View.VISIBLE);
        numberattended[tallycounter-1].setImageResource(R.drawable.tally3);  
    	break;
       case 4:
    	numberattended[tallycounter-1].setVisibility(View.VISIBLE);
        numberattended[tallycounter-1].setImageResource(R.drawable.tally4);
    	break;
       case 5:
    	numberattended[tallycounter-1].setVisibility(View.VISIBLE);
      	numberattended[tallycounter-1].setImageResource(R.drawable.download);   
        break;
       
       
       
       
       }
       for( i=1;i<btallycounter;i++)
       { 
    	numberbunked[i-1].setVisibility(View.VISIBLE);
		numberbunked[i-1].setImageResource(R.drawable.download);
		numatt+=5;
       }
       numbun = bunkcounter - numbun;
       switch(numbun)
       {
       case 1:
    	numberbunked[btallycounter-1].setVisibility(View.VISIBLE);
   		numberbunked[btallycounter-1].setImageResource(R.drawable.tallymark1);   
       break;
       case 2:
    	numberbunked[btallycounter-1].setVisibility(View.VISIBLE);
        numberbunked[btallycounter-1].setImageResource(R.drawable.tally2);   
    	break;
       case 3:
    	numberbunked[btallycounter-1].setVisibility(View.VISIBLE);
        numberbunked[btallycounter-1].setImageResource(R.drawable.tally3);  
    	break;
       case 4:
    	numberbunked[btallycounter-1].setVisibility(View.VISIBLE);
        numberbunked[btallycounter-1].setImageResource(R.drawable.tally4);
    	break;
       case 5:
    	numberbunked[btallycounter-1].setVisibility(View.VISIBLE);
      	numberbunked[btallycounter-1].setImageResource(R.drawable.download);   
        break;
       
       
       
       
       }
       
    	   
			    
			    
			
		}
		
		
		
		
       
       
  
	
	
	
	
	

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		importtofile = new int[7];
		//File f2 = new File(BunkManagermenu.filename+".txt");
		System.out.println(BunkManagermenu.filename+".txt");
		
		try {
			System.out.println("Trying");
			File f = new File(filePath+File.separator+BunkManagermenu.filename+".txt");
			fos = new FileOutputStream(f);
			System.out.println("Trying2");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			importtofile[0] = attcounter;
			importtofile[1] = bunkcounter;
			importtofile[2] = tallycounter;
			importtofile[3] = btallycounter;
			importtofile[4] = conductedcounter;
			importtofile[5] = bflag;
			importtofile[6] = undocount;
			
			oos.writeObject(importtofile);
			oos.close();
			fos.close();
			
			
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("FNF");
			e.printStackTrace();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("IOE");
			e.printStackTrace();
		}
		
			System.out.println("created and written");
	}


	//@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		
	} 
	
	}  	

	