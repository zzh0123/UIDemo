package com.zzh.uidemo.file1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.hmfl.careasy.baselib.base.LazyBaseFragment;
import com.hmfl.careasy.baselib.constant.CommonStatusType;
import com.hmfl.careasy.baselib.library.asynctask.HttpGetAsyncTask;
import com.hmfl.careasy.baselib.library.cache.StringUtils;
import com.hmfl.careasy.baselib.library.utils.LogUtil;
import com.hmfl.careasy.baselib.library.utils.NetworkDetector;
import com.hmfl.careasy.officialreceptions.R;
import com.hmfl.careasy.officialreceptions.activity.StatementDetailActivity;
import com.hmfl.careasy.officialreceptions.adapter.SettlementAuditAdapter;
import com.hmfl.careasy.officialreceptions.beans.SettlementBean;
import com.hmfl.careasy.officialreceptions.beans.SettlementSearchEvent;
import com.hmfl.careasy.officialreceptions.constant.OfficialReceptionsConstant;
import com.hmfl.careasy.officialreceptions.constant.OfficialReceptionsParamsConstant;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zzh
 * data : 2020/12/14
 * description：结算审核列表界面--待审批
 */
public class SettlementAuditFragment extends LazyBaseFragment {

    private View view;
    /**
     * 页码
     */
    private int pageNo = 1;
    /**
     * 页码大小
     */
    private int pageSize = 10;
    private String planName = "";
    /**
     * 刷新控件
     */
    private SmartRefreshLayout smartRefresh;
    /**
     * 列表控件
     */
    private RecyclerView recyclerView;
    private List<SettlementBean> settlementList = new ArrayList<>();
    private SettlementAuditAdapter adapter;
    /**
     *  空列表布局
     */
    private LinearLayout emptyViewlayout;
    /**
     *  空0待审批 1已审批
     */
    private String state = "";
    /**
     * 搜索关键字
     */
    private String mSearchKey = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.officialreceptions_fragment_settlement_audit, container, false);
        return view;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        state = bundle.getString(OfficialReceptionsParamsConstant.STATE);
        LogUtil.i("zzz1", "state " + state);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smartRefresh = view.findViewById(R.id.smart_refresh_layout);
        smartRefresh.setEnableRefresh(false);
        smartRefresh.setEnableLoadMore(true);
        emptyViewlayout = view.findViewById(R.id.empty_view);
        initEvent();
    }

    /**
     * 初始化adapter
     */
    private void initEvent() {
        // 注册订阅者
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }

        adapter = new SettlementAuditAdapter(getActivity(), settlementList);
        recyclerView.setAdapter(adapter);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smart.refresh.layout.api.RefreshLayout refreshLayout) {

            }

        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(com.scwang.smart.refresh.layout.api.RefreshLayout refreshLayout) {
                refresh();
            }
        });

        adapter.setOnItemClickListener(new SettlementAuditAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                SettlementBean bean = settlementList.get(pos);
                if (bean != null){
                    // 跳转详情
                    StatementDetailActivity.actionStart(getActivity(), bean.getId(), state);
                }
            }
        });

        emptyViewlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settlementList.clear();
                pageNo = 1;
                getAuditList();
            }
        });
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        pageNo++;
        getAuditList();
    }

    @Override
    public void getData() {
        settlementList.clear();
        pageNo = 1;
        getAuditList();
    }

    /**
     * 获取列表数据--待审批
     */
    private void getAuditList() {
        boolean net_flag = NetworkDetector.isNetworkConnected(getActivity());
        if (net_flag) {
            HttpGetAsyncTask commAsyncTask = new HttpGetAsyncTask(getActivity(), null);
            Map<String, String> reqParamMap = new HashMap<>();
            reqParamMap.put("isNeedInterceptUrl", "YES");
            reqParamMap.put("pageNo", pageNo + "");
            reqParamMap.put("pageSize", pageSize + "");
            reqParamMap.put("planName", planName); // 任务名称
            reqParamMap.put("state", state); // 状态 0待审批 1已审批
            reqParamMap.put("visitTimeStart", ""); // 来访时间-开始
            reqParamMap.put("visitTimeEnd", ""); // 来访时间-结束
            commAsyncTask.setShowDialog(CommonStatusType.DIALOG_TYPE_USUAL);
            commAsyncTask.setGetCompleteListener(new HttpGetAsyncTask.ReqGetCompleteListener() {
                @Override
                public void reqGetComplete(Map<String, Object> resultMap) {
                    try {
                        String result = resultMap.get("success").toString();
                        if (result != null && !"true".equals(result)) {
                            showCustomToast(resultMap.get("msg").toString());
                            smartRefresh.finishLoadMore();
                            return;
                        }
                        String dataStr = (String) resultMap.get("data");
                        if (!StringUtils.isStrEmpty(dataStr)) {
                            Map<String, Object> dataMap = StringUtils.transJsonToMap(dataStr);
                            String listStr = (String) dataMap.get("list");
                            if (listStr != null && !listStr.equals("")) {
                                smartRefresh.finishLoadMore();
                                TypeToken<List<SettlementBean>> typeToken = new TypeToken<List<SettlementBean>>() {
                                };
                                List<SettlementBean> modelBeanList = (List<SettlementBean>) StringUtils.convertMapToList(listStr, typeToken);
                                if (modelBeanList != null && modelBeanList.size() > 0) {
                                    if (pageNo == 1) {
                                        settlementList.clear();
                                    }
                                    settlementList.addAll(modelBeanList);
                                } else {
                                    if (pageNo == 1) {
//                                        showCustomToast(getString(R.string.officialreceptions_no_data));
                                        smartRefresh.finishLoadMore();
                                    } else {
                                        smartRefresh.finishLoadMoreWithNoMoreData();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showCustomToast(getString(R.string.system_error));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showCustomToast(getString(R.string.system_error));
                        smartRefresh.finishLoadMore();
                    }
                }
            });
            commAsyncTask.execute(OfficialReceptionsConstant.OFFICIAL_GET_SETTLEMENT_AUDIT_LIST, reqParamMap);
        } else {
            showCustomToast(getString(R.string.net_exception_tip));
        }
    }

    //订阅事件--待审批/已审批
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(SettlementSearchEvent event) {
        planName = event.getSearchKey();
        String eventState = event.getState();
        if (state.equals(eventState)){
            LogUtil.e("zzz1", "eventState  " + eventState);
            settlementList.clear();
            pageNo = 1;
            getAuditList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
