package behrman.justin.shoppinglist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingItemDBHelper extends SQLiteOpenHelper {

    private static final String TAG = ShoppingItemDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "myshoppingitems.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "shoppingitem";

    private static final String CREATE_SHOPPING_ITEM_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "description TEXT, estimatedPrice INTEGER," +
                    "category TEXT, purchased INTEGER)";

    public ShoppingItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SHOPPING_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database " + DATABASE_NAME + ", deleting all data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
