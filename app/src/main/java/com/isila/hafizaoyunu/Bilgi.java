package com.isila.hafizaoyunu;

import android.app.*;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Bilgi extends AppCompatActivity {
Button btnanaekran;
Context context=this;
MediaPlayer btnclickk;
private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bilgi);
        btnanaekran=findViewById(R.id.bilgiana);
        btnclickk=MediaPlayer.create(this, R.raw.butonses);
        btnanaekran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclickk.start();
                Intent i=new Intent(Bilgi.this,AnaEkran.class);
                startActivity(i);
            }
        });

        mAdView = findViewById(R.id.adViewbilgi);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
