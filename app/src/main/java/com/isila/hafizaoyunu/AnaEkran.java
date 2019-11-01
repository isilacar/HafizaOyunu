package com.isila.hafizaoyunu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AnaEkran extends AppCompatActivity {
    private AdView mAdView;
    MediaPlayer butonClick2;
    Context context = this;
    Button btngiris,btnbilgi;
    EditText etkullanici;
    String tamEkranAd="ca-app-pub-3940256099942544/1033173712";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full ekran yapıyor
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.anaekran);
        final SharedPref sharedPref = new SharedPref();
      //  MobileAds.initialize(context,tamEkranAd);
        final InterstitialAd interstitialAd=new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);
        final AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest2);

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            interstitialAd.show();
            }

        });


        butonClick2 = MediaPlayer.create(this, R.raw.butonses);


        etkullanici = findViewById(R.id.isim);
        etkullanici.setText(sharedPref.getValue(context, "kullaniciadi"));
        btngiris = findViewById(R.id.btngiris);
        btnbilgi=findViewById(R.id.btnbilgi);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                butonClick2.start();
                if (TextUtils.isEmpty(etkullanici.getText().toString())) {
                    Toast.makeText(context, "İsim alanı boş bırakılamaz..", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPref.saveString(context, "kullaniciadi", etkullanici.getText().toString());
                    Intent i = new Intent(AnaEkran.this, OyunEkrani.class);
                    startActivity(i);

                    toastGoster();

                }

            }
        });

        btnbilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonClick2.start();
                Intent i=new Intent(AnaEkran.this,Bilgi.class);
                startActivity(i);
            }
        });


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

    public void toastGoster() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_root));
        TextView toastt = view.findViewById(R.id.toasttext);
        toastt.setText("Merhaba " + etkullanici.getText().toString() + ".Haf" +
                "ıza Oyununa Hoşgeldin..");
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

    }

}
