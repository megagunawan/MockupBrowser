package com.bit7skes.mockupbrowser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bit7skes.mockupbrowser.database.AppDatabase;
import com.bit7skes.mockupbrowser.database.AppExecutors;
import com.bit7skes.mockupbrowser.database.ItemEntry;

public class DetailActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mURLEditText;
    private Button mEditButton;
    private Button mOpenUrlButton;

    private int mItemId;
    private ItemEntry item;

    private AppDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        mDatabase = AppDatabase.getsInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("itemId")) {
            mItemId = intent.getIntExtra("itemId", 0);
            EditItemViewModelFactory factory = new EditItemViewModelFactory(mDatabase, mItemId);
            final EditItemViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(EditItemViewModel.class);

            viewModel.getTask().observe(this, new Observer<ItemEntry>() {
                @Override
                public void onChanged(@Nullable ItemEntry itemEntry) {
                    viewModel.getTask().removeObserver(this);
                    item = itemEntry;
                    populateUI(itemEntry);
                }
            });
        }
    }

    private void initViews() {
        mNameEditText = findViewById(R.id.nameEditText);
        mURLEditText = findViewById(R.id.urlEditText);
        mEditButton = findViewById(R.id.editButton);

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditButtonClicked();
            }
        });

        mOpenUrlButton = findViewById(R.id.openURLButton);
        mOpenUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenUrlButtonClicked();
            }
        });
    }

    private void populateUI(ItemEntry itemEntry) {
        if (itemEntry == null) {
            return;
        }
        mNameEditText.setText(itemEntry.getItemTitle());
        mURLEditText.setText(itemEntry.getItemURL());
    }

    public void onEditButtonClicked() {
        mEditButton.setText("SAVE");
        mNameEditText.setEnabled(true);
        mURLEditText.setEnabled(true);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        if (mNameEditText.getText().toString().equals("") || mURLEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Input cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if (!URLUtil.isValidUrl(mURLEditText.getText().toString())) {
            Toast.makeText(this, "URL is invalid.", Toast.LENGTH_SHORT).show();
        } else {
            final String nameText = mNameEditText.getText().toString();
            final String urlText = mURLEditText.getText().toString();

            final ItemEntry item = new ItemEntry(nameText, urlText);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    item.setItemId(mItemId);
                    mDatabase.itemDao().updateItem(item);
                    finish();
                }
            });
        }
    }

    public void onOpenUrlButtonClicked() {
        Context context = this;
        Class destinationClass = WebViewActivity.class;
        Intent intentToOpenWebPage = new Intent(context, destinationClass);
        intentToOpenWebPage.putExtra("webURL", item.getItemURL());
        startActivity(intentToOpenWebPage);
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
