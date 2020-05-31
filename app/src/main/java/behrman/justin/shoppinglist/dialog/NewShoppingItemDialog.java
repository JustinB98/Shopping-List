package behrman.justin.shoppinglist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.util.Consumer;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class NewShoppingItemDialog {

    private Consumer<ShoppingItem> onFinish;
    private Dialog dialog;
    private EditText nameText, costText, descriptionText;
    private CheckBox purchasedBox;
    private Spinner categorySpinner;

    public NewShoppingItemDialog(Context context, Consumer<ShoppingItem> onFinish) {
        this.onFinish = onFinish;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_layout);
        extractViews();
        initSpinner();
        initBtnListener();
    }

    private void initSpinner() {
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<Category>(dialog.getContext(), android.R.layout.simple_spinner_item, Category.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private void onSave() {
        String itemName = nameText.getText().toString();
        double estimatedCost = Double.parseDouble(costText.getText().toString());
        String description = descriptionText.getText().toString();
        boolean purchased = purchasedBox.isChecked();
        ShoppingItem item = new ShoppingItem(itemName, description, estimatedCost, (Category) categorySpinner.getSelectedItem(), purchased);
        this.onFinish.accept(item);
    }

    private void initBtnListener() {
        Button saveBtn = dialog.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
                dialog.dismiss();
            }
        });
        Button exitBtn = dialog.findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void extractViews() {
        nameText = dialog.findViewById(R.id.nameText);
        costText = dialog.findViewById(R.id.costText);
        descriptionText = dialog.findViewById(R.id.descriptionText);
        purchasedBox = dialog.findViewById(R.id.alreadyPurchasedBox);
        categorySpinner = dialog.findViewById(R.id.categorySpinner);
    }

    public void show() {
        nameText.setText("");
        costText.setText("");
        descriptionText.setText("");
        purchasedBox.setChecked(false);
        categorySpinner.setSelection(0);
        nameText.requestFocus();
        dialog.show();
    }

}
