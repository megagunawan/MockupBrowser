package com.bit7skes.mockupbrowser.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem(ItemEntry itemEntry);

    @Update
    void updateItem(ItemEntry itemEntry);

    @Query("SELECT * FROM browserItem")
    LiveData<List<ItemEntry>> getAllItems();

    @Query("SELECT * FROM browserItem WHERE itemId = :id")
    LiveData<ItemEntry> getItem(int id);

}
