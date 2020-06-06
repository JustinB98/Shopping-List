package behrman.justin.shoppinglist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import behrman.justin.shoppinglist.constants.DatabaseConstants;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class ShoppingItemDataSource {

    private static final String TAG = ShoppingItemDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private ShoppingItemDBHelper dbHelper;

    private final static String TABLE_NAME = DatabaseConstants.TABLE_NAME;

    public ShoppingItemDataSource(Context context) {
        dbHelper = new ShoppingItemDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues buildShippingItemValues(ShoppingItem item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("description", item.getDescription());
        values.put("estimatedPrice", item.getEstimatedPrice());
        values.put("category", item.getCategory().name());
        values.put("purchased", item.isPurchased() ? 1 : 0);
        return values;
    }

    public long insertShoppingItem(ShoppingItem item) {
        try {
            ContentValues initValues = buildShippingItemValues(item);
            long newId = database.insert(TABLE_NAME, null, initValues);
            Log.i(TAG, "New Database Row: " + newId);
            return newId;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return -1;
    }

    public long updateShoppingItem(ShoppingItem item) {
        try {
            long rowId = item.getId();
            ContentValues updateValues = buildShippingItemValues(item);
            long rowsAffected = database.update(TABLE_NAME, updateValues, "_id=" + rowId, null);
            Log.i(TAG, "Updated item: " + item.getId());
            return rowsAffected;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return -1;
        }
    }

    public boolean putShoppingItemInDb(ShoppingItem item) {
        if (item.getId() < 0) {
            long newId = insertShoppingItem(item);
            if (newId < 0) return false;
            item.setId(newId);
            return true;
        } else {
            return updateShoppingItem(item) < 0;
        }
    }

    private ShoppingItem unpackCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("_id");
        int nameIndex = cursor.getColumnIndex("name");
        int descriptionIndex = cursor.getColumnIndex("description");
        int estimatedPriceIndex = cursor.getColumnIndex("estimatedPrice");
        int categoryIndex = cursor.getColumnIndex("category");
        int purchasedIndex = cursor.getColumnIndex("purchased");
        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        String description = cursor.getString(descriptionIndex);
        int estimatedPrice = cursor.getInt(estimatedPriceIndex);
        String categoryString = cursor.getString(categoryIndex);
        boolean purchased = cursor.getInt(purchasedIndex) != 0;
        Category category = Category.valueOf(categoryString);
        ShoppingItem item = new ShoppingItem(name, description, estimatedPrice, category, purchased);
        item.setId(id);
        return item;
    }

    public List<ShoppingItem> loadData() {
        try {
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            int len = cursor.getCount();
            List<ShoppingItem> items = new ArrayList<>(len);
            for (int i = 0; i < len; ++i) {
                ShoppingItem item = unpackCursor(cursor);
                items.add(item);
                cursor.moveToNext();
            }
            cursor.close();
            return items;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public void clearShoppingItems() {
        try {
            database.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void deleteShoppingItem(ShoppingItem item) {
        try {
            database.delete(TABLE_NAME, "_id=" + item.getId(), null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

}
