package com.isila.hafizaoyunu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class SkorEkrani extends AppCompatActivity {

TextView hatat;
Button button;
private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skorekrani);

        hatat=findViewById( R.id.hata);
        button=findViewById(R.id.button);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Intent i=getIntent();

        int hata=i.getIntExtra("hatalar",0);
        String isim=i.getStringExtra("isim");

        hatat.setText(isim+", "+hata + " hata ile oyunu bitirdin.Tebrikler..");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SkorEkrani.this,OyunEkrani.class);
                startActivity(i);
            }
        });

    }

}
