package com.isila.hafizaoyunu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;

//Kart sınıfımız buton özelliği taşıyacağı için extends button dedik
public class Kart extends Button {
    boolean acikMi = false;
    boolean cevrilebilir=true;
    int onID; //ön taraftaki resim
    int arkaPlanID; //arka taraftaki resim
    Drawable arka;
    Drawable on;



    public Kart(Context context, int id) {
        super(context);
        setId(id);
        arkaPlanID = R.drawable.soru; //R java classımızın drawable klasöründe arka resmine
        // ulaşıyoruz

        if(id%8==1){
            onID=R.drawable.p1;
        }
        if(id%8==2){
            onID=R.drawable.p2;
        }
        if(id%8==3){
            onID=R.drawable.p3;
        }
        if(id%8==4){
            onID=R.drawable.p4;
        }
        if(id%8==5){
            onID=R.drawable.p5;
        }
        if(id%8==6){
            onID=R.drawable.p6;
        }
        if(id%8==7){
            onID=R.drawable.p7;
        }
        if(id%8==0){
            onID=R.drawable.p8;
        }

        arka = AppCompatDrawableManager.get().getDrawable(context, arkaPlanID);
        on=AppCompatDrawableManager.get().getDrawable(context, onID);
        setBackground(arka);




}
public void cevir(){
        if(cevrilebilir){
        if(!acikMi){//açıksa karta resimleri getir
            setBackground(on);
            acikMi=true;
        }
        else{//kapalıysa karta soru işareti getir
            setBackground(arka);
            acikMi=false;
        }
        }
}
}
