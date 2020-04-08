package com.isila.hafizaoyunu;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    TextView hatat, hatat2;
    Button button;
    private AdView mAdView;
    Context context = this;
    //  String tamEkranAd = "ca-app-pub-5037089565212879/7301103816";
    MediaPlayer butonclick3;
    //   private InterstitialAd interstitialAd;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.skorekrani);

        sp = PreferenceManager.getDefaultSharedPreferences(context);


      /*  interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);
        final AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest2);*/

        mAdView = findViewById(R.id.adViewskor);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        butonclick3 = MediaPlayer.create(this, R.raw.butonses);
        hatat = findViewById(R.id.hata);
        hatat2 = findViewById(R.id.hata2);
        button = findViewById(R.id.button);

        Intent i = getIntent();

        int hata = i.getIntExtra("hatalar", 0);
        //  String isim=i.getStringExtra("isim");
        //   SharedPref sharedPref = new SharedPref();
        final String isim = sp.getString("kullaniciadi", null);

        hatat.setText(isim.toUpperCase() + ", " + hata + " ");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonclick3.start();
                final Intent i = new Intent(SkorEkrani.this, OyunEkrani.class);
                startActivity(i);
           /*
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    startActivity(i);
                }
*/
           /*     interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        interstitialAd.loadAd(adRequest2);
                        startActivity(i);
                    }
                });
*/

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
            showMyCustomAlertDialog();

        }

        return super.onKeyDown(keyCode, event);
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
