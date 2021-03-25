package com.zzh.uidemo.file1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.aroutermodule.OfficialReceptionsRouterConstant;
import com.hmfl.careasy.baselib.base.BaseActivity;
import com.hmfl.careasy.baselib.base.baseadapter.adapter.MyFragmentAdapter;
import com.hmfl.careasy.baselib.library.utils.LogUtil;
import com.hmfl.careasy.baselib.library.utils.SoftKeyBoardListener;
import com.hmfl.careasy.officialreceptions.R;
import com.hmfl.careasy.officialreceptions.beans.SettlementSearchEvent;
import com.hmfl.careasy.officialreceptions.constant.OfficialReceptionsParamsConstant;
import com.hmfl.careasy.officialreceptions.fragment.SettlementAuditFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zzh
 * data : 2020/12/14
 * description：结算审核列表界面
 */
@Route(path = OfficialReceptionsRouterConstant.PATH_SETTLEMENT_AUDIT_ACTIVITY)
public class SettlementAuditActivity extends BaseActivity implements View.OnClickListener {

    private int mFrom = -1;//确认界面和使用界面公用复用一个table，改字段加以区分
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private MyFragmentAdapter adapter;
    private final int[] array = new int[]{R.id.tv_pending_approval, R.id.tv_approved};
    ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        //不管是Viewpager滑动,还是RadioGroup点击  都会调用这个方法
        @Override
        public void onPageSelected(int position) {
            change(array[position]);
            sendSearchEvent();
        }
    };
    private Button btBack;
    /**
     * 待审批
     */
    private TextView tvPendingApproval;
    /**
     * 已审批
     */
    private TextView tvApproved;
    /**
     * 搜索框
     */
    private AutoCompleteTextView mQueryView;
    /**
     * 搜索框清除按钮
     */
    private ImageButton mSearchClearBtn;
    /**
     * 搜索关键字
     */
    private String mSearchKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officialreceptions_activity_settlement_audit);
        initView();
        initFragment();
        initEvent();
    }

    private void initView() {
        btBack = findViewById(R.id.bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager = findViewById(R.id.viewpager_home);
        tvPendingApproval = findViewById(R.id.tv_pending_approval);
        tvApproved = findViewById(R.id.tv_approved);
        tvPendingApproval.setOnClickListener(this);
        tvApproved.setOnClickListener(this);

        // 搜索框
        mQueryView = (AutoCompleteTextView) findViewById(R.id.query);
        mQueryView.setHint(R.string.officialreceptions_search_hint1);
        mSearchClearBtn = (ImageButton) findViewById(R.id.search_clear);
        Button searchBtn = (Button) findViewById(R.id.search);
        mSearchClearBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        searchBtn.setBackgroundResource(R.drawable.car_easy_warning_startnow);
        searchBtn.setVisibility(View.GONE);
    }

    private void initEvent(){

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LogUtil.iJsonFormat("mzkml", "键盘显示 高度" + height);
            }

            @Override
            public void keyBoardHide(int height) {
                LogUtil.iJsonFormat("mzkml", "键盘隐藏 高度" + height);
                mQueryView.clearFocus();
            }
        });
        // 搜索框
        mQueryView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mSearchKey = "";
                    sendSearchEvent();
                }
            }
        });

        mQueryView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    sendSearchEvent();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 发送搜索事件 currentPage 0 待审批界面 1 已审批界面
     * 0未提交;1草稿;2审批中;3通过;4退回
     */
    private void sendSearchEvent(){
        mSearchKey = mQueryView.getText().toString().trim();//请输入任务名称
        int currentPage = viewPager.getCurrentItem();
        SettlementSearchEvent settlementSearchEvent = new SettlementSearchEvent();
        if (currentPage == 0) {
            settlementSearchEvent.setSearchKey(mSearchKey);
            settlementSearchEvent.setState("0");
            EventBus.getDefault().post(settlementSearchEvent);
        } else if (currentPage == 1) {
            settlementSearchEvent.setSearchKey(mSearchKey);
            settlementSearchEvent.setState("1");
            EventBus.getDefault().post(settlementSearchEvent);
        }
    }

    // 状态 0待审批 1已审批
    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(OfficialReceptionsParamsConstant.STATE, "0");
        SettlementAuditFragment pendingApprovalFragment = new SettlementAuditFragment();
        pendingApprovalFragment.setArguments(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString(OfficialReceptionsParamsConstant.STATE, "1");
        SettlementAuditFragment approvedFragment = new SettlementAuditFragment();
        approvedFragment.setArguments(bundle1);
        list.add(pendingApprovalFragment);
        list.add(approvedFragment);

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    /**
     * 改变背景颜色,背景图片
     *
     * @param checkedId 选中的tab项的ID
     */
    private void change(int checkedId) {
        // 改变字体颜色
        if (checkedId == R.id.tv_pending_approval) {
            tvPendingApproval.setBackground(getResources().getDrawable(R.drawable.officialreceptions_switch_white_bg));
            tvApproved.setBackground(getResources().getDrawable(R.drawable.officialreceptions_switch_grey_bg));
            tvPendingApproval.setTextColor(getResources().getColor(R.color.officialreceptions_color_333333));
            tvApproved.setTextColor(getResources().getColor(R.color.officialreceptions_color_95989C));
        } else if (checkedId == R.id.tv_approved) {
            tvApproved.setBackground(getResources().getDrawable(R.drawable.officialreceptions_switch_white_bg));
            tvPendingApproval.setBackground(getResources().getDrawable(R.drawable.officialreceptions_switch_grey_bg));
            tvApproved.setTextColor(getResources().getColor(R.color.officialreceptions_color_333333));
            tvPendingApproval.setTextColor(getResources().getColor(R.color.officialreceptions_color_95989C));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.tv_pending_approval == id) {
            viewPager.setCurrentItem(0);
        } else if (R.id.tv_approved == id) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
