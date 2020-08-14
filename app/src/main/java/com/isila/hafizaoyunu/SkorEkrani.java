package com.isila.hafizaoyunu;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class SkorEkrani extends AppCompatActivity {

    TextView hatat, hatat2, enAzHata;
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
        setContentView(R.layout.skorekrani);

        sp = getSharedPreferences("SignUp", MODE_PRIVATE);

        mAdView = findViewById(R.id.adViewskor);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        butonclick3 = MediaPlayer.create(this, R.raw.butonses);
        hatat = findViewById(R.id.hata);
        hatat2 = findViewById(R.id.hata2);
        button = findViewById(R.id.button);
        enAzHata = findViewById(R.id.hsayi);


        Intent i = getIntent();

        int hata = i.getIntExtra("hatalar", 0);

        final String isim = sp.getString("kullaniciadi", null);
        int azHata=sp.getInt("azHataa",0);


        if (hata < azHata || azHata==0) {
            sp.edit().putInt("azHataa",hata).commit();
            enAzHata.setText(String.valueOf(hata));
        } else {
            enAzHata.setText(String.valueOf(azHata));
        }


        hatat.setText(isim.toUpperCase() + ", " + hata + " ");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonclick3.start();
                final Intent i = new Intent(SkorEkrani.this, OyunEkrani.class);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
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
