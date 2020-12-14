package com.zzh.uidemo.viewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Author: zzhh
 * Date: 2020/12/14 21:51
 * Description: viewpager适配器
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<View> mPageList;

    public MyPagerAdapter(List<View> pageList){
        this.mPageList = pageList;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        if (mPageList == null){
            return 0;
        }
        return mPageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mPageList.get(position), 0);
        return mPageList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mPageList.get(position));
    }
}
