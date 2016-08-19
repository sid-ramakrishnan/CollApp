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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Splash extends Activity implements OnClickListener
{

	EditText amt,reason;
	String amount,reasons,path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// decode image currency.png
		InputStream strm = getResources().openRawResource(R.drawable.currency);
        Bitmap bmp = BitmapFactory.decodeStream(strm);
		setContentView(R.layout.splash);
		amt = (EditText)findViewById(R.id.etamt);
		amt.setKeyListener(new DigitsKeyListener());
		reason = (EditText) findViewById(R.id.etreason);
		Button b = (Button) findViewById(R.id.updateexpense);
		b.setOnClickListener(this);
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp";
	}
	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		// setting onclick listener for update expense button
		amount = amt.getText().toString();
		reasons = reason.getText().toString();
		Integer kk = new Integer(0);
		try{
			kk = Integer.parseInt(amount);
			}
		catch(NumberFormatException e)
		{
			Toast.makeText(getApplicationContext(), "Enter only a number in the amount field", Toast.LENGTH_LONG).show();
			return;
		}
		File f = new File(path);
		f.mkdirs();
		String pathnew = path + File.separator+"tempexpense.txt";
		f = new File(pathnew);
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Calendar c = Calendar.getInstance();
			//System.out.println("Current time => " + c.getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = df.format(c.getTime());
			FileInputStream fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			ArrayList<String> data=new ArrayList();
			String s ="";
			int chk = 0;
			while((s=bfr.readLine())!=null)
			{
				if(s.equals(formattedDate))
					chk=1;
			 data.add(s);
			}
			if(chk==1)
			data.remove(data.size()-1);
			if(chk==0)
			data.add(formattedDate);
			data.add(amount);
			data.add(reasons);
			data.add("EndOfDaTe");
			bfr.close();
			fis.close();
			FileOutputStream fos = new FileOutputStream (f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			for(int ip=0;ip<data.size();ip++)
			osw.write(data.get(ip)+"\n");
			osw.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Problem", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Problem", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "Quiting", Toast.LENGTH_LONG).show();
		finish();

	}

}
