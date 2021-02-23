package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.adapter.EquipItemInnerAdapter;
import com.zzh.uidemo.recyclerview.adapter.EquipItemLeftAdapter;
import com.zzh.uidemo.recyclerview.adapter.EquipItemRightAdapter;
import com.zzh.uidemo.recyclerview.bean.InnerBean;
import com.zzh.uidemo.recyclerview.bean.LeftBean;
import com.zzh.uidemo.recyclerview.bean.RightBean;
import com.zzh.uidemo.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView嵌套RecyclerView
 */
public class NestRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener,
        EquipItemInnerAdapter.OnInnerItemClickListener {

    public static final int CHOOSE_EQUIP = 11;

    /**
     * 装备类型列表跳转
     *
     * @param activity
     * @param isMulti  是否多选， true 多选， false 单选
     */
    public static void actionStart(Activity activity, boolean isMulti) {
        Intent intent = new Intent(activity, NestRecyclerViewActivity.class);
//        intent.putExtra(AssetsParamsConstant.IS_MULTI, isMulti);
//        intent.putExtra(AssetsParamsConstant.PUSH_CODE, pushCode);
        activity.startActivityForResult(intent, CHOOSE_EQUIP);
    }

    /**
     * 标题
     */
    private TextView tvBack, tvTitle;

    /**
     * 点验申请列表
     */
    private RecyclerView recyclerViewLeft;
    private EquipItemLeftAdapter adapterLeft;
    private RecyclerView recyclerViewRight;
    private EquipItemRightAdapter adapterRight;
    private List<LeftBean> checkApplyBeanListLeft = new ArrayList<>();
    private List<RightBean> checkApplyBeanListRight = new ArrayList<>();
    private List<InnerBean> innerBeaneanList = new ArrayList<>();
    /**
     * 全选,提交
     */
    private ImageView ivAllCheck;
    private TextView tvSubmit, tvAllCheck;
    private RelativeLayout rlAllCheck;
    private boolean isAllCheck = false;
    private boolean isMulti = true; // 默认多选
    // 被选中的节点集合
    private List<String> selectedIdList = new ArrayList<>();
    private String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_recycler_view);

        initTitle();
        initView();
        initEvent();
        getEquipTypeTree();
    }

    /**
     * 标题栏
     */
    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvBack = findViewById(R.id.tv_back);
        Intent intent = getIntent();
        if (intent != null) {
//            isMulti = intent.getBooleanExtra(AssetsParamsConstant.IS_MULTI, true);
        }
    }

    private void initView() {
        // 左侧
        recyclerViewLeft = (RecyclerView) findViewById(R.id.recyclerView_left);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        adapterLeft = new EquipItemLeftAdapter(this, checkApplyBeanListLeft);
        recyclerViewLeft.setAdapter(adapterLeft);
        // 右侧
        recyclerViewRight = (RecyclerView) findViewById(R.id.recyclerView_right);
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
        adapterRight = new EquipItemRightAdapter(this, checkApplyBeanListRight, isMulti);
        adapterRight.setInnerListener(this);
        recyclerViewRight.setAdapter(adapterRight);

        ivAllCheck = findViewById(R.id.iv_all_check);
        tvAllCheck = findViewById(R.id.tv_all_check);
        rlAllCheck = findViewById(R.id.rl_all_check);
        tvSubmit = findViewById(R.id.tv_submit);
        // 单选隐藏全选
        if (!isMulti) {
            ivAllCheck.setVisibility(View.GONE);
            tvAllCheck.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        tvBack.setOnClickListener(this);
        ivAllCheck.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        // 左侧
        adapterLeft.setOnItemClickListener(new EquipItemLeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                LeftBean bean = checkApplyBeanListLeft.get(pos);
                if (bean != null) {
                    setLeftListChecked(pos);
                }
            }
        });
        // 右侧
        adapterRight.setOnItemClickListener(new EquipItemRightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                LogUtil.i("zzz1", "right position " + position);
                RightBean rightBean = checkApplyBeanListRight.get(position);
                if (rightBean != null) {
                    boolean isChecked = rightBean.isChecked();
                    if (isChecked) {
                        rightBean.setChecked(false);
                        setInnerListChecked(position, false);
                    } else {
                        rightBean.setChecked(true);
                        setInnerListChecked(position, true);
                    }
                    adapterRight.notifyDataSetChanged();

                    isAllChecked();
                }

            }
        });

    }

    /**
     * 设置右侧内部列表数据
     *
     * @param pos
     */
    private void setInnerListChecked(int pos, boolean isAllChecked) {
        innerBeaneanList = checkApplyBeanListRight.get(pos).getChildList();
        for (int i = 0; i < innerBeaneanList.size(); i++) {
            innerBeaneanList.get(i).setChecked(isAllChecked);
        }
    }

    /**
     * 设置左侧列表数据
     *
     * @param pos
     */
    private void setLeftListChecked(int pos) {
        for (int i = 0; i < checkApplyBeanListLeft.size(); i++) {
            if (i == pos) {
                checkApplyBeanListLeft.get(pos).setChecked(true);
            } else {
                checkApplyBeanListLeft.get(i).setChecked(false);
            }
        }
        adapterLeft.notifyDataSetChanged();

        checkApplyBeanListRight.clear();
        List<RightBean> rightBeanList = checkApplyBeanListLeft.get(pos).getChildList();
        if (rightBeanList != null && rightBeanList.size() > 0) {
//            LogUtil.i("zzz1", "rightBeanList " + rightBeanList.size());
            checkApplyBeanListRight.addAll(rightBeanList);
        }
        adapterRight.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_all_check) { // 全选
            setAllCheckedButton();
        } else if (id == R.id.tv_submit) { // 提交
            submitSelect();
        } else if (id == R.id.tv_back) {
            finish();
        }
    }

    private void submitSelect() {
        selectedIdList.clear();
        for (int i = 0; i < checkApplyBeanListLeft.size(); i++) {
            List<RightBean> checkApplyBeanListRight = checkApplyBeanListLeft.get(i).getChildList();
            for (int j = 0; j < checkApplyBeanListRight.size(); j++) {
                List<InnerBean> innerBeaneanList = checkApplyBeanListRight.get(j).getChildList();
                if (innerBeaneanList != null && innerBeaneanList.size() > 0) {
                    for (int k = 0; k < innerBeaneanList.size(); k++) {
                        if (innerBeaneanList.get(k).isChecked()) {
                            selectedIdList.add(innerBeaneanList.get(k).getId());
                            selectedName = innerBeaneanList.get(k).getName();
                        }
                    }
                }
            }
        }
        if (selectedIdList == null || selectedIdList.size() == 0) {
//            showCustomToast(getResources().getString(R.string.assets_equip_type_tip));
            return;
        }

//        String[] idArray = new String[selectedIdList.size()];
//        String selectedIdStr = Arrays.toString(selectedIdList.toArray(idArray));
        Intent intent = new Intent();
        intent.putExtra("selectedIdList", (Serializable) selectedIdList);
        intent.putExtra("selectedName", selectedName);
//        intent.putExtra("selectedIdStr", selectedIdStr);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * @param position 为第一层recycleview位置
     * @param tag      为第二层recycleview位置
     */
    @Override
    public void onInnerItemClick(int position, int tag) {
//        LogUtil.i("zzz1", "inner pos " + position + " " + tag);
        innerBeaneanList = checkApplyBeanListRight.get(position).getChildList();
        InnerBean innerBean = innerBeaneanList.get(tag);
        boolean isChecked = innerBean.isChecked();
        if (isChecked) {
            // 单选
            if (!isMulti) {
                clearChecked();
            }
            innerBean.setChecked(false);
        } else {
            // 单选
            if (!isMulti) {
                clearChecked();
            }
            innerBean.setChecked(true);
        }
        if (isMulti) {
            setRightItemChecked(position);
        }
        adapterRight.notifyDataSetChanged();


    }

    // 单选-遍历所有
    private void clearChecked() {
        for (int i = 0; i < checkApplyBeanListLeft.size(); i++) {
            List<RightBean> checkApplyBeanListRight = checkApplyBeanListLeft.get(i).getChildList();
            for (int j = 0; j < checkApplyBeanListRight.size(); j++) {
                List<InnerBean> innerBeaneanList = checkApplyBeanListRight.get(j).getChildList();
                for (int k = 0; k < innerBeaneanList.size(); k++) {
                    innerBeaneanList.get(k).setChecked(false);
                }
            }
        }
    }

    // 点击内部item-设置右侧反选
    private void setRightItemChecked(int pos) {
        boolean allChecked = false;
        for (int i = 0; i < innerBeaneanList.size(); i++) {
            if (innerBeaneanList.get(i).isChecked()) {
                allChecked = true;
            } else {
                allChecked = false;
                break;
            }
        }
        checkApplyBeanListRight.get(pos).setChecked(allChecked);

        isAllChecked();
    }

    // 设置反选-全选按钮是否选择
    private void isAllChecked() {
        for1:
        for (int i = 0; i < checkApplyBeanListLeft.size(); i++) {
            List<RightBean> checkApplyBeanListRight = checkApplyBeanListLeft.get(i).getChildList();
            for2:
            for (int j = 0; j < checkApplyBeanListRight.size(); j++) {
                if (checkApplyBeanListRight.get(j).isChecked()) {
                    isAllCheck = true;
                } else {
                    isAllCheck = false;
                    break for1;
                }
            }
        }

        if (isAllCheck) {
            ivAllCheck.setImageResource(R.mipmap.checked);
        } else {
            ivAllCheck.setImageResource(R.mipmap.unchecked);
        }
    }

    // 全选-遍历所有
    private void setAllChecked(boolean isAllCheck) {
        for (int i = 0; i < checkApplyBeanListLeft.size(); i++) {
            List<RightBean> checkApplyBeanListRight = checkApplyBeanListLeft.get(i).getChildList();
            for (int j = 0; j < checkApplyBeanListRight.size(); j++) {
                checkApplyBeanListRight.get(j).setChecked(isAllCheck);
                List<InnerBean> innerBeaneanList = checkApplyBeanListRight.get(j).getChildList();
                for (int k = 0; k < innerBeaneanList.size(); k++) {
                    innerBeaneanList.get(k).setChecked(isAllCheck);
                }
            }
        }
        adapterRight.notifyDataSetChanged();
    }

    // 全选按钮状态
    private void setAllCheckedButton() {
        if (isAllCheck) {
            isAllCheck = false;
            ivAllCheck.setImageResource(R.mipmap.unchecked);
            setAllChecked(isAllCheck);
        } else {
            isAllCheck = true;
            setAllChecked(isAllCheck);
            ivAllCheck.setImageResource(R.mipmap.checked);
        }
    }

    /**
     * 获取装备类型
     */
    private void getEquipTypeTree() {

        String resultStr = Util.readAssert(this, "equipType.txt");
        Map<String, Object> resultMap = Util.transJsonToMap(resultStr);


        String result = resultMap.get("success").toString();
        if (result != null && !"true".equals(result)) {
//            showCustomToast(resultMap.get("msg").toString());
            return;
        } else {
            String dataStr = (String) resultMap.get("data");
            if (dataStr != null && !dataStr.equals("")) {
                TypeToken<List<LeftBean>> typeToken = new TypeToken<List<LeftBean>>() {
                };
                List<LeftBean> modelBeanList = (List<LeftBean>) Util.convertMapToList(dataStr, typeToken);
                if (modelBeanList != null && modelBeanList.size() > 0) {
                    checkApplyBeanListLeft.addAll(modelBeanList);
                    setLeftListChecked(0);
                    adapterLeft.notifyDataSetChanged();
                }
            }
        }
    }

}
