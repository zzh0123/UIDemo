package com.zzh.uidemo.choose.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zzh.uidemo.R;
import com.zzh.uidemo.choose.bean.Area;

/**
 * @author: zzh
 * data : 2020/06/12
 * description：地区列表适配器
 */
public class AreaAdapter extends BaseQuickAdapter<Area, BaseViewHolder> {

    public AreaAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, Area bean) {
        holder.setText(R.id.tv_province_city_area, bean.getName()); // 地区名称
    }
}
