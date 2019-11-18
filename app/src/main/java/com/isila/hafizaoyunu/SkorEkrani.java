package com.isila.hafizaoyunu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class SkorEkrani extends AppCompatActivity {

    TextView hatat;
    Button button;
    private AdView mAdView;
    Context context = this;
    String tamEkranAd = "ca-app-pub-6855653886010075/1791522221";
    MediaPlayer butonclick3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.skorekrani);

        final InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);
        final AdRequest adRequest2 = new AdRequest.Builder()
                .build();
        interstitialAd.loadAd(adRequest2);
        //interstitialAd.loadAd(new AdRequest.Builder().build());

        butonclick3 = MediaPlayer.create(this, R.raw.butonses);
        hatat = findViewById(R.id.hata);
        button = findViewById(R.id.button);

        mAdView = findViewById(R.id.adViewskor);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Intent i = getIntent();

        int hata = i.getIntExtra("hatalar", 0);
        //  String isim=i.getStringExtra("isim");
        SharedPref sharedPref = new SharedPref();
        final String isim = sharedPref.getValue(context, "kullaniciadi");

        hatat.setText(isim.toUpperCase() + ", " + hata + " HATA İLE OYUNU BİTİRDİN. TEBRİKLER..");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonclick3.start();
                final Intent i = new Intent(SkorEkrani.this, OyunEkrani.class);
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    startActivity(i);
                }

                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        interstitialAd.loadAd(adRequest2);
                        startActivity(i);
                    }
                });


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("ÇIKIŞ");
            builder.setMessage("Çıkmak istediğinize eminmisiniz?");
            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);

                }
            });
            builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();

        }

        return super.onKeyDown(keyCode, event);
    }
}
