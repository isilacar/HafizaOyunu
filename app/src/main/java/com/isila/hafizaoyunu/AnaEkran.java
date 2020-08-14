package com.isila.hafizaoyunu;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.*;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.textfield.TextInputEditText;


public class AnaEkran extends AppCompatActivity {
    private AdView mAdView;
    MediaPlayer butonClick2;
    Context context = this;
    Button btngiris, btnbilgi;
    TextInputEditText etkullanici;
    TextView isimToast;
    String tamEkranAd = "ca-app-pub-5037089565212879/7301103816";
    private InterstitialAd interstitialAd;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    private Animation asagidanyukari,yukaridanasagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.anaekran);

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(tamEkranAd);
        final AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest2);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        asagidanyukari= AnimationUtils.loadAnimation(context, R.anim.asagidanyukari);
        yukaridanasagi= AnimationUtils.loadAnimation(context, R.anim.yukardanasagi);

        sp = getSharedPreferences("SignUp", MODE_PRIVATE);
        spe = sp.edit();

        butonClick2 = MediaPlayer.create(this, R.raw.butonses);

        etkullanici = findViewById(R.id.isim);
        //  etkullanici.setText(sharedPref.getValue(context, "kullaniciadi"));
        etkullanici.setText(sp.getString("kullaniciadi", null));
        btngiris = findViewById(R.id.btngiris);
        btnbilgi = findViewById(R.id.btnbilgi);

        btnbilgi.setAnimation(asagidanyukari);
        btngiris.setAnimation(asagidanyukari);
        etkullanici.setAnimation(yukaridanasagi);

        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                butonClick2.start();
                if (TextUtils.isEmpty(etkullanici.getText().toString())) {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View view2 = layoutInflater.inflate(R.layout.isimalani_toast,
                            (ViewGroup) findViewById(R.id.toast_isimalani));
                    Toast toast = new Toast(context);
                    //    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(view2);
                    toast.show();
                    //  Toast.makeText(context, "İsim alanı boş bırakılamaz..", Toast.LENGTH_SHORT).show();
                } else {

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
                final Intent i = new Intent(AnaEkran.this, Bilgi.class);
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    startActivity(i);
                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                     //   super.onAdClosed();
                        interstitialAd.loadAd(adRequest2);
                        startActivity(i);
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        showMyCustomAlertDialog();
    }


    public void toastGoster() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_root));
        TextView toastt = view.findViewById(R.id.toasttext);
        toastt.setText(etkullanici.getText().toString().toUpperCase() + " ");
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

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


