package com.bit7skes.mockupbrowser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bit7skes.mockupbrowser.database.AppDatabase;
import com.bit7skes.mockupbrowser.database.ItemEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BrowserAdapter.BrowserAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private BrowserAdapter mBrowserAdapter;
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addItemIntent);
            }
        });

        mDatabase = AppDatabase.getsInstance(getApplicationContext());

        mRecyclerView = findViewById(R.id.recycler_view);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);

        mBrowserAdapter = new BrowserAdapter(this);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mBrowserAdapter);

        loadItems();
    }

    private void loadItems() {
        LiveData<List<ItemEntry>> itemEntries = mDatabase.itemDao().getAllItems();
        itemEntries.observe(this, new Observer<List<ItemEntry>>() {
            @Override
            public void onChanged(List<ItemEntry> itemEntryList) {
                mBrowserAdapter.setData(itemEntryList);
            }
        });
    }

    @Override
    public void onClick(int itemId) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("itemId", itemId);
        startActivity(intentToStartDetailActivity);
    }
}
