package com.example.hafizaoyunu;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnaEkran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anaekran);
        Button button = findViewById(R.id.basla);
        final EditText isim = findViewById(R.id.isim);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //burda isim yazmadan diğer ekana geçme dedik,ekrana bir alert dialog getiriyoruz
                if (TextUtils.isEmpty(isim.getText())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AnaEkran.this);
                    builder.setTitle("UYARI").setMessage("Lütfen isim alanını doldurunuz");

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    //yada sadece toast mesajıda yayınlayabiliriz..
                    //   Toast.makeText(AnaEkran.this, "Lütfen isim alanını doldurunuz..",
                    //         Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(AnaEkran.this, OyunEkrani.class);
                    intent.putExtra("isim", isim.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }

}
