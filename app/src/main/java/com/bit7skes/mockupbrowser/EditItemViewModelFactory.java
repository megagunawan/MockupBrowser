package com.bit7skes.mockupbrowser;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bit7skes.mockupbrowser.database.AppDatabase;

public class EditItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final int mItemId;

    public EditItemViewModelFactory(AppDatabase database, int taskId) {
        mDb = database;
        mItemId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new EditItemViewModel(mDb, mItemId);
    }
}
