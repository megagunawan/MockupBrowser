package com.bit7skes.mockupbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit7skes.mockupbrowser.database.ItemEntry;

import java.util.ArrayList;
import java.util.List;

public class BrowserAdapter extends RecyclerView.Adapter<BrowserAdapter.PlaceViewHolder> {

    private Context mContext;
    private List<Integer> itemIds = new ArrayList<>();
    private List<String> itemTitles = new ArrayList<>();
    private Integer itemId;
    private BrowserAdapterOnClickHandler mClickHandler;

    public interface BrowserAdapterOnClickHandler {
        void onClick(int itemId);
    }

    public BrowserAdapter(BrowserAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_custom_layout, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.mText.setText(itemTitles.get(position));
    }

    @Override
    public int getItemCount() {
        if (itemTitles != null) {
            return itemTitles.size();
        } else return 0;
    }

    void setData(List<ItemEntry> itemEntryList) {
        itemIds = new ArrayList<>();
        itemTitles = new ArrayList<>();
        for (ItemEntry item: itemEntryList) {
            itemIds.add(item.getItemId());
            itemTitles.add(item.getItemTitle());
        }
        notifyDataSetChanged();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mText;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            itemId = itemIds.get(adapterPosition);
            try {
                mClickHandler.onClick(itemId);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
