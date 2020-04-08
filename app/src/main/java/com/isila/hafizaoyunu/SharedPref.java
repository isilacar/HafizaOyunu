package com.isila.hafizaoyunu;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    static final String DOSYA_ISMI = "SignUp";

    public void saveString(Context context, String key, String value) {
        //verilerimizi kendi telefonumuzda dosya diye bir klasöre kaydedicek
        //aNAEKRAN DAKİ KULLANICI ADINI ALICAZ
        SharedPreferences ayarlar = context.getSharedPreferences(DOSYA_ISMI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ayarlar.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String getValue(Context context, String key) {
        SharedPreferences ayarlar = context.getSharedPreferences(DOSYA_ISMI, Context.MODE_PRIVATE);
        String text = ayarlar.getString(key, null);
        return text;

    }

}
