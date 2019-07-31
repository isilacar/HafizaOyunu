package com.example.hafizaoyunu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SkorEkrani extends AppCompatActivity {

TextView hatat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skorekrani);

        hatat=findViewById( R.id.hata);



        Intent i=getIntent();

        int hata=i.getIntExtra("hatalar",0);
        String isim=i.getStringExtra("isim");

        hatat.setText(isim+", "+hata + " hata ile oyunu bitirdin.Tebrikler..");

    }

}
