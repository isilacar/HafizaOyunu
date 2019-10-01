package com.isila.hafizaoyunu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class OyunEkrani extends AppCompatActivity {
    TextView tv,tvkalansure;
    int sonKart = 0;
    int skor = 0;
    int hata = 0;

    private AdView mAdView;
    MediaPlayer butonClick;
    Vibrator titre;
    CountDownTimer timer;


    Context context=this;
    public OyunEkrani() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.oyunekrani);

        tvkalansure=findViewById(R.id.kalansure);
        butonClick=MediaPlayer.create(this, R.raw.btnclick);

        titre= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        SharedPref sharedPref = new SharedPref();
        final String s = sharedPref.getValue(context,"kullaniciadi");
   //     tv = findViewById(R.id.textView2);
     //   tv.setText("");
     //   tv.setText("Merhaba " + s + ".Hafıza Oyununa Hoşgeldin..");
        GridLayout gl = findViewById(R.id.kartlar);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        timer=new CountDownTimer(25000,1000) {
            @Override
            public void onTick(long l) {

                tvkalansure.setText("Kalan Süre = " +l/1000);
            }

            @Override
            public void onFinish() {
                Intent i2 = new Intent(OyunEkrani.this, SureBitti.class);
           //     Toast.makeText(context, "SÜRE BİTTİ", Toast.LENGTH_SHORT).show();
                startActivity(i2);
            }
        }.start();



//        Intent i = getIntent();
 //       final String s = i.getStringExtra("isim");



        //kartların içeriklerini bir arraya atıyoruz.
        //her bir buton için clicklistener uyguluyoruz
        Kart kartlar[] = new Kart[16];

        for (int a = 1; a <= 16; a++) {
            kartlar[a - 1] = new Kart(this, a);

            kartlar[a - 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Kart k = (Kart) v;
                    k.cevir();
                    butonClick.start();
                    //eğer bir kart çevrilmişse
                    if (sonKart > 0) {

                        final Kart k2 = findViewById(sonKart);
                        if (k2.onID == k.onID && k2.getId() != k.getId()) {
                            k.setClickable(false);
                            k2.setClickable(false);
                            k.cevrilebilir = false;
                            k2.cevrilebilir = false;
                            skor++;
                            titre.vibrate(250);
                           // TextView tvSkor = findViewById(R.id.textSkor);

                     //       tvSkor.setText("Skorunuz = " + skor);
                            sonKart = 0;

                            if (skor == 8) {
                                //oyun bitti
                                timer.cancel();
                                Intent i2 = new Intent(OyunEkrani.this, SkorEkrani.class);
                                i2.putExtra("hatalar", hata);
                                i2.putExtra("isim", s);

                                startActivity(i2);
                            }
                        } else {
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    k2.cevir();
                                    k.cevir();
                                }
                            }, 500);

                            sonKart = 0;
                            hata++;
                            TextView tvHata = findViewById(R.id.textHata);
                            tvHata.setText("HATA : " + hata);

                        }
                    } else {
                        sonKart = k.getId();
                    }
                }
            });
        }

        //karıştır
        for (int a = 0; a < 16; a++) {
            int rg = (int) (Math.random() * 16);
            Kart k = kartlar[rg];
            kartlar[rg] = kartlar[a];
            kartlar[a] = k;
        }

        //ekrana ekleyelim
        for (int a = 1; a < 17; a++) {
            gl.addView(kartlar[a - 1]);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
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
