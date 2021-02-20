package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.zzh.uidemo.R;
import com.zzh.uidemo.bean.TestBean;
import com.zzh.uidemo.recyclerview.adapter.RecyclerDemoAdapter;
import com.zzh.uidemo.recyclerview.view.AutoPollRecyclerView;
import com.zzh.uidemo.recyclerview.view.AutoScrollLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class AutoPollRecyclerViewActivity extends AppCompatActivity {

    private AutoPollRecyclerView recyclerView;
    private RecyclerDemoAdapter adapter;
    private List<TestBean> mTestBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_poll_recycler_view);

        initData();
        initView();

    }

    private void initData(){
        String imgUrl = "http://www.baidu.com/img/bdlogo.png";
        for (int i = 0; i < 10; i++){
            TestBean testBean = (new TestBean("" + i, imgUrl));
            mTestBeanList.add(testBean);
        }
    }

    private void initView(){
        // androidx 有问题
        AutoScrollLayoutManager autoScrollLayoutManager = new AutoScrollLayoutManager(this);
        recyclerView = (AutoPollRecyclerView) findViewById(R.id.autoPollRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(autoScrollLayoutManager);
        adapter = new RecyclerDemoAdapter(this, mTestBeanList);
        recyclerView.setAdapter(adapter);

        if (mTestBeanList.size() > 5) {
            recyclerView.start();
        } else {
            recyclerView.stop();
        }
    }
}
