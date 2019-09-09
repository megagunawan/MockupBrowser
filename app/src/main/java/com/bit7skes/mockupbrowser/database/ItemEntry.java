package com.bit7skes.mockupbrowser.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "browserItem")
public class ItemEntry {
    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private String itemTitle;
    private String itemURL;

    @Ignore
    public ItemEntry(String itemTitle, String itemURL) {
        this.itemTitle = itemTitle;
        this.itemURL = itemURL;
    }

    public ItemEntry() { }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }
}
