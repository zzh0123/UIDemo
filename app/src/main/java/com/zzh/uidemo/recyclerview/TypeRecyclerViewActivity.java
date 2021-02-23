package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.adapter.DiscussItemAdapter;
import com.zzh.uidemo.recyclerview.bean.DiscussItemBean;

import java.util.ArrayList;
import java.util.List;

public class TypeRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DiscussItemBean> discussList = new ArrayList<>();
    private DiscussItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new DiscussItemAdapter(this, userId, discussList);
//        adapter.setOnItemMyClickListener(new DiscussItemAdapter.OnItemMyClickListener() {
//            @Override
//            public void onItemMyClick(View view, String id, boolean currentLikeFlag, int pos) {
//                if (!currentLikeFlag) {
//                    likeComment(id, currentLikeFlag, pos);
//                } else {
//                    unLikeComment(id, currentLikeFlag, pos);
//                }
//            }
//        });
//
//        adapter.setOnItemOtherClickListener(new DiscussItemAdapter.OnItemOtherClickListener() {
//            @Override
//            public void onItemOtherClick(View view, String id, boolean currentLikeFlag, int pos) {
//                if (!currentLikeFlag) {
//                    likeComment(id, currentLikeFlag, pos);
//                } else {
//                    unLikeComment(id, currentLikeFlag, pos);
//                }
//            }
//        });
//        recyclerView.setAdapter(adapter);
    }
}
