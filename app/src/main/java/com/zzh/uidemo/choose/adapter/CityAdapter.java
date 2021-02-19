package com.zzh.uidemo.choose.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zzh.uidemo.R;
import com.zzh.uidemo.choose.bean.City;

/**
 * @author: zzh
 * data : 2020/06/12
 * description：城市列表适配器
 */
public class CityAdapter extends BaseQuickAdapter<City, BaseViewHolder> {

    public CityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, City bean) {
        holder.setText(R.id.tv_province_city_area, bean.getName()); // 城市名称
    }
}
