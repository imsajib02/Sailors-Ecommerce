package com.b2gsoft.sailorsexpress.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.b2gsoft.sailorsexpress.model.Cart;
import com.b2gsoft.sailorsexpress.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DatabaseName = "SailorsExpressCart.db";
    private static final int DatabaseVersion = 1;

    private static final String CartTable = "CartItems";

    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_JSON = "product";

    public DBHelper(Context context)
    {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CART_TABLE = "CREATE TABLE " + CartTable + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PRODUCT_JSON + " TEXT)";

        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CartTable);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {

        db.disableWriteAheadLogging();
        super.onConfigure(db);
    }


    public void addProduct(Product product) {

        List<Cart> cartList = getCartItems();

        boolean matchFound = false;
        int index = 0;

        for(int i=0; i<cartList.size(); i++) {

            if(product.getSize() != null && cartList.get(i).getProduct().getSize() != null
                    && TextUtils.equals(product.getSize().getId(), cartList.get(i).getProduct().getSize().getId())
                    && product.getCurrentPrice() == cartList.get(i).getProduct().getCurrentPrice()) {

                index = i;
                matchFound = true;
                cartList.get(i).getProduct().setQuantity(cartList.get(i).getProduct().getQuantity() + product.getQuantity());
                break;
            }
        }

        if(matchFound) {

            updateRow(cartList.get(index).getPosition(), cartList.get(index).getProduct());
        }
        else {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_PRODUCT_JSON, new Gson().toJson(product));

            db.insert(CartTable, null, values);
            db.close();
        }
    }


    public int updateRow(int position, Product product) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_JSON, new Gson().toJson(product));

        return db.update(CartTable, values, KEY_ID + " = ?", new String[] {String.valueOf(position)});
    }


    public List<Cart> getCartItems() {

        List<Cart> cartList = new ArrayList<>();

        String query = "SELECT  * FROM " + CartTable;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(query, null);

        while(result.moveToNext())
        {
            String jsonString = result.getString(1);
            cartList.add(new Cart(result.getInt(0), new Gson().fromJson(jsonString, Product.class), true));
        }

        return cartList;
    }


    public int deleteProduct(int position) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(CartTable, KEY_ID + " = ?", new String[] { String.valueOf(position) });
    }


    public int getProductsCount() {

        String countQuery = "SELECT  * FROM " + CartTable;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
