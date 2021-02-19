package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzh.uidemo.R;
import com.zzh.uidemo.dialog.EditCountDialog;
import com.zzh.uidemo.recyclerview.adapter.CheckItemAdapter;
import com.zzh.uidemo.recyclerview.bean.CheckItemBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPlusReduceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckItemAdapter adapter;
    private List<CheckItemBean> checkItemBeanList = new ArrayList<>();
    private boolean isAllCheck = false;
    private ImageView ivAllCheck;
    // 提示弹框
    private EditCountDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_plus_reduce);

        initData();
        initView();
    }

    private void initData(){
//        String imgUrl = "http://www.baidu.com/img/bdlogo.png";
        String imgUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0";
        for (int i = 0; i < 10; i++){
            CheckItemBean testBean = (new CheckItemBean("" + i, 20, i+1, imgUrl));
            checkItemBeanList.add(testBean);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckItemAdapter(this, checkItemBeanList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CheckItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int pos) {
                int id = view.getId();
                final CheckItemBean bean = checkItemBeanList.get(pos);
                if (bean != null) {
                    int count = bean.getEditNum();
                    if (id == R.id.iv_check) { // 选中
                        boolean isChecked = bean.isChecked();
                        if (isChecked) {
                            bean.setChecked(false);
                        } else {
                            bean.setChecked(true);
                        }
//                        adapter.notifyDataSetChanged();
                        adapter.notifyItemChanged(pos);
                        isAllChecked();
                    } else if (id == R.id.ll_see_detail) { // 查看详情

                    } else if (id == R.id.tv_reduce) { // -
                        if (count > 1) {
                            count--;
                            bean.setEditNum(count);
                            adapter.notifyDataSetChanged();
//                            adapter.notifyItemChanged(pos);
                        } else {
                            showCustomToast("不能再少了！");
                        }
                    } else if (id == R.id.tv_plus) { // +
                        if (count < bean.getNum()){
                            count++;
                            bean.setEditNum(count);
//                            adapter.notifyDataSetChanged();
                            adapter.notifyItemChanged(pos);
                        } else {
                            showCustomToast("不能大于总数量！");
                        }

                    } else if (id == R.id.tv_count) { // 数量
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            dialog = null;
                        } else {
                            dialog = new EditCountDialog(RecyclerPlusReduceActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.setContent("" + count);
                            dialog.setSureOnclickListener(new EditCountDialog.onSureOnclickListener() {
                                @Override
                                public void onSureClick() {
                                    String content = dialog.getContent();
                                    if (!TextUtils.isEmpty(content)) {
                                        int count = Integer.parseInt(content);
                                        if (count > 0 && count <= bean.getNum()) {
                                            bean.setEditNum(count);
//                                            adapter.notifyDataSetChanged();
                                            adapter.notifyItemChanged(pos);
                                        } else {
                                            showCustomToast("不能大于总数量！");
                                        }
                                    }
                                    if (dialog != null) {
                                        dialog.dismiss();
                                        dialog = null;
                                    }
                                }

                                @Override
                                public void onCancelDialog() {
                                    if (dialog != null) {
                                        dialog.dismiss();
                                        dialog = null;
                                    }
                                }
                            });
                            dialog.show();
                        }
                    }
                }
            }
        });

        // 全选
        ivAllCheck = findViewById(R.id.iv_all_check);
        ivAllCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllCheck) {
                    isAllCheck = false;
                    ivAllCheck.setImageResource(R.mipmap.unchecked);
                    setAllDataChecked(isAllCheck);
                } else {
                    isAllCheck = true;
                    setAllDataChecked(isAllCheck);
                    ivAllCheck.setImageResource(R.mipmap.checked);
                }
            }
        });
    }

    private void setAllDataChecked(boolean isAllCheck) {
        for (int i = 0; i < checkItemBeanList.size(); i++) {
            checkItemBeanList.get(i).setChecked(isAllCheck);
        }
        adapter.notifyDataSetChanged();
    }

    // 是否所有item被选中，全选按钮是否选择
    private void isAllChecked() {
        for (int i = 0; i < checkItemBeanList.size(); i++) {
            if (checkItemBeanList.get(i).isChecked()) {
                isAllCheck = true;
            } else {
                isAllCheck = false;
                break;
            }
        }

        if (isAllCheck) {
            ivAllCheck.setImageResource(R.mipmap.checked);
        } else {
            ivAllCheck.setImageResource(R.mipmap.unchecked);
        }
    }

    public void showCustomToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
