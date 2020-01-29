package com.isila.hafizaoyunu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class AnaEkran extends AppCompatActivity {
    private AdView mAdView;
    MediaPlayer butonClick2;
    Context context = this;
    Button btngiris, btnbilgi;
    EditText etkullanici;
    String tamEkranAd = "ca-app-pub-5037089565212879/7301103816";
    private InterstitialAd interstitialAd;
    SharedPreferences sp;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //full ekran yapıyor
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.anaekran);


        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);
        final AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest2);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }
        });


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sp= PreferenceManager.getDefaultSharedPreferences(context);
        spe=sp.edit();


        butonClick2 = MediaPlayer.create(this, R.raw.butonses);

        etkullanici = findViewById(R.id.isim);
      //  etkullanici.setText(sharedPref.getValue(context, "kullaniciadi"));
        etkullanici.setText(sp.getString("kullaniciadi", null));
        btngiris = findViewById(R.id.btngiris);
        btnbilgi = findViewById(R.id.btnbilgi);

        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                butonClick2.start();
                if (TextUtils.isEmpty(etkullanici.getText().toString())) {
                    Toast.makeText(context, "İsim alanı boş bırakılamaz..", Toast.LENGTH_SHORT).show();
                } else {
                   /* sharedPref.saveString(context, "kullaniciadi",
                            etkullanici.getText().toString());*/
                   spe.putString("kullaniciadi", etkullanici.getText().toString());
                   spe.commit();
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
                Intent i = new Intent(AnaEkran.this, Bilgi.class);
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
