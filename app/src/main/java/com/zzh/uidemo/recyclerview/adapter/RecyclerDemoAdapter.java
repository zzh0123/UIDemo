package com.zzh.uidemo.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zzh.uidemo.R;
import com.zzh.uidemo.glide.Glide;

import java.util.List;

import com.zzh.uidemo.bean.TestBean;

/**
 * Author: zzhh
 * Date: 2021/02/18
 * Description:
 */
public class RecyclerDemoAdapter extends RecyclerView.Adapter<RecyclerDemoAdapter.ViewHolder> {

    private Context mContext;
    private List<TestBean> mTestBeanList;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerDemoAdapter(Context context, List<TestBean> testBeanList) {
        mContext = context;
        mTestBeanList = testBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestBean bean = mTestBeanList.get(position);
        if (bean != null){
            holder.tvItemName.setText(bean.getTitle());
            String imgUrl = bean.getImgUrl();
            Glide.with(mContext)
                    .load(imgUrl)
                    .sizeMultiplier(0.5f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivImg);
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTestBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        RelativeLayout rlItem;
        ImageView ivImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}
