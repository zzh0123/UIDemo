package com.zzh.uidemo.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zzh.uidemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Author: zzhh
 * Date: 2020/12/14 21:51
 * Description: viewpager示例
 * 1.PagerAdapter加载view
 * 2.
 *
 *
 * 参考博客：https://blog.csdn.net/wangjinyu501/article/details/8169924
 *
 */
public class ViewPagerShowViewActivity1 extends AppCompatActivity implements View.OnClickListener{

//    @BindView(R.id.viewPager1)
    ViewPager viewPager1;

    private MyPagerAdapter myPagerAdapter;
    private List<View> mPageList;
    private View page1, page2, page3; // ViewPager包含的页面

//    @BindView(R.id.tv_page1)
    TextView tv_page1;
//    @BindView(R.id.tv_page2)
    TextView tv_page2;
//    @BindView(R.id.tv_page3)
    TextView tv_page3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_show_view1);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        viewPager1 = findViewById(R.id.viewPager1);

        mPageList = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        page1 = inflater.inflate(R.layout.page1, null);
        page2 = inflater.inflate(R.layout.page2, null);
        page3 = inflater.inflate(R.layout.page3, null);

        tv_page1 = page1.findViewById(R.id.tv_page1);
        tv_page2 = page2.findViewById(R.id.tv_page2);
        tv_page3 = page3.findViewById(R.id.tv_page3);
        tv_page1.setOnClickListener(this);
        tv_page2.setOnClickListener(this);
        tv_page3.setOnClickListener(this);

        mPageList.add(page1);
        mPageList.add(page2);
        mPageList.add(page3);
        myPagerAdapter = new MyPagerAdapter(mPageList);
        viewPager1.setAdapter(myPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_page1:
                Toast.makeText(this, "page1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_page2:
                Toast.makeText(this, "page2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_page3:
                Toast.makeText(this, "page3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
