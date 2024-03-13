package com.example.testeapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    static final String DATABASE_NAME = "ProductDB.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "table_produto";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "product_name";
    static final String COLUMN_IMAGE = "product_image";
    static final String COLUMN_PRICE = "product_price";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS table_produto(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMAGE + " VARCHAR(100)," +
                COLUMN_PRICE + " LONG" +
                ");";
        String queryReprocess = "CREATE TABLE IF NOT EXISTS table_reprocess(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "reprocess_auth VARCHAR(100)," +
                "reprocess_installments INTEGER," +
                "reprocess_nsu VARCHAR(100)," +
                "reprocess_paycode VARCHAR(100)," +
                "reprocess_price LONG)";
        try{
            db.execSQL(query);
            db.execSQL(queryReprocess);
            Log.d("TABLE","TABLE CRIADA");
        }
        catch (Exception e){
            Log.d("TABLE","TABLE NAO CRIADA");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
