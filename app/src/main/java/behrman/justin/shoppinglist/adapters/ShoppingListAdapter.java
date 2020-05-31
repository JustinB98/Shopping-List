package behrman.justin.shoppinglist.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.dialog.NewShoppingItemDialog;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private static final String TAG = ShoppingListAdapter.class.getSimpleName();
    private List<ShoppingItem> items;
    private NewShoppingItemDialog dialog;

    public ShoppingListAdapter(List<ShoppingItem> items, NewShoppingItemDialog dialog) {
        this.items = items;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder invoked");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item_layout, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        Log.v(TAG, "onBindViewHolder invoked for position " + position);
        holder.setWith(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView descriptionView, itemNameView, priceView;
        private CheckBox purchaseBox;
        private Button editBtn;
        private ToggleButton detailsBtn;
        private View itemView;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.categoryImage = itemView.findViewById(R.id.categoryImage);
            this.descriptionView = itemView.findViewById(R.id.descriptionView);
            this.itemNameView =  itemView.findViewById(R.id.itemNameView);
            this.priceView = itemView.findViewById(R.id.priceView);
            this.purchaseBox = itemView.findViewById(R.id.purchasedBox);
            this.editBtn = itemView.findViewById(R.id.editBtn);
            this.detailsBtn = itemView.findViewById(R.id.detailsBtn);
        }

        private void setWith(int position) {
            final ShoppingItem item = items.get(position);
            descriptionView.setText(item.getDescription());
            itemNameView.setText(item.getName());
            priceView.setText("$" + item.getEstimatedPrice());
            purchaseBox.setChecked(item.isPurchased());
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show(item, new Consumer<ShoppingItem>() {
                        @Override
                        public void accept(ShoppingItem shoppingItem) {
                            item.copy(shoppingItem);
                            ShoppingListAdapter.super.notifyDataSetChanged();
                        }
                    });
                }
            });
            purchaseBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setPurchased(purchaseBox.isChecked());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    purchaseBox.setChecked(!purchaseBox.isChecked());
                    item.setPurchased(purchaseBox.isChecked());
                }
            });
        }

    }

}
