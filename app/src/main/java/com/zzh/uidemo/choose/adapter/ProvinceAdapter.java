package com.zzh.uidemo.choose.adapter;

import android.widget.Filter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zzh.uidemo.R;
import com.zzh.uidemo.choose.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zzh
 * data : 2020/06/12
 * description：省列表适配器
 */
public class ProvinceAdapter extends BaseQuickAdapter<Province, BaseViewHolder> {
    private ProvinceAdapter.MyFilter myFilter;
    private List<Province> provinces;
    private List<Province> copyProvinces;
    private String previousPrefix = "";
    public ProvinceAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder holder, Province bean) {
        holder.setText(R.id.tv_province_city_area, bean.getName()); // 省名称
    }

    public Filter getFilter() {
        if (myFilter == null) {
            List<Province> originProvinces = getData();
            myFilter = new ProvinceAdapter.MyFilter(originProvinces);
        }
        return myFilter;
    }

    private class MyFilter extends Filter {
        List<Province> originProvinces;

        MyFilter(List<Province> originProvinces) {
            this.originProvinces = originProvinces;
            provinces = originProvinces;
            copyProvinces = new ArrayList<>();
            copyProvinces.addAll(originProvinces);
        }

        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (originProvinces == null) {
                originProvinces = new ArrayList<>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyProvinces;
                results.count = copyProvinces.size();
            } else {
                String prefixString = prefix.toString();
                int count = originProvinces.size();
                if (previousPrefix.length() > prefixString.length()) {
                    originProvinces = copyProvinces;
                }
                previousPrefix = prefixString;
                ArrayList<Province> newValues = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Province provinceBean = originProvinces.get(i);
                    String name = provinceBean.getName();
                    if (name.contains(prefixString)) { // 输入内容不完整时搜索到多条符合条件的信息
                        newValues.add(provinceBean);
                    } else if (prefixString.contains(name)) {
                        newValues.add(provinceBean);
                    }
                    results.values = newValues;
                    results.count = newValues.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
            provinces.clear();
            if (results.count > 0) {
                provinces.addAll((List<Province>) results.values);
            }
            notifyDataSetChanged();
        }

    }
}
