package behrman.justin.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.adapters.ShoppingListAdapter;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
