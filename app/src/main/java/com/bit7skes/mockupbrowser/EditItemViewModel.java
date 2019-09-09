package com.bit7skes.mockupbrowser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bit7skes.mockupbrowser.database.AppDatabase;
import com.bit7skes.mockupbrowser.database.ItemEntry;

public class EditItemViewModel extends ViewModel {

    private LiveData<ItemEntry> item;

    public EditItemViewModel(AppDatabase database, int taskId) {
        item = database.itemDao().getItem(taskId);
    }

    public LiveData<ItemEntry> getTask() {
        return item;
    }
}

