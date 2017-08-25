package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.R.attr.defaultFocusHighlightEnabled;
import static android.R.attr.id;
import static android.R.attr.switchMinWidth;
import static android.os.Build.PRODUCT;

/**
 * Created by theodosiostziomakas on 23/08/2017.
 */

public class ProductProvider extends ContentProvider{
    public static final String TAG = ProductContract.ProductEntry.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the inventory table
     */
    private static final int PRODUCT = 100;

    /**
     * URI matcher code for the content URI for single item in the inventory table
     */
    private static final int PRODUCT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT, PRODUCT);

        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT + "/#", PRODUCT_ID);
    }


    private ProductDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper = new ProductDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Cursor cursor;

            int match = sUriMatcher.match(uri);

            switch (match){
                case PRODUCT:
                    cursor = db.query(ProductContract.ProductEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;
                case PRODUCT_ID:
                    selection = ProductContract.ProductEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                    cursor = db.query(ProductContract.ProductEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);

                    break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
            }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch(match){
            case PRODUCT:
                return insertProduct(uri,contentValues);
            default:
                throw new IllegalArgumentException("Bad uri");
        }

    }

    public Uri insertProduct(Uri uri, ContentValues values){

        // Check that the name is not null
        String name = values.getAsString(ProductContract.ProductEntry.COL_NAME);

        if(name == null){
            throw new IllegalArgumentException("Product requires name");
        }

        Integer quantity = values.getAsInteger(ProductContract.ProductEntry.COL_QUANTITY);

        Float price = values.getAsFloat(ProductContract.ProductEntry.COL_PRICE);

        if(price != null && price < 0){
            throw new IllegalArgumentException("Product requires a valid price");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long rowId = db.insert(ProductContract.ProductEntry.TABLE_NAME,null,values);

        if (rowId == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int rowsDeleted;

        switch(match){
            case PRODUCT:
                rowsDeleted = db.delete(ProductContract.ProductEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ProductContract.ProductEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported" + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        int updatedRows;
        switch(match){
            case PRODUCT:
                updatedRows = database.update(
                        ProductContract.ProductEntry.TABLE_NAME,
                        contentValues,
                        s,
                        strings);
                break;
            case PRODUCT_ID:
                updatedRows = database.update(
                        ProductContract.ProductEntry.TABLE_NAME,
                        contentValues,
                        ProductContract.ProductEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }

        return updatedRows;
    }


}
