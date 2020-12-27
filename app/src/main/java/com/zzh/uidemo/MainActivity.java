package com.zzh.uidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    }

}
