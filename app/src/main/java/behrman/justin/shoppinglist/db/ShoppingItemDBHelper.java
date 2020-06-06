package behrman.justin.shoppinglist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import behrman.justin.shoppinglist.constants.DatabaseConstants;

public class ShoppingItemDBHelper extends SQLiteOpenHelper {

    private static final String TAG = ShoppingItemDBHelper.class.getSimpleName();

    private static final String CREATE_SHOPPING_ITEM_TABLE =
            "CREATE TABLE " + DatabaseConstants.TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "description TEXT, estimatedPrice INTEGER," +
                    "category TEXT, purchased INTEGER)";

    public ShoppingItemDBHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SHOPPING_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database " + DatabaseConstants.DATABASE_NAME + " which is a no op. No data was deleted or modified");
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // onCreate(sqLiteDatabase);
    }
}
