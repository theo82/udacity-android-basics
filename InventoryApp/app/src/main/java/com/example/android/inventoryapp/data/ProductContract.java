package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by theodosiostziomakas on 23/08/2017.
 */

public class ProductContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PRODUCT = "product";

    public static final class ProductEntry implements BaseColumns{
        /**
         * The content URI to access the inventory data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);
        /**
         * The MIME type of the {@link #BASE_CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        /**
         * The MIME type of the {@link #BASE_CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        /**
         * Name of database table for inventory
         */
        public final static String TABLE_NAME = "inventory";

        public final static String _ID = BaseColumns._ID;
        public final static String COL_NAME = "name";
        public final static String COL_QUANTITY = "quantity";
        public final static String COL_PRICE = "price";
        public final static String COL_DESCRIPTION = "description";
        public final static String COL_ITEMS_SOLD = "sales";
        public final static String COL_SUPPLIER = "supplier";
        public final static String COL_PICTURE = "picture";

        public static Uri buildProductURI(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
