package behrman.justin.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
import behrman.justin.shoppinglist.constants.SharedPreferencesConstants;
import behrman.justin.shoppinglist.db.ShoppingItemDataSource;
import behrman.justin.shoppinglist.dialog.DialogUtils;
import behrman.justin.shoppinglist.dialog.EditShoppingItemDialog;
import behrman.justin.shoppinglist.model.ShoppingItem;
import behrman.justin.shoppinglist.model.SortingOption;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditShoppingItemDialog dialog;
    private List<ShoppingItem> displayedItems;
    private ShoppingListAdapter adapter;
    private ShoppingItemDataSource database;
    private TextView infoView;
    private RecyclerView recyclerView;

    private Snackbar currentlyShownSnackbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public void removeAllItems(MenuItem item) {
        DialogUtils.showConfirmDialog(this, R.string.delete_items_confirm_title, R.string.delete_items_confirm_description, new Runnable() {
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

    @Override
    protected void onStop() {
        if (currentlyShownSnackbar != null) {
            currentlyShownSnackbar.dismiss();
        }
        super.onStop();
    }

    public void showAllItems(MenuItem menuItem) {
        for (ShoppingItem item : displayedItems) {
            item.setHideDetails(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void hideAllItems(MenuItem menuItem) {
        for (ShoppingItem item : displayedItems) {
            item.setHideDetails(true);
        }
        adapter.notifyDataSetChanged();
    }

    private void removeAllItems() {
        displayedItems.clear();
        database.open();
        database.clearShoppingItems();
        database.close();
        showInfoView();
        adapter.notifyDataSetChanged();
    }

    private void saveItem(ShoppingItem item) {
        database.open();
        database.putShoppingItemInDb(item);
        database.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        infoView = findViewById(R.id.infoView);
        initData();
        initRecyclerView();
    }

    private void initData() {
        database = new ShoppingItemDataSource(this);
        database.open();
        displayedItems = database.loadData();
        if (displayedItems == null) {
            Toast.makeText(this, R.string.loading_items_error, Toast.LENGTH_LONG).show();
            displayedItems = new ArrayList<>();
        }
        database.close();
        dialog = new EditShoppingItemDialog(getSupportFragmentManager());
        if (displayedItems.isEmpty()) {
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
        recyclerView = findViewById(R.id.list_recycler);
        adapter = new ShoppingListAdapter(this, displayedItems, dialog, new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                saveItem(shoppingItem);
                sortList();
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
                ShoppingItem item = displayedItems.remove(position);
                adapter.notifyItemRemoved(position);
                createSnackBar(item, position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void createSnackBar(final ShoppingItem item, final int position) {
        currentlyShownSnackbar = Snackbar.make(recyclerView, "Removed " + item.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedItems.add(position, item);
                adapter.notifyItemInserted(position);
            }
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                /* If the event was pressing the undo btn, don't remove from database */
                if (event == Snackbar.Callback.DISMISS_EVENT_ACTION) return;
                removeItemFromDatabase(item);
            }
        });
        currentlyShownSnackbar.show();
    }

    private void removeItemFromDatabase(ShoppingItem item) {
        deleteItem(item);
        if (displayedItems.isEmpty()) {
            showInfoView();
        }
    }

    private void deleteItem(ShoppingItem item) {
        database.open();
        database.deleteShoppingItem(item);
        database.close();
    }

    private void addItem(ShoppingItem item) {
        displayedItems.add(item);
        saveItem(item);
        hideInfoView();
        adapter.notifyItemInserted(displayedItems.size() - 1);
        sortList();
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
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesConstants.FILE_NAME, Context.MODE_PRIVATE);
        String sorterStr = sharedPreferences.getString(SharedPreferencesConstants.SORTING_OPTION_KEY, SortingOption.DATE_ADDED.name());
        boolean ascending = sharedPreferences.getInt(SharedPreferencesConstants.ASCENDING_KEY, 1) != 0;
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
