package com.isila.hafizaoyunu;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class OyunEkrani extends AppCompatActivity {
    TextView tv, tvkalansure;
    int sonKart = 0;
    int skor = 0;
    int hata = 0;
    String tamEkranAd = "ca-app-pub-5037089565212879/7301103816";
    private AdView mAdView;
    MediaPlayer butonClick, eslesdi, skorekrani, surebitti;
    private InterstitialAd interstitialAd;
    CountDownTimer timer;
    Context context = this;


    public OyunEkrani() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.oyunekrani);

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);


        tvkalansure = findViewById(R.id.kalansure);
        butonClick = MediaPlayer.create(this, R.raw.btnclick);
        eslesdi = MediaPlayer.create(this, R.raw.eslesdi2);
        skorekrani = MediaPlayer.create(this, R.raw.skorekran);
        surebitti = MediaPlayer.create(this, R.raw.surebitti);

        //tam ekran reklam
        final AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest2);


        GridLayout gl = findViewById(R.id.kartlar);

        //Banner Reklam
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        timer = new CountDownTimer(26000, 1000) {
            @Override
            public void onTick(long l) {

                tvkalansure.setText(" " + l / 1000);
            }

            @Override
            public void onFinish() {
                final Intent i3 = new Intent(OyunEkrani.this, SureBitti.class);

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    surebitti.start();
                    startActivity(i3);
                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        interstitialAd.loadAd(adRequest2);
                        surebitti.start();
                        startActivity(i3);
                    }
                });
            }
        }.start();


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

                    //eğer bir kart çevrilmişse
                    if (sonKart > 0) {

                        final Kart k2 = findViewById(sonKart);
                        if (k2.onID == k.onID && k2.getId() != k.getId()) {

                            k.setClickable(false);
                            k2.setClickable(false);
                            k.cevrilebilir = false;
                            k2.cevrilebilir = false;
                            skor++;
                            // titre.vibrate(250);
                            eslesdi.start();

                            sonKart = 0;
                            //oyun bitti
                            if (skor == 8) {
                                timer.cancel();
                                final Intent i2 = new Intent(OyunEkrani.this, SkorEkrani.class);
                                i2.putExtra("hatalar", hata);
                                // i2.putExtra("isim", s);
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                } else {
                                    skorekrani.start();
                                    startActivity(i2);
                                }
                                interstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {
                                        super.onAdClosed();
                                        interstitialAd.loadAd(adRequest2);
                                        skorekrani.start();
                                        startActivity(i2);
                                    }
                                });


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
                            tvHata.setText("" + hata);

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
    protected void onStop() {
        super.onStop();
        timer.cancel();
        finish();

    }

    @Override
    public void onBackPressed() {
        showMyCustomAlertDialog();
    }

    public void showMyCustomAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cikisstoast);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button btnEvet = (Button) dialog.findViewById(R.id.tevet);
        Button btnHayir = (Button) dialog.findViewById(R.id.thayir);
        //  TextView tvBaslik = (TextView) dialog.findViewById(R.id.textview_baslik);


        // tamam butonunun tıklanma olayları
        btnEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
        // iptal butonunun tıklanma olayları
        btnHayir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
