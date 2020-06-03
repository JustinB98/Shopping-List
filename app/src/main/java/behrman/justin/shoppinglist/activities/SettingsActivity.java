package behrman.justin.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import behrman.justin.shoppinglist.R;
import behrman.justin.shoppinglist.model.SortingOption;
import behrman.justin.shoppinglist.model.SortingSettings;

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
        SortingSettings sortingSettings = new SortingSettings();
        sortingSettings.setSortingOption(option);
        int radioBtnId = sortOrderGroup.getCheckedRadioButtonId();
        if (radioBtnId == R.id.descendingRadioBtn) {
            sortingSettings.setToDescending();
        } else {
            /* Make default ascending */
            sortingSettings.setToAscending();
        }
        Intent intent = new Intent();
        intent.putExtra("settings", sortingSettings);
        setResult(RESULT_OK, intent);
        finish();
    }

}
