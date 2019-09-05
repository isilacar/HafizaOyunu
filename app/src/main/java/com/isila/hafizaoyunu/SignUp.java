package com.isila.hafizaoyunu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_0;
import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_1;
import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_2;
import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_3;
import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_4;
import static com.isila.hafizaoyunu.SQLiteHelperr.ROW_5;
import static com.isila.hafizaoyunu.SQLiteHelperr.TABLE_NAME;

public class SignUp extends AppCompatActivity {
    EditText etisim, etsoyisim, etemail, etkullaniciadi, etsifre;
    Button btnkaydet, btnvazgec;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        SQLiteHelperr helperr = new SQLiteHelperr(SignUp.this);
        final SQLiteDatabase db = helperr.getWritableDatabase();
        final ContentValues values = new ContentValues();
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //veriyi kaydetmek için
                values.put(ROW_1, etisim.getText().toString());
                values.put(ROW_2, etsoyisim.getText().toString());
                values.put(ROW_3, etemail.getText().toString());
                values.put(ROW_4, etkullaniciadi.getText().toString());
                values.put(ROW_5, etsifre.getText().toString());

                Toast.makeText(context, "Başarıyla kayıt olundu", Toast.LENGTH_SHORT).show();
                Intent intentSignup = new Intent(SignUp.this, AnaEkran.class);
                long row = db.insert(TABLE_NAME, null, values);


                Log.i("mesaj", "EKLENDİ. ROW ID = " + row);
                startActivity(intentSignup);
            }
        });
                //Kayıtlı veriyi okumak için
        /*String projection[]={ROW_0,ROW_1,ROW_2,ROW_3,ROW_4,ROW_5};
        Cursor c=db.query(TABLE_NAME, projection, null, null, null, null, null);
        c.moveToPosition(1);
        Log.i("goster", "Adı = "+c.getString(1));*/



    }

    public void init() {
        etisim = findViewById(R.id.etisim);
        etsoyisim = findViewById(R.id.etsoyisim);
        etemail = findViewById(R.id.etemail);
        etkullaniciadi = findViewById(R.id.etkullaniciadi);
        etsifre = findViewById(R.id.etsifre);
        btnkaydet = findViewById(R.id.btnkaydet);
        btnvazgec = findViewById(R.id.btnvazgec);
    }
}
