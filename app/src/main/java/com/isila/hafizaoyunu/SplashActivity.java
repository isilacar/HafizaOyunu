package com.isila.hafizaoyunu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;

public class SplashActivity extends AppCompatActivity {
Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //GoogleAdmob da ki hafıza Oyunununadmob idsi
        MobileAds.initialize(context,
                "ca-app-pub-5037089565212879~8312246236");



        new SayfaGecisi().start();
    }
    private class SayfaGecisi extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Intent i=new Intent(SplashActivity.this,AnaEkran.class);
                startActivity(i);
                finish();
            }
        }
    }
}
