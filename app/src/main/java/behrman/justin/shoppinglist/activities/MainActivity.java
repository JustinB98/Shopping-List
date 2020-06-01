package behrman.justin.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
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
                items.remove(position);
                ItemManager.saveItems(MainActivity.this, items);
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
