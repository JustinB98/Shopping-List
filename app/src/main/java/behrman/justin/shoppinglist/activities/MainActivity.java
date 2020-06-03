package behrman.justin.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
import behrman.justin.shoppinglist.db.ShoppingItemDataSource;
import behrman.justin.shoppinglist.dialog.DialogUtils;
import behrman.justin.shoppinglist.dialog.EditShoppingItemDialog;
import behrman.justin.shoppinglist.model.ShoppingItem;
import behrman.justin.shoppinglist.model.SortingOption;

public class MainActivity extends AppCompatActivity {

    private EditShoppingItemDialog dialog;
    private List<ShoppingItem> items, displayedItems;
    private ShoppingListAdapter adapter;
    private ShoppingItemDataSource db;
    private TextView infoView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public void removeAllItems(MenuItem item) {
        DialogUtils.showConfirmDialog(this, "Delete All Items?", "Are you sure you want to delete all items?", new Runnable() {
            @Override
            public void run() {
                removeAllItems();
            }
        });
    }

    public void sortItemsSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sortList();
    }

    public void showAllItems(MenuItem menuItem) {
        for (ShoppingItem item : items) {
            item.setHideDetails(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void hideAllItems(MenuItem menuItem) {
        for (ShoppingItem item : items) {
            item.setHideDetails(true);
        }
        adapter.notifyDataSetChanged();
    }

    private void removeAllItems() {
        items.clear();
        displayedItems.clear();
        db.open();
        db.clearShoppingItems();
        db.close();
        showInfoView();
        adapter.notifyDataSetChanged();
    }

    private void saveItem(ShoppingItem item) {
        db.open();
        db.putShoppingItemInDb(item);
        db.close();
        sortList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoView = findViewById(R.id.infoView);
        initData();
        initRecyclerView();
    }

    private void initData() {
        db = new ShoppingItemDataSource(this);
        db.open();
        items = db.loadData();
        if (items == null) {
            Toast.makeText(this, "Trouble loading items", Toast.LENGTH_LONG).show();
            items = new ArrayList<>();
        }
        db.close();
        dialog = new EditShoppingItemDialog(getSupportFragmentManager());
        displayedItems = new ArrayList<>();
        displayedItems.addAll(items);
        if (items.isEmpty()) {
            showInfoView();
        } else {
            hideInfoView();
        }
    }

    private void showInfoView() {
        infoView.setVisibility(View.VISIBLE);
    }

    private void hideInfoView() {
        infoView.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.list_recycler);
        adapter = new ShoppingListAdapter(this, displayedItems, dialog, new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                saveItem(shoppingItem);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initDeleteOnSwipe(recyclerView);
    }

    private void initDeleteOnSwipe(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                removeItem(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeItem(int position) {
        ShoppingItem item = items.get(position);
        items.remove(position);
        displayedItems.remove(position);
        deleteItem(item);
        if (items.isEmpty()) {
            showInfoView();
        }
        adapter.notifyDataSetChanged();
    }

    private void deleteItem(ShoppingItem item) {
        db.open();
        db.deleteShoppingItem(item);
        db.close();
    }

    private void addItem(ShoppingItem item) {
        items.add(item);
        displayedItems.add(item);
        saveItem(item);
        hideInfoView();
        adapter.notifyDataSetChanged();
    }

    public void showAddItemPopUp(View view) {
        dialog.show(getSupportFragmentManager(), new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                addItem(shoppingItem);
            }
        });
    }

    private void sortList() {
        SharedPreferences sharedPreferences = getSharedPreferences("behrman.justin.shoppinglist.settings", Context.MODE_PRIVATE);
        String sorterStr = sharedPreferences.getString("sortingOption", "DATE_ADDED");
        boolean ascending = sharedPreferences.getInt("ascending", 1) != 0;
        final SortingOption sorter = SortingOption.valueOf(sorterStr);
        Comparator<ShoppingItem> comparator;
        if (ascending) {
            comparator = sorter.getComparator();
        } else {
            comparator = new Comparator<ShoppingItem>() {
                @Override
                public int compare(ShoppingItem si1, ShoppingItem si2) {
                    return sorter.getComparator().compare(si2, si1);
                }
            };
        }
        Collections.sort(displayedItems, comparator);
        adapter.notifyDataSetChanged();
    }

}
