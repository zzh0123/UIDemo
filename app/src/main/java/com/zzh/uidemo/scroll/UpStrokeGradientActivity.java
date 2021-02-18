package com.zzh.uidemo.scroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzh.uidemo.R;
import com.zzh.uidemo.adapter.LineIndicatorAdapter;

import java.util.ArrayList;
import java.util.List;

import utils.DpUtil;

public class UpStrokeGradientActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LineIndicatorAdapter adapter;
    private List<String> mItemList = new ArrayList<>();

    private NestedScrollView nestedScrollView;
    private RelativeLayout rl_title;
    private ImageView iv_back;
    private TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // https://blog.csdn.net/jiejingguo/article/details/80133880
        //方式一：这句代码必须写在setContentView()方法的后面
//        getSupportActionBar().hide();
        //方式二：这句代码必须写在setContentView()方法的前面
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_stroke_gradient);

        rl_title = findViewById(R.id.rl_title);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);

        for (int i = 0; i < 10; i++){
            mItemList.add("" + i);
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LineIndicatorAdapter(mItemList);
        recyclerView.setAdapter(adapter);

        nestedScrollView = findViewById(R.id.nestedScrollView);
        // 标题滑动渐变
        int height = DpUtil.dip2px(this, 45);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 0) {
                    //顶部图处于最顶部，标题栏透明
                    iv_back.setImageResource(R.mipmap.back_white);
                    tv_title.setVisibility(View.GONE);
                    float scale = (float) Math.abs(scrollY) / height;
                    float alpha = scale * 255;
                    rl_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else if (scrollY > 0 && scrollY < height) {
                    float scale = (float) scrollY / height;
                    float alpha = scale * 255;
                    rl_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

                } else {
                    //过顶部图区域，标题栏定色
                    iv_back.setImageResource(R.mipmap.back_black);
                    if (scrollY == height){
                        tv_title.setVisibility(View.GONE);
                    } else {
                        tv_title.setVisibility(View.VISIBLE);
                    }
                    rl_title.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                }
            }
        });
    }
}
