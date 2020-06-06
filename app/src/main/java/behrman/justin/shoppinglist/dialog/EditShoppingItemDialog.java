package behrman.justin.shoppinglist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.model.Category;
import behrman.justin.shoppinglist.model.ShoppingItem;

public class EditShoppingItemDialog extends DialogFragment {

    private FragmentManager fragmentManager;

    private Consumer<ShoppingItem> itemConsumer;
    private EditText nameText, costText, descriptionText;
    private CheckBox purchasedBox;
    private Spinner categorySpinner;

    private final static ShoppingItem DEFAULT_SHOPPING_ITEM = new ShoppingItem("", "", -1, Category.values()[0], false);

    private ShoppingItem currentItem;

    public EditShoppingItemDialog(FragmentManager fragmentManager) {
        super();
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(R.string.app_name);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveItem();
            }
        });
        builder.setNegativeButton("Cancel", null);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.popup_layout, null, false);
        builder.setView(view);
        extractViews(view);
        initSpinner();
        setUpViews();

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
                setUpTextListeners((AlertDialog) dialogInterface);
            }
        });
        return dialog;
    }

    private void setUpTextListeners(AlertDialog dialog) {
        boolean nameEmpty = currentItem.getName().trim().isEmpty();
        final Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setEnabled(!nameEmpty);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                boolean nameTextEmpty = nameText.getText().toString().trim().isEmpty();
                boolean priceTextEmpty = costText.getText().toString().trim().isEmpty();
                positiveBtn.setEnabled(!nameTextEmpty && !priceTextEmpty);
            }
        };
        nameText.addTextChangedListener(watcher);
        costText.addTextChangedListener(watcher);
    }

    private void setUpViews() {
        nameText.setText(currentItem.getName());
        if (currentItem.getEstimatedPrice() >= 0) costText.setText(currentItem.getEstimatedPrice() + "");
        else costText.setText("");
        descriptionText.setText(currentItem.getDescription());
        purchasedBox.setChecked(currentItem.isPurchased());
        categorySpinner.setSelection(currentItem.getCategory().ordinal());
        nameText.requestFocus();
    }

    private void initSpinner() {
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_item, Category.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private boolean invalidFields(String... strings) {
        for (String s : strings) {
            if (s.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    private void saveItem() {
        String itemName = nameText.getText().toString().trim();
        String estimatedCostString = costText.getText().toString().trim();
        String description = descriptionText.getText().toString().trim();
        if (invalidFields(itemName, estimatedCostString)) return;
        int estimatedCost = Integer.parseInt(estimatedCostString);
        boolean purchased = purchasedBox.isChecked();
        ShoppingItem item = new ShoppingItem(itemName, description, estimatedCost, (Category) categorySpinner.getSelectedItem(), purchased);
        this.itemConsumer.accept(item);
    }

    private void extractViews(View view) {
        nameText = view.findViewById(R.id.nameText);
        costText = view.findViewById(R.id.costText);
        descriptionText = view.findViewById(R.id.descriptionText);
        purchasedBox = view.findViewById(R.id.alreadyPurchasedBox);
        categorySpinner = view.findViewById(R.id.categorySpinner);
    }

    public void show(ShoppingItem initialSettings, Consumer<ShoppingItem> itemConsumer) {
        if (this.isAdded()) return;
        /* Although not thread safe, and provides a global state, it does make it easier */
        /* than setting the button's onClick every time */
        this.itemConsumer = itemConsumer;
        this.currentItem = initialSettings;
        this.show(this.fragmentManager, "Tag");
    }

    public void show(FragmentManager fragmentManager, Consumer<ShoppingItem> onSave) {
        this.fragmentManager = fragmentManager;
        this.show(DEFAULT_SHOPPING_ITEM, onSave);
    }
}
