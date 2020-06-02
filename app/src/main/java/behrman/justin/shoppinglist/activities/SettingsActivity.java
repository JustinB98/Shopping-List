package behrman.justin.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        extractViews();
        initSpinner();
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
        SortingSettings.setSortingOption(option);
        int radioBtnId = sortOrderGroup.getCheckedRadioButtonId();
        if (radioBtnId == R.id.descendingRadioBtn) {
            SortingSettings.setToDescending();
        } else {
            SortingSettings.setToAscending();
        }
        finish();
    }

}
