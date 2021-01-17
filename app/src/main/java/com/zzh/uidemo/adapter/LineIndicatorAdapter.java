package com.zzh.uidemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzh.uidemo.DemoBean;
import com.zzh.uidemo.R;

import java.util.List;

/**
 * Author: zzhh
 * Date: 2021/01/17 21:05
 * Description:
 */
public class LineIndicatorAdapter extends RecyclerView.Adapter<LineIndicatorAdapter.ViewHolder> {

    private List<String> mItemList;

    public LineIndicatorAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_indicator, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
