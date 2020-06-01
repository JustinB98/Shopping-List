package behrman.justin.shoppinglist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class NewShoppingItemDialog {

    private Consumer<ShoppingItem> onFinish;
    private AlertDialog dialog;
    private View popupView;
    private EditText nameText, costText, descriptionText;
    private CheckBox purchasedBox;
    private Spinner categorySpinner;

    private final static ShoppingItem DEFAULT_SHOPPING_ITEM = new ShoppingItem("", "", 0, Category.values()[0], false);

    public NewShoppingItemDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(R.string.app_name);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onSave(dialogInterface);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
        builder.setView(popupView);
        dialog = builder.create();
        extractViews();
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<Category>(dialog.getContext(), android.R.layout.simple_spinner_item, Category.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private boolean invalidFields(String... strings) {
        for (String s : strings) {
            if (s.isEmpty()) {
                Toast.makeText(dialog.getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    private void onSave(DialogInterface dialogInterface) {
        String itemName = nameText.getText().toString().trim();
        String estimatedCostString = costText.getText().toString().trim();
        String description = descriptionText.getText().toString().trim();
        if (invalidFields(itemName, estimatedCostString, description)) return;
        int estimatedCost = Integer.parseInt(estimatedCostString);
        boolean purchased = purchasedBox.isChecked();
        ShoppingItem item = new ShoppingItem(itemName, description, estimatedCost, (Category) categorySpinner.getSelectedItem(), purchased);
        this.onFinish.accept(item);
        dialogInterface.dismiss();
    }

    private void extractViews() {
        nameText = popupView.findViewById(R.id.nameText);
        costText = popupView.findViewById(R.id.costText);
        descriptionText = popupView.findViewById(R.id.descriptionText);
        purchasedBox = popupView.findViewById(R.id.alreadyPurchasedBox);
        categorySpinner = popupView.findViewById(R.id.categorySpinner);
    }

    public void show(ShoppingItem initialSettings, Consumer<ShoppingItem> onSave) {
        /* Although not thread safe, and provides a global state, it does make it easier */
        /* than setting the button's onClick every time */
        this.onFinish = onSave;
        nameText.setText(initialSettings.getName());
        costText.setText(initialSettings.getEstimatedPrice() + "");
        descriptionText.setText(initialSettings.getDescription());
        purchasedBox.setChecked(initialSettings.isPurchased());
        categorySpinner.setSelection(initialSettings.getCategory().ordinal());
        nameText.requestFocus();
        dialog.show();
    }

    public void show(Consumer<ShoppingItem> onSave) {
        this.show(DEFAULT_SHOPPING_ITEM, onSave);
    }

}
