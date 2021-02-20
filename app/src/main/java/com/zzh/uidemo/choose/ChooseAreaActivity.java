package com.zzh.uidemo.choose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.zzh.uidemo.R;
import com.zzh.uidemo.choose.adapter.AreaAdapter;
import com.zzh.uidemo.choose.adapter.CityAdapter;
import com.zzh.uidemo.choose.adapter.ProvinceAdapter;
import com.zzh.uidemo.choose.bean.Area;
import com.zzh.uidemo.choose.bean.City;
import com.zzh.uidemo.choose.bean.Province;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zzh.uidemo.utils.Util;

/**
 * @author: zzh
 * data : 2020-06-12
 * description：选择省市区
 */
public class ChooseAreaActivity extends Activity {
    /**
     * 选择地址回调值
     */
    private static final int CHOOSE_AREA = 11;
    private TextView mActionbarTitle;
    private Context mContext;
    /**
     * 省市区列表
     */
    private RecyclerView mRvProvinceCityArea;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private AreaAdapter mAreaItemAdapter;
    private TextView mTvAllArea;
    /**
     * 省市区列表数据
     */
    private ArrayList<Province> mProvinceList = null;
    private ArrayList<City> mCityList = null;
    private ArrayList<Area> mAreaList = null;
    private String mResult;
    /**
     * 途径地列表次序
     */
    private int stopoverSequence;
    /**
     * 搜索
     */
    private LinearLayout mllSearch;
    private EditText mEtSearch;
    private ImageButton mImbSearchClear;
    /**
     * 当前城市(百度一次定位获取当前定位信息)，历史选择
     */
    private LinearLayout mllCurrentCity;
    private TextView mTvChooseHistory;

    private static final int MAX_HISTORY_COUNT = 9;
    private TextView mTvCurrentCity;
    private RecyclerView mRvHistory;
    private SharedPreferences mSpUserInfo;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    private final String USER_HISTORY_CITY = "user_history_city";
    private final String USER_INFO = "user_info";
    private String mPhone;

