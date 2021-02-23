package com.zzh.uidemo.recyclerview.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.bean.InnerBean;

import java.util.List;

/**
 * @author: zzh
 * data : 2021/01/26
 * description：装备类型列表适配器-内
 */
public class EquipItemInnerAdapter extends RecyclerView.Adapter<EquipItemInnerAdapter.ViewHolder> {
    private Context mContext;
    private List<InnerBean> mList;
    private OnInnerItemClickListener onInnerItemClickListener;
    private int mPosition;

    public EquipItemInnerAdapter(Context context, List<InnerBean> list, int position) {
        mContext = context;
        mList = list;
        mPosition = position;
    }

    /**
     * 新增方法
     *
     * @param position 位置
     */
    public void setPosition(int position) {
        this.mPosition = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equip_type_item_right_inner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNameInner.setTag(position);
        InnerBean innerBean = mList.get(position);
        if (innerBean != null){
//            LogUtil.i("zzz1", "inner " + this);
            holder.tvNameInner.setText(innerBean.getName());
            if (innerBean.isChecked()){
                holder.tvNameInner.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue6_light));
                holder.tvNameInner.setTextColor(mContext.getResources().getColor(R.color.c_5660FF));
            } else {
                holder.tvNameInner.setBackground(mContext.getResources().getDrawable(R.drawable.bg_grey1));
                holder.tvNameInner.setTextColor(mContext.getResources().getColor(R.color.c_272848));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameInner;

        ViewHolder(View view) {
            super(view);
            tvNameInner = view.findViewById(R.id.tv_name_inner);
            //这里设置一个tag,作为被嵌套的recycleview item点击事件的 position
            tvNameInner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mList.get((int) v.getTag()).setSelect(true);
//                    ((SpecialColumnActivity) mContext).OnClickListener(mPosition, (int) v.getTag());
                    onInnerItemClickListener.onInnerItemClick(mPosition, (int) v.getTag());
                }
            });


        }
    }


    public void setInnerOnItemClickListener(OnInnerItemClickListener listener) {
        this.onInnerItemClickListener = listener;

    }
    public interface OnInnerItemClickListener {
        /**
         * @param position 为第一层recycleview位置
         * @param tag      为第二层recycleview位置
         */
        void onInnerItemClick(int position, int tag);
    }
}