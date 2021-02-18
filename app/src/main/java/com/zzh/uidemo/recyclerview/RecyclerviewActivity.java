package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zzh.uidemo.R;
import com.zzh.uidemo.adapter.RecyclerDemoAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.TestBean;

public class RecyclerviewActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerDemoAdapter adapter;
    private List<TestBean> mTestBeanList = new ArrayList<>();

    private Button bt_notifyDataSetChanged, bt_notifyDataSetChanged_pos,
            bt_notifyItemRangeChanged, bt_notifyItemRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initData();
        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerDemoAdapter(this, mTestBeanList);
        adapter.setOnItemClickListener(new RecyclerDemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        // 全部刷新
        bt_notifyDataSetChanged = findViewById(R.id.bt_notifyDataSetChanged);
        bt_notifyDataSetChanged.setOnClickListener(this);

        // 定点刷新
        bt_notifyDataSetChanged_pos = findViewById(R.id.bt_notifyDataSetChanged_pos);
        bt_notifyDataSetChanged_pos.setOnClickListener(this);

        // 批量刷新
        bt_notifyItemRangeChanged = findViewById(R.id.bt_notifyItemRangeChanged);
        bt_notifyItemRangeChanged.setOnClickListener(this);

        // 定点删除
        bt_notifyItemRemoved = findViewById(R.id.bt_notifyItemRemoved);
        bt_notifyItemRemoved.setOnClickListener(this);

    }

    private void initData(){
        String imgUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0";
        for (int i = 0; i < 10; i++){
            TestBean testBean = (new TestBean("" + i, imgUrl));
            mTestBeanList.add(testBean);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_notifyDataSetChanged){
            adapter.notifyDataSetChanged();
        } else  if (id == R.id.bt_notifyDataSetChanged_pos){
            mTestBeanList.set(1, new TestBean("哈哈！"));
            mTestBeanList.set(2, new TestBean("哈哈哈哈哈！"));
            adapter.notifyItemChanged(2);
        } else if (id == R.id.bt_notifyItemRangeChanged){
//            mTestBeanList.set(3, new TestBean("3333333！"));
            mTestBeanList.set(4, new TestBean("44444！"));
            mTestBeanList.set(6, new TestBean("666666！"));
            mTestBeanList.set(8, new TestBean("888888！"));
            adapter.notifyItemRangeChanged(4, 5); // 算上第4个，往后数3
        }else  if (id == R.id.bt_notifyItemRemoved){
            mTestBeanList.remove(2);
            adapter.notifyItemRemoved(2); // 不操作原来集合
            Log.i("zzz1", "mTestBeanList.size " + mTestBeanList.size());
        }
    }
}
