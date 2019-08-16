package com.isila.hafizaoyunu;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    static final String DOSYA_ISMI = "Dosya";
    static final String KEY_VALUE = "Key";

    public void isimKaydet(Context context, String text) {
        SharedPreferences ayarlar = context.getSharedPreferences(DOSYA_ISMI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ayarlar.edit();
        editor.putString(KEY_VALUE, text);
        editor.commit();
    }

    public String getValue(Context context) {
        SharedPreferences ayarlar = context.getSharedPreferences(DOSYA_ISMI, Context.MODE_PRIVATE);
        String text = ayarlar.getString(KEY_VALUE, null);
        return text;

    }

}
