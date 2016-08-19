package com.collapp.rkd;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class OpenScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;
    boolean firstTime=false;
    FileInputStream fis;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.openscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
            	{
            	Intent mainIntent = new Intent(OpenScreen.this,AppMenu.class);
                OpenScreen.this.startActivity(mainIntent);
                OpenScreen.this.finish();
            	}
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}