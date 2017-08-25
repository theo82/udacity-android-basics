package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import  com.example.android.inventoryapp.data.ProductContract.ProductEntry;

import static android.R.attr.version;

/**
  *  Created by theodosiostziomakas on 23/08/2017.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    public static final String TAG = ProductDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

     public ProductDbHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }


     @Override
     public void onCreate(SQLiteDatabase db) {
         String SQL_CREATE_INVENTORY = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                 + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + ProductEntry.COL_NAME + " TEXT NOT NULL, "
                 + ProductEntry.COL_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                 + ProductEntry.COL_PRICE + " REAL NOT NULL DEFAULT 0.0, "
                 + ProductEntry.COL_PICTURE + " TEXT NOT NULL DEFAULT 'No images', "
                 + ProductEntry.COL_DESCRIPTION + " TEXT NOT NULL DEFAULT 'New Product ', "
                 + ProductEntry.COL_ITEMS_SOLD + " INTEGER NOT NULL DEFAULT 0, "
                 + ProductEntry.COL_SUPPLIER + " TEXT NOT NULL DEFAULT 'new' "
                 + ");";

         db.execSQL(SQL_CREATE_INVENTORY);
     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
         onCreate(db);
     }
 }
