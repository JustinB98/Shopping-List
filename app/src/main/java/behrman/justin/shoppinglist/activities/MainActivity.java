package behrman.justin.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;
import behrman.justin.shoppinglist.dialog.NewShoppingItemDialog;

public class MainActivity extends AppCompatActivity {

    private NewShoppingItemDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new NewShoppingItemDialog(this, new Consumer<ShoppingItem>() {
            @Override
            public void accept(ShoppingItem shoppingItem) {
                addItem(shoppingItem);
            }
        });
        List<ShoppingItem> items = Arrays.asList(
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Good item", "Good description", 6.90, Category.BOOK, true),
                new ShoppingItem("Bad item", "Bad description", 6.13, Category.ELECTRONIC, false)
        );
        RecyclerView recyclerView = findViewById(R.id.list_recycler);
        recyclerView.setAdapter(new ShoppingListAdapter(items));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addItem(ShoppingItem item) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    public void showAddItemPopUp(View view) {
        dialog.show();
    }
}
