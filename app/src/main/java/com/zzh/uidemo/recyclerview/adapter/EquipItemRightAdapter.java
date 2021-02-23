package com.zzh.uidemo.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.bean.InnerBean;
import com.zzh.uidemo.recyclerview.bean.RightBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zzh
 * data : 2021/01/26
 * description：装备类型列表适配器-右
 */
public class EquipItemRightAdapter extends RecyclerView.Adapter<EquipItemRightAdapter.ViewHolder> {
    //新增itemType
    public static final int ITEM_TYPE = 100;

    private boolean isMulti;
    private Context mContext;
    private List<RightBean> checkApplyBeanList;
    private OnItemClickListener onItemClickListener;
    private EquipItemInnerAdapter.OnInnerItemClickListener onInnerItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public EquipItemRightAdapter(Context context, List<RightBean> checkApplyBeanList, boolean isMulti) {
        mContext = context;
        this.checkApplyBeanList = checkApplyBeanList;
        this.isMulti = isMulti;
    }

    //重写改方法，设置ItemViewType
    @Override
    public int getItemViewType(int position) {
        //返回值与使用时设置的值需保持一致
        return ITEM_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equip_type_item_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RightBean rightBean = checkApplyBeanList.get(position);
        if (rightBean != null) {
            holder.tvNameSecond.setText(rightBean.getName());
            if (rightBean.isChecked()){
                holder.ivCheck.setImageResource(R.mipmap.checked);
            } else {
                holder.ivCheck.setImageResource(R.mipmap.unchecked);
            }
            if (isMulti){
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.GONE);
            }
            holder.ivCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });

            /**
             * 1.把内部RecyclerView的adapter和集合数据通过holder缓存
             * 2.使内部RecyclerView的adapter提供设置position的方法
             */
            holder.list.clear();
            List<InnerBean> itemList = checkApplyBeanList.get(position).getChildList();
            if (itemList != null && itemList.size() > 0) {
                holder.list.addAll(itemList);
            }

            if (holder.mItemAapter == null) {
                //初始化内层adapter时,把对应的position传过去
                holder.mItemAapter = new EquipItemInnerAdapter(mContext, holder.list, position);
//                LogUtil.i("zzz1", "new " +  holder.mItemAapter);
                holder.mItemAapter.setInnerOnItemClickListener(onInnerItemClickListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                holder.recyclerViewInner.setLayoutManager(layoutManager);
                holder.recyclerViewInner.setAdapter(holder.mItemAapter);
            } else {
                holder.mItemAapter.setPosition(position);
                holder.mItemAapter.notifyDataSetChanged();
            }

        }

    }

    public void setInnerListener(EquipItemInnerAdapter.OnInnerItemClickListener onInnerItemClickListener) {
        this.onInnerItemClickListener = onInnerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return checkApplyBeanList == null ? 0 : checkApplyBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameSecond;
        ImageView ivCheck;
        RecyclerView recyclerViewInner;

        private EquipItemInnerAdapter mItemAapter;
        private List<InnerBean> list = new ArrayList<>();

        ViewHolder(View view) {
            super(view);
            tvNameSecond = view.findViewById(R.id.tv_name_second);
            ivCheck = view.findViewById(R.id.iv_check);
            recyclerViewInner = view.findViewById(R.id.recyclerView_inner);
        }
    }
}
