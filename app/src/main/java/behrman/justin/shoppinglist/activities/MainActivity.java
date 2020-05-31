package behrman.justin.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ItemManager;
import behrman.justin.shoppinglist.model.ShoppingItem;
import behrman.justin.shoppinglist.dialog.NewShoppingItemDialog;

public class MainActivity extends AppCompatActivity {

    private NewShoppingItemDialog dialog;
    private List<ShoppingItem> items;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new NewShoppingItemDialog(this);
        items = ItemManager.getShoppingItems(this);
        //items.add(new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true));
        RecyclerView recyclerView = findViewById(R.id.list_recycler);
        adapter = new ShoppingListAdapter(items, dialog, new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                ItemManager.saveItems(MainActivity.this, items);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addItem(ShoppingItem item) {
        items.add(item);
        ItemManager.saveItems(this, items);
        adapter.notifyDataSetChanged();
    }

    public void showAddItemPopUp(View view) {
        dialog.show(new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                addItem(shoppingItem);
            }
        });
    }
}
