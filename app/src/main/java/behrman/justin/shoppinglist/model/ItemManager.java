package behrman.justin.shoppinglist.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import behrman.justin.shoppinglist.R;

public class ItemManager {

    public static List<ShoppingItem> getShoppingItems(Activity activity) {
        SharedPreferences sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
        String json = sharedPrefs.getString(activity.getString(R.string.preferences_file), "[]");
        try {
            JSONArray jsonItems = new JSONArray(json);
            int len = jsonItems.length();
            List<ShoppingItem> items = new ArrayList<>(len);
            for (int i = 0; i < len; ++i) {
                JSONObject object = jsonItems.getJSONObject(i);
                ShoppingItem item = ShoppingItem.fromJSONObject(object);
                items.add(item);
            }
            return items;
        } catch (JSONException e) {
            Log.i("ItemManager", "Trouble getting items");
            return new ArrayList<>();
        }
    }

    public static void saveItems(Activity activity, List<ShoppingItem> items) {
        JSONArray jsonItems = new JSONArray();
        for (ShoppingItem item : items) {
            jsonItems.put(item.toJSONObject());
        }
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.preferences_file), jsonItems.toString());
        editor.apply();
    }

}
