package com.bit7skes.mockupbrowser;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bit7skes.mockupbrowser.database.AppDatabase;
import com.bit7skes.mockupbrowser.database.AppExecutors;
import com.bit7skes.mockupbrowser.database.ItemEntry;

public class AddActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mURLEditText;
    private Button mAddButton;

    private AppDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        mDatabase = AppDatabase.getsInstance(getApplicationContext());
    }

    private void initViews() {
        mNameEditText = findViewById(R.id.nameEditText);
        mURLEditText = findViewById(R.id.urlEditText);

        mAddButton = findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
    }

    public void onAddButtonClicked() {
        if (mNameEditText.getText().toString() == "" || mURLEditText.getText().toString() == "") {
            Toast.makeText(this, "Input cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if (!URLUtil.isValidUrl(mURLEditText.getText().toString())) {
            Toast.makeText(this, "URL is invalid.", Toast.LENGTH_SHORT).show();
        } else {
            String nameText = mNameEditText.getText().toString();
            String urlText = mURLEditText.getText().toString();

            final ItemEntry item = new ItemEntry(nameText, urlText);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDatabase.itemDao().insertItem(item);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