    /**
     * 百度一次定位获取当前定位信息
     */
//    private BDLoacationsingle bdLoacationsingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_choose_area);
        initTitle();
        initView();
        initHistoryCity();
        initData();
        initSearchViewAndLocation();
    }

    /**
     * 标题栏
     */
    private void initTitle() {
//        TitleBarUtils titleBarUtils = new TitleBarUtils();
//        titleBarUtils.initTitle(this,getString(R.string.choose_province_city_area));
//        mActionbarTitle = titleBarUtils.getActionBarTitleView();
    }

    private void initView() {
        // 获取用户手机号码
        mSpUserInfo = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        mPhone = mSpUserInfo.getString("phone", "");
//        mSp = ActivityUtils.selSharedPreferencesData(this, USER_HISTORY_CITY);
        mSp = getSharedPreferences(USER_HISTORY_CITY, Context.MODE_PRIVATE);
        mEditor = mSp.edit();
        // 搜索，当前城市，历史选择
        mllSearch = (LinearLayout) findViewById(R.id.ll_search);
        mllCurrentCity = (LinearLayout) findViewById(R.id.ll_current_city);
        mTvChooseHistory = (TextView) findViewById(R.id.tv_choose_history);
        mRvHistory = (RecyclerView) findViewById(R.id.rv_history);
        mTvAllArea = (TextView) findViewById(R.id.tv_all_area);

        mRvProvinceCityArea = findViewById(R.id.rv_province_city_area);
        mRvProvinceCityArea.setHasFixedSize(true);
        mRvProvinceCityArea.setLayoutManager(new LinearLayoutManager(mContext));
        // 省列表适配器
        mProvinceAdapter = new ProvinceAdapter(R.layout.item_choose_area);
        mProvinceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mCityList = (ArrayList) mProvinceList.get(position).getCityList();
                if (mCityList != null && mCityList.size() > 0) { // 有城市列表进入下一级选择
                    Province provinceEntity = mProvinceList.get(position);
                    Intent intent = new Intent(ChooseAreaActivity.this, ChooseAreaActivity.class);
                    intent.putExtra("cityList", mCityList); // 城市列表数据
                    intent.putExtra("province", provinceEntity.getName()); // 选择的省
                    startActivityForResult(intent, CHOOSE_AREA);
                } else { // 当没有城市列表时直接返回结果
                    String province = mProvinceList.get(position).getName();
                    mResult = province;
                    Intent intentResultArea = new Intent();
                    intentResultArea.putExtra("result", mResult);
                    intentResultArea.putExtra("stopoverSequence", stopoverSequence);
                    // 更新历史选择
                    updateHistoryChoose(mResult);
                    setResult(RESULT_OK, intentResultArea);
                    finish();
                }
            }
        });

        // 城市列表适配器
        mCityAdapter = new CityAdapter(R.layout.item_choose_area);
        mCityAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mAreaList = (ArrayList) mCityList.get(position).getAreaList();
                Intent getIntent = getIntent();
                String province = getIntent.getStringExtra("province");
                if ("请选择".equals(mCityList.get(position).getName())) { // 直接返回上一级结果
                    mResult = province;
                    Intent intentResult = new Intent();
                    intentResult.putExtra("result", mResult);
                    setResult(RESULT_OK, intentResult);
                    finish();
                } else {
                    if (mAreaList != null && mAreaList.size() > 0) { // 有地区列表进入下一级选择
                        City cityEntity = mCityList.get(position);
                        Intent intent = new Intent(ChooseAreaActivity.this, ChooseAreaActivity.class);
                        intent.putExtra("areaList", mAreaList); // 地区列表数据
                        intent.putExtra("province", province); // 选择的省
                        intent.putExtra("city", cityEntity.getName()); // 选择的城市
                        startActivityForResult(intent, CHOOSE_AREA);
                    } else { // 当没有地区列表时直接返回结果
                        String city = mCityList.get(position).getName();
                        mResult = province + "/" + city;
                        Intent intentResultArea = new Intent();
                        intentResultArea.putExtra("result", mResult);
                        setResult(RESULT_OK, intentResultArea);
                        finish();
                    }
                }
            }
        });

        // 地区列表适配器
        mAreaItemAdapter = new AreaAdapter(R.layout.item_choose_area);
        mAreaItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = getIntent();
                String province = intent.getStringExtra("province");
                String city = intent.getStringExtra("city");
                if ("请选择".equals(mAreaList.get(position).getName())) { // 直接返回上一级结果
                    mResult = province + "/" + city;
                    Intent intentResult = new Intent();
                    intentResult.putExtra("result", mResult);
                    setResult(RESULT_OK, intentResult);
                    finish();
                } else { // 返回选择的结果
                    Area areaEntity = mAreaList.get(position);
                    mResult = province + "/" + city + "/" + areaEntity.getName();
                    Intent intentResultArea = new Intent();
                    intentResultArea.putExtra("result", mResult);
                    setResult(RESULT_OK, intentResultArea);
                    finish();
                }
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        String province = intent.getStringExtra("province");
        String city = intent.getStringExtra("city");
        if (province == null && city == null) { // 当为市区，县区界面时，不再加载资源文件
            //初始化地区的数据
            String address = Util.readAssert(this, "reimbursementAddress.json");
            if (!TextUtils.isEmpty(address)) {
                TypeToken<List<Province>> typeToken = new TypeToken<List<Province>>() {
                };
                mProvinceList = (ArrayList<Province>) Util.convertMapToList(address, typeToken);
                if (mProvinceList != null && mProvinceList.size() > 0) {
                    // 默认地区
                    mRvProvinceCityArea.setAdapter(mProvinceAdapter);
                    mProvinceAdapter.setNewData(mProvinceList);
                }
            }
        } else {
            // 搜索，当前城市，历史选择
            mllSearch.setVisibility(View.GONE);
            mllCurrentCity.setVisibility(View.GONE);
            mTvChooseHistory.setVisibility(View.GONE);
            mRvHistory.setVisibility(View.GONE);
            mTvAllArea.setVisibility(View.GONE);
        }

        stopoverSequence = intent.getIntExtra("stopoverSequence", -1);
        mCityList = (ArrayList<City>) intent.getSerializableExtra("cityList"); // 城市列表

        // 城市列表不为空加载城市列表数据
        if (mCityList != null && mCityList.size() > 0) {
//            mActionbarTitle.setText(mContext.getString(R.string.choose_city));
            City cityChoose = new City();
            cityChoose.setName("请选择");
            mCityList.add(0, cityChoose);
            mRvProvinceCityArea.setAdapter(mCityAdapter);
            mCityAdapter.setNewData(mCityList);
        }

        mAreaList = (ArrayList<Area>) intent.getSerializableExtra("areaList"); // 地区列表

        // 地区列表不为空加载地区列表数据
        if (mAreaList != null && mAreaList.size() > 0) {
//            mActionbarTitle.setText(mContext.getString(R.string.choose_area));
            Area areaChoose = new Area();
            areaChoose.setName("请选择");
            mAreaList.add(0, areaChoose);
            mRvProvinceCityArea.setAdapter(mAreaItemAdapter);
            mAreaItemAdapter.setNewData(mAreaList);
        }
    }

    // 定位当前城市
    private void initSearchViewAndLocation() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mImbSearchClear = (ImageButton) findViewById(R.id.imb_search_clear);
        mImbSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.getText().clear();
            }
        });
        // 当前城市
        mTvCurrentCity = (TextView) findViewById(R.id.tv_current_city);
        mTvCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mTvCurrentCity.getText().toString().trim();
                if (city.equals("未定位到当前位置")){
//                    showCustomToast(mContext.getString(R.string.choose_not_located));
                    return;
                } else {
                   if (mResult != null){
                       // 更新历史选择
                       Log.i("--zzz1--", "--1111-");
                       updateHistoryChoose(mResult);
                       Intent intentResultArea = new Intent();
                       intentResultArea.putExtra("result", mResult);
//                       intentResultArea.putExtra("stopoverSequence", stopoverSequence);
                       setResult(RESULT_OK, intentResultArea);
                       finish();
                   }
                }

            }
        });
        String currentProvince = mSpUserInfo.getString("current_province", "");
        String currentCity = mSpUserInfo.getString("city", "");
        if (!TextUtils.isEmpty(currentProvince) && !TextUtils.isEmpty(currentCity)) {
            mResult = currentProvince + "/" + currentCity;
            mTvCurrentCity.setText(currentCity);
        } else {
            // 百度地图定位当前城市
//            initBaiduMap();
        }
        //搜索
        initSearch();
    }

    // 获取当前位置信息
