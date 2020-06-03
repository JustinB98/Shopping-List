package behrman.justin.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.model.SortingOption;

public class SettingsActivity extends AppCompatActivity {

    private Spinner sortOptionsSpinner;
    private RadioGroup sortOrderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extractViews();
        initSpinner();
        setInitialValues();
    }

    private void setInitialValues() {
        SharedPreferences sharedPreferences = getSharedPreferences("behrman.justin.shoppinglist.settings", Context.MODE_PRIVATE);
        String sorterStr = sharedPreferences.getString("sortingOption", "DATE_ADDED");
        boolean ascending = sharedPreferences.getInt("ascending", 1) != 0;
        if (ascending) {
            sortOrderGroup.check(R.id.ascendingRadioBtn);
        } else {
            sortOrderGroup.check(R.id.descendingRadioBtn);
        }
        SortingOption option = SortingOption.valueOf(sorterStr);
        sortOptionsSpinner.setSelection(option.ordinal());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSpinner() {
        ArrayAdapter<SortingOption> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SortingOption.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortOptionsSpinner.setAdapter(adapter);
    }

    private void extractViews() {
        sortOptionsSpinner = findViewById(R.id.sortOptionSpinner);
        sortOrderGroup = findViewById(R.id.sortOrderGroup);
    }

    public void submit(View view) {
        SortingOption option = (SortingOption) sortOptionsSpinner.getSelectedItem();
        int radioBtnId = sortOrderGroup.getCheckedRadioButtonId();
        boolean ascending = radioBtnId != R.id.descendingRadioBtn;
        SharedPreferences sharedPreferences = getSharedPreferences("behrman.justin.shoppinglist.settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sortingOption", option.name());
        editor.putInt("ascending", ascending ? 1 : 0);
        editor.apply();
        finish();
    }

}
