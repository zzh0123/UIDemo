package com.zzh.uidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzh.uidemo.channel.ChannelActivity;
import com.zzh.uidemo.choose.ChooseAreaActivity;
import com.zzh.uidemo.dialog.CustomDialogActivity;
import com.zzh.uidemo.javatest.AlgorithmActivity;
import com.zzh.uidemo.lineIndicator.LineIndicatorActivity;
import com.zzh.uidemo.recyclerview.AutoPollRecyclerViewActivity;
import com.zzh.uidemo.recyclerview.NestRecyclerViewActivity;
import com.zzh.uidemo.recyclerview.RecyclerPlusReduceActivity;
import com.zzh.uidemo.recyclerview.RecyclerviewActivity;
import com.zzh.uidemo.recyclerview.TypeRecyclerViewActivity;
import com.zzh.uidemo.scroll.UpStrokeGradientActivity;
import com.zzh.uidemo.viewpager.ViewPagerDemoActivity;
import com.zzh.uidemo.viewpager.ViewPagerShowViewActivity1;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private List<DemoBean> mDemoBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线  https://www.jianshu.com/p/e68a0b5fd383
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new DemoAdapter(mDemoBeanList);
        adapter.setOnItemClickListener(new DemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, mDemoBeanList.get(position).getClazz());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        mDemoBeanList.add(new DemoBean(getString(R.string.view_pager_demo), ViewPagerDemoActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.line_indicator_demo), LineIndicatorActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.up_strokeGradient_demo), UpStrokeGradientActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.comment_dialog_demo), CustomDialogActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.recyclerview_demo), RecyclerviewActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.recycler_plus_reduce_demo), RecyclerPlusReduceActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.choose_area_demo), ChooseAreaActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.channel_demo), ChannelActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.autoPollRecyclerView_demo), AutoPollRecyclerViewActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.nestRecyclerView_demo), NestRecyclerViewActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.TypeRecyclerViewdemo), TypeRecyclerViewActivity.class));
        mDemoBeanList.add(new DemoBean(getString(R.string.AlgorithmDemo), AlgorithmActivity.class));
    }

}