//    private void initBaiduMap() {
//        bdLoacationsingle = new BDLoacationsingle(this);
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String... params) {
//
//                String result = null;
//                String currentProvince = null;
//                while (currentProvince == null) {
//                    currentProvince = bdLoacationsingle.getCurrentProvince();
//                }
//                String cityName = bdLoacationsingle.getCityName();
//                // currentProvince定位到cityName也会有定位，BDLoacationsingle中已经处理
//                if (!StringUtils.isStrEmpty(currentProvince)){
//                    if (currentProvince.equals("error")){
//                        result = "error";
//                    } else {
//                        result = currentProvince + "/" + cityName;
//                    }
//                } else {
//                    result = "error";
//                }
//                return result;
//            }
//
//            @Override
//            protected void onPreExecute() {
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                if (s.equals("error")) {
//                    mTvCurrentCity.setText(mContext.getString(R.string.choose_not_located));
//                } else {
//                    mResult = s;
//                    String[] result = s.split("/"); // 斜杠截取 保存在数组中
//                    mTvCurrentCity.setText(result[1]);
//                }
//            }
//        }.execute();
//    }

    // 搜索
    private void initSearch() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (mProvinceAdapter != null && mProvinceAdapter.getFilter() != null) {
                    mProvinceAdapter.getFilter().filter(s.toString());
                    if (s.toString().length() > 0) {
                        mImbSearchClear.setVisibility(View.VISIBLE);
                        // 隐藏当前城市，历史选择布局
                        mllCurrentCity.setVisibility(View.GONE);
                        mTvChooseHistory.setVisibility(View.GONE);
                        mRvHistory.setVisibility(View.GONE);
                        mTvAllArea.setVisibility(View.GONE);
                    } else {
                        mImbSearchClear.setVisibility(View.INVISIBLE);
                        // 显示当前城市，历史选择布局
                        mllCurrentCity.setVisibility(View.VISIBLE);
                        mTvChooseHistory.setVisibility(View.VISIBLE);
                        mRvHistory.setVisibility(View.VISIBLE);
                        mTvAllArea.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    // 历史选择初始化
    private void initHistoryCity() {
        String longHistory = mSp.getString(mPhone, "");  // 获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(","); // 逗号截取 保存在数组中
        if (tmpHistory != null && tmpHistory.length > 0 && !TextUtils.isEmpty(tmpHistory[0])) {
            final List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); // 将改数组转换成ArrayList
            HistoryCityAdapter historyCityAdapter = new HistoryCityAdapter(R.layout.item_choose_area_history);
            historyCityAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    String city = historyList.get(position);
                    // 更新历史选择
                    updateHistoryChoose(city);
                    Intent intentResultArea = new Intent();
                    intentResultArea.putExtra("result", city);
                    intentResultArea.putExtra("stopoverSequence", stopoverSequence);
                    setResult(RESULT_OK, intentResultArea);
                    finish();
                }
            });

            //设置布局的方式
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            mRvHistory.setLayoutManager(layoutManager);
            mRvHistory.setAdapter(historyCityAdapter);
            historyCityAdapter.setNewData(historyList);
            mRvHistory.setVisibility(View.VISIBLE);
            mTvChooseHistory.setVisibility(View.VISIBLE);
        } else {
            mRvHistory.setVisibility(View.GONE);
            mTvChooseHistory.setVisibility(View.GONE);
        }
    }

    // 历史选择适配器
    public class HistoryCityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public HistoryCityAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder holder, String city) {
            if (!TextUtils.isEmpty(city)) {
                String[] tmpCity = city.split("/"); // 斜杠截取 保存在数组中
                if (tmpCity.length == 1) {
                    holder.setText(R.id.tv_history_city, city); // 城市名称
                } else if (tmpCity.length == 2) {
                    holder.setText(R.id.tv_history_city, tmpCity[1]);
                } else if (tmpCity.length == 3) {
                    holder.setText(R.id.tv_history_city, tmpCity[2]);
                }

            }

        }
    }

    // 更新历史选择城市
    private void updateHistoryChoose(String city) {
        if (!TextUtils.isEmpty(city)) {
            String longHistory = mSp.getString(mPhone, "");  // 获取之前保存的历史记录
            String[] tmpHistory = longHistory.split(","); // 逗号截取 保存在数组中
            List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); // 将改数组转换成ArrayList
            if (historyList.size() > 0) {
                // 移除之前重复添加的元素
                for (int i = 0; i < historyList.size(); i++) {
                    if (city.equals(historyList.get(i))) {
                        historyList.remove(i);
                        break;
                    }
                }
                historyList.add(0, city); // 将新选择的文字添加集合的第0位也就是最前面(倒序)
                if (historyList.size() > MAX_HISTORY_COUNT) {
                    historyList.remove(historyList.size() - 1); // 最多保存 MAX_HISTORY_COUNT 条搜索记录 删除最早搜索的那一项
                }
                // 逗号拼接
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < historyList.size(); i++) {
                    sb.append(historyList.get(i) + ",");
                }
                // 保存到sp
                mEditor.putString(mPhone, sb.toString());
                mEditor.commit();
            } else {
                //之前未添加过
                mEditor.putString(mPhone, city + ",");
                mEditor.commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_AREA) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Intent intentResultArea = new Intent();
                intentResultArea.putExtra("result", result);
                intentResultArea.putExtra("stopoverSequence", stopoverSequence);
                // 更新历史选择
                updateHistoryChoose(result);
                setResult(RESULT_OK, intentResultArea);
                finish();
            }
        }
    }
}
