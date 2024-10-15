package com.example.vamsi.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "PreferredProducts.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_PRODUCTS = "products";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    // Create Table SQL Query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE + " INTEGER, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        // Insert initial products
        insertInitialProducts(db);
    }

    private void insertInitialProducts(SQLiteDatabase db) {
        // Example products. Replace with your own products and images.
        insertProduct(db, new Product(0, R.drawable.mobile, "Smartphone with 6GB RAM", 299.99));
        insertProduct(db, new Product(0, R.drawable.monitor, "24-inch Full HD Monitor", 149.99));
        insertProduct(db, new Product(0, R.drawable.printer, "Wireless Printer", 89.99));
        // Add more products as needed
    }

    private void insertProduct(SQLiteDatabase db, Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, product.getImage());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        db.insert(TABLE_PRODUCTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // CRUD Operations

    // Add a new product
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, product.getImage());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // Get all products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                product.setImage(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));

                productList.add(product);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    // Update a product
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, product.getImage());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        // updating row
        return db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }

    // Delete a product
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
























//package com.example.vamsi.login;
//
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//        public static final String DATABASE_NAME="register.db";
//        public static final String TABLE_NAME="registration";
//        public static final String COL_1="ID";
//        public static final String COL_2="Name";
//        public static final String COL_3="Phone";
//        public static final String COL_4="Gmail";
//        public static final String COL_5="Password";
//        public DatabaseHelper(Context context) {
//            super(context, DATABASE_NAME, null, 1);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone TEXT,Gmail TEXT,Password TEXT)");
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
//            onCreate(db);
//        }
//    }
//


