package com.collapp.rkd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class ExpenseManager extends Activity implements OnClickListener{

	String path1,path2,path3,path4;
	ArrayList<String>dates = new ArrayList();
	ArrayList<Integer>enterys = new ArrayList();
	ArrayList<String>cost = new ArrayList();
	ArrayList<String>reason = new ArrayList();
	TextView tvl;
	int counterfor ;
	LayoutParams lp;
	FrameLayout fl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InputStream strm = getResources().openRawResource(R.drawable.debit); // open debit.png
        Bitmap bmp = BitmapFactory.decodeStream(strm);// decoding image file debit.png
        InputStream strm2 = getResources().openRawResource(R.drawable.credit); // open  credit.png
        Bitmap bmp2 = BitmapFactory.decodeStream(strm2);  // decoding image file credit.png
		setContentView(R.layout.expense);
		counterfor=0;
		setContentView(R.layout.expense);
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		//if(counterfor!=1)
		//tabHost.clearAllTabs();
		// set 3 tabs detailed expense daily expense monthly expense
		TabSpec tab1 = tabHost.newTabSpec("Detailed");
        TabSpec tab2 = tabHost.newTabSpec("Daily");
        TabSpec tab3 = tabHost.newTabSpec("Monthly");
        tab1.setIndicator("Detailed");
        tab1.setContent(R.id.detailed);
        tab2.setIndicator("Daily");
        tab2.setContent(R.id.daily);
        tab3.setIndicator("Monthly");
        tab3.setContent(R.id.monthly);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
	}

		@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		counterfor++;
		tvl = (TextView) findViewById(R.id.balance);
		path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"Expenses";
		path3=path1+File.separator+"main.txt";
		path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"tempexpense.txt";
		path4=Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"amountleft.txt";
		readtempexpense(); //read temp expenses i.e expenses written in the widget
		currentexpensestatus(); // read from file having all expenses
		ImageButton ib = (ImageButton)findViewById(R.id.newexpense);
		ImageButton ib2 = (ImageButton)findViewById(R.id.credit);
		ib.setOnClickListener(this);
		ib2.setOnClickListener(this);

		updatebalance(); // updates current balance status
		LinearLayout detailed = (LinearLayout)findViewById(R.id.detailed);
		LinearLayout daily = (LinearLayout) findViewById(R.id.daily);
		LinearLayout monthly = (LinearLayout) findViewById(R.id.monthly);
		if(dates.size()>0)
		{
		setDetailed(dates.get(dates.size()-1));
		setDaily();
		setMonthly();
		}
		/*GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			      new GraphViewData(1, 2.0d)
			      , new GraphViewData(2, 1.5d)
		index	      , new GraphViewData(3, 2.5d)
			      , new GraphViewData(4, 1.0d)
			});

			GraphView graphView = new LineGraphView(this,"GraphViewDemo");
			graphView.addSeries(exampleSeries);
			LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
			layout.addView(graphView);
			*/
		}
	void readtempexpense()
	{
	     // read temp expenses, written using the widget
		File f = new File(path1);
		if(!f.exists())
			f.mkdirs();
		f = new File(path2);
		String datatowrite="";
		String datatoread="";
		String s="";
		try {
			int countsl =0;
			int scs =0;
			String lastdate ="";
			Integer sumsn =new Integer(0);
			FileInputStream fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			while((s=bfr.readLine())!=null )
				if(!s.equals(" "))
				{
				datatoread+=s+"\n";
				 countsl++;
				 scs ++;
				 if(scs==1)
					 lastdate = s;
				 if(countsl%2==0 && !(s.equals("EndOfDaTe")))
					sumsn+=Integer.valueOf(s);
				}
			bfr.close();
			fis.close();
			f= new File(path4);
			fis = new FileInputStream(f);
			bfr = new BufferedReader(new InputStreamReader(fis));
			String slo = "";
			while((s=bfr.readLine())!=null)
				{
				slo +=s;
				}
			fis.close();
			bfr.close();

			sumsn = Integer.valueOf(slo) - sumsn;
			FileOutputStream sf = new FileOutputStream(f);
			OutputStreamWriter osss = new OutputStreamWriter(sf);
			BufferedWriter bwr = new BufferedWriter(osss);
			bwr.write(String.valueOf(sumsn));
			bwr.close();
			osss.close();
			sf.close();
			tvl.setText("Balance Left: "+String.valueOf(sumsn));
			f = new File(path3);
			if(!f.exists())
				f.createNewFile();
			s="";
			fis = new FileInputStream(f);
			bfr = new BufferedReader(new InputStreamReader(fis));
			String slla ="";
			int colla=0;
			colla =0;
			int glo =0;
			while((s=bfr.readLine())!=null)
			{
				if(!s.equals(" "))
				{
					glo++;
					if(glo==1)
						slla = s;
					datatowrite+=s+"\n";
					if(colla==1)
					{
						slla = s;
						colla=0;
					}
					if(s.equals("EndOfDaTe"))
					colla =1;
				}
			}
			if(lastdate.equals(slla))
			{
				String [] allan = datatowrite.split("\n");
				ArrayList <String> ssq = new ArrayList();
				for(int su = 0;su<allan.length-1;su++)
					ssq.add(allan[su]);
				String [] allan2 = datatoread.split("\n");
				for(int su=1;su<allan2.length;su++)
					ssq.add(allan2[su]);
				datatowrite ="";
				for(int su=0;su<ssq.size();su++)
					datatowrite+=ssq.get(su)+"\n";
			}
			else
			{
			  datatowrite+=datatoread;
			}
			bfr.close();
			fis.close();
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(datatowrite);
			osw.close();
			fos.close();
			f = new File(path2);
			fos = new FileOutputStream(f);
			osw = new OutputStreamWriter(fos);
			osw.write("");
			osw.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void currentexpensestatus()
	{
	    // get all details of expenses like date reason for expenditure and amount
		dates.clear();
		enterys.clear();
		reason.clear();
		cost.clear();
		int no=0;
		File f = new File(path3);
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String s ="";
			while((s=bfr.readLine())!=null)
			{
				if(s.contains("/"))
					{
					dates.add(s);

					}
				else if (s.equals("EndOfDaTe"))
					{
					cost.add(s);
					enterys.add(no);
					no=0;
					}
				else
					{
					    no++;
						cost.add(s);
						s=bfr.readLine();
						reason.add(s);
					}
			}
			bfr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void setMonthly()
	{
	    // set monthly expense i.e calculate expense for the whole month
		ArrayList<String> monthandyear = new ArrayList();
		ArrayList<Integer> ints = new ArrayList();
		for(int u=0;u<dates.size();u++)
		{
		     String [] splits =dates.get(u).split("/");
		     String s = month(Integer.valueOf(splits[1]))+String.valueOf(Integer.valueOf(splits[2])%100);
		     if(!monthandyear.contains(s))
		    	 monthandyear.add(s);
		}
		int sum =0,ks=0;
		for(int d=0;d<dates.size();d++)
		{
			sum=0;

		    while(!cost.get(ks).equals("EndOfDaTe"))
			{
				sum+=Integer.valueOf(cost.get(ks));
				ks++;
			}
			ks++;
			String [] splits =dates.get(d).split("/");
		    String s = month(Integer.valueOf(splits[1]))+String.valueOf(Integer.valueOf(splits[2])%100);
		    int index = monthandyear.indexOf(s);
		    if(index!=-1 && index < ints.size())
		    	ints.set(index, ints.get(index)+sum);
		    else
		    	ints.add(sum);
		}
		Integer ssf = Collections.max(ints);
		GraphViewData[] gvd = new GraphViewData[monthandyear.size()];
		for(int k=0;k<monthandyear.size();k++)
			gvd[k]=new GraphViewData(k+1,ints.get(k));
		GraphViewSeries gvs = new GraphViewSeries(gvd);
		GraphView graphView = new  BarGraphView(this,"Monthly expense");
		String[] datel = new String[monthandyear.size()];
		for(int k=0;k<monthandyear.size();k++)
			datel[k]=monthandyear.
			get(k);
		graphView.setHorizontalLabels(datel);
		graphView.addSeries(gvs);
		graphView.getGraphViewStyle().setNumVerticalLabels(5);
		graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
		graphView.setScrollable(true);
		graphView.setScalable(true);
		graphView.setManualYAxisBounds(ssf, 0);
		//graphView.getGraphViewStyle().setNumHorizontalLabels(date.length+3);
		LinearLayout layout = (LinearLayout) findViewById(R.id.monthly);
		layout.addView(graphView);
	}
	String month(Integer i)
	{
	    // return string for given month
		String s="";
		if(i==1)
		{
			s="Jan- ";
		}
		else if(i==2)
		{
			s="Feb- ";
		}
		else if(i==3)
		{
			s="Mar- ";
		}
		else if(i==4)
		{
			s="Apr- ";
		}
		else if(i==5)
		{
			s="May- ";
		}
		else if(i==6)
		{
			s="Jun- ";
		}
		else if(i==7)
		{
			s="Jul- ";
		}
		else if(i==8)
		{
			s="Aug- ";
		}
		else if(i==9)
		{
			s="Sep- ";
		}
		else if(i==10)
		{
			s="Oct- ";
		}
		else if(i==11)
		{
			s="Nov- ";
		}
		else if(i==12)
		{
			s="Dec- ";
		}
		return s;
	}
	void credit(String s)
	{
	    // update available balance once amount credited
		File f = new File(path4);
		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String ss ="",s2="";

			while((s2=bfr.readLine())!=null)
			{
				ss=s2;
			}
			bfr.close();
			fis.close();
				int x = Integer.valueOf(ss)+Integer.valueOf(s);
			    FileOutputStream fos = new FileOutputStream(f);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
			    osw.write(String.valueOf(x)+"\n");
			    osw.close();
			    fos.close();
			    updatebalance();
			    Toast.makeText(getApplicationContext(), "Amount added :)", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void debit(String s)
	{
	    // update available balance when amount debited
		File f = new File(path4);
		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String ss ="",s2="";

			while((s2=bfr.readLine())!=null)
			{
				ss=s2;
			}
			bfr.close();
			fis.close();
				int x = Integer.valueOf(ss)-Integer.valueOf(s);
			    FileOutputStream fos = new FileOutputStream(f);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
			    osw.write(String.valueOf(x)+"\n");
			    osw.close();
			    fos.close();
			    updatebalance();
			    Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void setDaily()
	{
	    // set daily expense i.e calculate on a day to day basis
		ArrayList<Integer> costs = new ArrayList();
		int l=0;
		for(int k=0;k<dates.size();k++)
		{
			int sum=0;
			while(!cost.get(l).equals("EndOfDaTe"))
		      {
				sum+=Integer.valueOf(cost.get(l));
				l++;
		      }
			l++;
			costs.add(sum);
		}
		Integer ssf = Collections.max(costs);

		GraphViewData[] gvd = new GraphViewData[costs.size()];
		for(int k=0;k<costs.size();k++)
			gvd[k]=new GraphViewData(k+1,costs.get(k));
		GraphViewSeries gvs = new GraphViewSeries(gvd);
		GraphView graphView = new  BarGraphView(this,"Daily expense");
		String[] date = new String[dates.size()];
		for(int k=0;k<dates.size();k++)
			date[k]=dates.get(k);
		graphView.setHorizontalLabels(date);
		graphView.addSeries(gvs);
		graphView.getGraphViewStyle().setNumVerticalLabels(5);
		graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
		graphView.setManualYAxisBounds(ssf, 0);

		graphView.setScrollable(true);
		graphView.setScalable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.daily);
		graphView.getGraphViewStyle().setNumHorizontalLabels(date.length+3);
		layout.addView(graphView);
	}

	void setDetailed(String s)
	{
	    // set a detailed expense for a given day, by defaultlast day for which expense is written
		ArrayList<String> reasons= new ArrayList();
		ArrayList<Integer> costs = new ArrayList();
		int ks2=0;
		for(int ks=0;ks<dates.size();ks++)
			{
			if(s.equals(dates.get(ks)))
				{
				ks2=ks;
				break;
				}
			}
			String lastdate = dates.get(dates.size()-1);
			int skip=0,skip2=0;
			int count = enterys.get(ks2);
			for(int k =0; k<ks2;k++)
		{
				skip+=enterys.get(k);
				skip2+=enterys.get(k)+1;
		}
		for(int counter=0;counter<count;counter++)
		{
			reasons.add(reason.get(skip+counter));
			costs.add(Integer.valueOf(cost.get(skip2+counter)));
		}
		String[] copu = new String[reasons.size()];
		for(int s0=0;s0<reasons.size();s0++)
			copu[s0]=reasons.get(s0);
		Integer ssf = Collections.max(costs);
		GraphViewData[] gvdd = new GraphViewData[costs.size()];
		for(int x=0;x<costs.size();x++)
			gvdd[x]=new GraphViewData(x+1,costs.get(x));
	GraphViewSeries series = new GraphViewSeries(gvdd);
	GraphView graphView = new  BarGraphView(this,"Detailed expense for "+s);
	graphView.addSeries(series);
	graphView.setScrollable(true);
	graphView.setScalable(true);
	graphView.setHorizontalLabels(copu);
	graphView.getGraphViewStyle().setNumVerticalLabels(5);
	graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
	graphView.setManualYAxisBounds(ssf, 0);
	//graphView.getGraphViewStyle().setNumHorizontalLabels(costs.size());
	LinearLayout layout = (LinearLayout) findViewById(R.id.detailed);
	layout.removeAllViews();
	layout.addView(graphView);
	}
	void update(String s1, String s2)
	{
	    //update expense status
		File f = new File(path3);
		String s="";
		ArrayList<String> al =new ArrayList();
		String dater="";
		int quantum=0;
		FileInputStream fis;
		try {
			if(!f.exists())
				f.createNewFile();
			fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			while((s=bfr.readLine())!=null)
				{
				al.add(s);
				if(al.get(quantum).contains("/"))
					dater =s;
				quantum++;
				}
			bfr.close();
			fis.close();
			Calendar c = Calendar.getInstance();
			//System.out.println("Current time => " + c.getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = df.format(c.getTime());
			if(formattedDate.equals(dater))
			{
				int size = al.size();
				al.remove(size-1);

			}
			else
			{
				al.add(formattedDate);
			}
			al.add(s1);
			al.add(s2);
			al.add("EndOfDaTe");
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			int k=0;
			while(k<al.size())
			{
				osw.write(al.get(k)+"\n");
				k++;
			}
			osw.close();
			fos.close();
			Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void updatebalance()
	{
	    // update balance available if below 0 alert dialog displayed
		File f = new File(path4);
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String ss ="",s="";
			while((ss=bfr.readLine())!=null)
			{
				s=ss;
			}
			bfr.close();
			fis.close();
			tvl.setText("Balance Left: "+s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId())
			{
			case R.id.newexpense : // if new expense button pressed
            final AlertDialog hint2 =new AlertDialog.Builder(ExpenseManager.this).create();
	        hint2.setTitle("New Expense :( ");
	        hint2.setMessage("Enter expense details");
	        LinearLayout l = new LinearLayout(hint2.getContext());
	        l.setOrientation(LinearLayout.VERTICAL);
	        final EditText et2 = new EditText(hint2.getContext());
	        et2.setKeyListener(new DigitsKeyListener());
	        TextView tv = new TextView(hint2.getContext());
	        tv.setText("Amount :");
	        tv.setTextSize(30);
	        TextView tv2 = new TextView(hint2.getContext());

	        final EditText et22 = new EditText(hint2.getContext());
	        tv2.setText("Reason :");
	        tv2.setTextSize(30);
	        Button b = new Button(hint2.getContext());
	        b.setText("Update expense");
	        b.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(et2.getText().toString()!=null && et22.getText().toString()!=null)
					{
						try{
							Integer op = Integer.parseInt(et2.getText().toString());
							update(et2.getText().toString(),et22.getText().toString());
							debit(et2.getText().toString());
							currentexpensestatus();
							setDaily();
							setDetailed(dates.get(dates.size()-1));
							setMonthly();
							hint2.dismiss();
						}
						catch(NumberFormatException e)
						{
							Toast.makeText(getApplicationContext(), "Enter only a number in amount field", Toast.LENGTH_LONG).show();
						}

					}
					else
						Toast.makeText(getApplicationContext(), "Fill both fields", Toast.LENGTH_LONG).show();
				}
			});
	        l.addView(tv);
	        l.addView(et2);
	        l.addView(tv2);
	        l.addView(et22);
	        l.addView(b);
	        hint2.setView(l);
	        hint2.show();
	        break;
			case R.id.credit : // if amount credited button pressed
				final AlertDialog hint3 =new AlertDialog.Builder(ExpenseManager.this).create();
		        hint3.setTitle("Amount Credited :) ");
		        hint3.setMessage("Enter amount credited");
		        LinearLayout ls = new LinearLayout(hint3.getContext());
		        ls.setOrientation(LinearLayout.VERTICAL);
		        final EditText et4 = new EditText(hint3.getContext());
		        et4.setKeyListener(new DigitsKeyListener());
		        TextView tv4 = new TextView(hint3.getContext());
		        tv4.setText("Amount :");
		        tv4.setTextSize(30);
		        Button b2 = new Button(hint3.getContext());
		        b2.setText("Update ");
		        b2.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(et4.getText().toString()!=null )
						{
							try{
							Integer fs = Integer.parseInt(et4.getText().toString());
							credit(et4.getText().toString());
							hint3.dismiss();
							} catch(NumberFormatException e)
							{
								Toast.makeText(getApplicationContext(), "Enter a number in the amount field", Toast.LENGTH_LONG).show();
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
		        break;
		}
	}
		public boolean onCreateOptionsMenu(Menu menu)
	    {
	         // setting up menu to select a date to see detailed expense for
	    	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.date, menu);
	    	return super.onCreateOptionsMenu(menu);
	    }

	     public boolean onOptionsItemSelected(MenuItem item)
	    {
	        // on click listener for menu item
		 LayoutParams a,b;
	    	switch(item.getItemId())
	    	{

	    	 case R.id.dates :
	    		final AlertDialog hint2 =new AlertDialog.Builder(ExpenseManager.this).create();
	 	        hint2.setTitle("Select Date ");

	 	        hint2.setMessage("Select date for which detailed expense is to be displayed");
	 	        LinearLayout l = new LinearLayout(hint2.getContext());
	 	        l.setOrientation(LinearLayout.VERTICAL);

	 	        TextView tv2 = new TextView(hint2.getContext());
	 	        final EditText et2 = new EditText(hint2.getContext());
	 	        String sadhu="";
	 	        for(int s =0;s<dates.size();s++)
	 	         {
	 	        	 sadhu+=String.valueOf(s+1)+". "+dates.get(s)+"\n";
	 	         }

	 	        tv2.setText(sadhu);
	 	        Button bse = new Button(hint2.getContext());
	 	        bse.setText("Update expense");
	 	        bse.setOnClickListener(new View.OnClickListener() {

	 				@Override
	 				public void onClick(View v) {
	 					// TODO Auto-generated method stub
	 					if(et2.getText().toString()!=null )
	 					{
	 						try{
	 							Integer op = Integer.parseInt(et2.getText().toString());
	 							if(op<=dates.size())
	 								{setDetailed(dates.get(op-1));
	 							hint2.dismiss();}
	 							else
	 								Toast.makeText(getApplicationContext(), "Value in the range provided", Toast.LENGTH_LONG).show();
	 						}
	 						catch(NumberFormatException e)
	 						{
	 							Toast.makeText(getApplicationContext(), "Enter only a number in amount field", Toast.LENGTH_LONG).show();
	 						}

	 					}
	 					else
	 						Toast.makeText(getApplicationContext(), "Fill  field", Toast.LENGTH_LONG).show();
	 				}
	 			});
	 	        l.addView(tv2);
	 	        l.addView(et2);
	 	        l.addView(bse);
	 	        ScrollView sw = new ScrollView(ExpenseManager.this);
	 	        sw.addView(l);
	 	        hint2.setView(sw);
	 	        hint2.show();
	    		      break;
	    	}
	    	return true;
	    }


}
