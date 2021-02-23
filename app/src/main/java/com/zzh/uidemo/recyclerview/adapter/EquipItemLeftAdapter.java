package com.zzh.uidemo.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.bean.LeftBean;

import java.util.List;


/**
 * @author: zzh
 * data : 2021/01/26
 * description：装备类型列表适配器-左
 */
public class EquipItemLeftAdapter extends RecyclerView.Adapter<EquipItemLeftAdapter.ViewHolder> {

    private Context context;
    /**
     * 数据
     */
    private List<LeftBean> checkApplyBeanList;
    private OnItemClickListener onItemClickListener;

    public EquipItemLeftAdapter(Context context, List<LeftBean> checkApplyBeanList) {
        this.context = context;
        this.checkApplyBeanList = checkApplyBeanList;
    }

    @Override
    public EquipItemLeftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.equip_type_item_left, parent, false);
        EquipItemLeftAdapter.ViewHolder vh = new EquipItemLeftAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final EquipItemLeftAdapter.ViewHolder holder, final int position) {
        LeftBean bean = checkApplyBeanList.get(position);
        if (bean != null){
            // 标题
            holder.tvName.setText(bean.getName());
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

            if (bean.isChecked()){
                holder.tvName.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.c_272848));
                holder.tvName.getPaint().setFakeBoldText(true);
            } else {
                holder.tvName.setBackgroundColor(context.getResources().getColor(R.color.c_F5F6FA));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.c_B0B3BE));
                holder.tvName.getPaint().setFakeBoldText(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (checkApplyBeanList != null) {
            return checkApplyBeanList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName; // 名称

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }


    
}
