package com.zzh.uidemo.recyclerview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zzh.uidemo.R;
import com.zzh.uidemo.glide.Glide;
import com.zzh.uidemo.recyclerview.bean.CheckItemBean;

import java.util.List;


public class CheckItemAdapter extends RecyclerView.Adapter<CheckItemAdapter.ViewHolder> {

    private Context context;
    /**
     * 数据
     */
    private List<CheckItemBean> checkItemBeanList;
    private OnItemClickListener onItemClickListener;

    public CheckItemAdapter(Context context, List<CheckItemBean> checkItemBeanList) {
        this.context = context;
        this.checkItemBeanList = checkItemBeanList;
    }

    @Override
    public CheckItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check, parent, false);
        CheckItemAdapter.ViewHolder vh = new CheckItemAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CheckItemAdapter.ViewHolder holder, final int position) {
        CheckItemBean bean = checkItemBeanList.get(position);
        if (bean != null){
            // 标题
            holder.tvName.setText(bean.getTitle());
            // 选中
            if (bean.isChecked()){
                holder.ivCheck.setImageResource(R.mipmap.checked);
            } else {
                holder.ivCheck.setImageResource(R.mipmap.unchecked);
            }
            holder.ivCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            // -
            holder.tvReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            // +
            holder.tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

            // 数量
            holder.tvCount.setText("" + bean.getEditNum());
            holder.tvCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

            String imageUrl = bean.getImgUrl();
            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(context)
                        .load(imageUrl)
                        .sizeMultiplier(0.5f)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .error(R.mipmap.ic_launcher)
                        .into(holder.imgForm);
            }

            // 查看详情
            holder.llSeeDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (checkItemBeanList != null) {
            return checkItemBeanList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCheck; // 选中
        private TextView tvName; // 名称
        private TextView tvReduce; // -
        private TextView tvPlus; // +
        private TextView tvCount; // 数量
        private TextView tvProduceTime; // 生产日期
        private TextView tvAmount; // 总数量
        private TextView tvMaintenanceStatus; // 保养
        private TextView tvRepairStatus; // 维修
        private ImageView imgForm; // 图片
        private LinearLayout llSeeDetail; // 图片

        public ViewHolder(View itemView) {
            super(itemView);
            ivCheck = itemView.findViewById(R.id.iv_check);
            tvName = itemView.findViewById(R.id.tv_name);
            tvReduce = itemView.findViewById(R.id.tv_reduce);
            tvPlus = itemView.findViewById(R.id.tv_plus);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvProduceTime = itemView.findViewById(R.id.tv_produce_time);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            imgForm = itemView.findViewById(R.id.img_form);
            llSeeDetail = itemView.findViewById(R.id.ll_see_detail);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }
    
}
