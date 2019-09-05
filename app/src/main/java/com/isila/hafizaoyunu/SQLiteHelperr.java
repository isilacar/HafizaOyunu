package com.isila.hafizaoyunu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
//data işlemlerimiz için bu sıfını kullanıcaz,yeni kullanıcı kayıtları için,SQLiteOpenHelper
// sınıfını extend etmek zorunda
public class SQLiteHelperr extends SQLiteOpenHelper {
  //Teknodate ders 94 e bak
  public static final String DATABASE_NAME="kullanici";
  public static final String TABLE_NAME="kisiler";
  public static final int VERSION_NO=1;
  public static final String ROW_0="kid";
  public static final String ROW_1="kisim";
  public static final String ROW_2="ksoyisim";
  public static final String ROW_3="kemail";
  public static final String ROW_4="kkullaniciadi";
  public static final String ROW_5="ksifre";

    public SQLiteHelperr(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + ROW_0 + " INTEGER NOT NULL PRIMARY KEY " +
          "AUTOINCREMENT UNIQUE, " + ROW_1 + " TEXT NOT NULL, " + ROW_2 + " TEXT NOT NULL, " + ROW_3 + " TEXT NOT NULL UNIQUE, " + ROW_4 + " TEXT NOT NULL UNIQUE, " + ROW_5 + " TEXT NOT NULL) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

      db.execSQL("drop table if exists " + TABLE_NAME);
      onCreate(db);
    }
}
