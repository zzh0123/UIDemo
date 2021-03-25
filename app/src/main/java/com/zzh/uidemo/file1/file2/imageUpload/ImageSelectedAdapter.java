package com.zzh.uidemo.file1.file2.imageUpload;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hmfl.careasy.baselib.R;
import com.hmfl.careasy.baselib.library.imageselector.bean.SingleImage;
import com.hmfl.careasy.baselib.library.imageselector.view.SquareImageView;
import com.hmfl.careasy.baselib.library.imageselector.view.SquareTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ccMagic on 2018/3/12.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：选择了的图片的显示
 */
public class ImageSelectedAdapter extends BaseAdapter {

    private static final String TAG = "ImageSelectedAdapter";
    /**
     * 选择了的图片信息
     */
    private ArrayList<SingleImage> mImagesArrayList;
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private int mMaxSelectCount;
    /**
     * 记录是否添加过了默认项
     * true :添加过了
     * false:没有添加
     */
    private boolean mHaveDefaultItem = false;

    /**
     * 进度条显示格式
     */
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00%");

    /**
     * 用于将处理按顺序放进消息队列
     */
    private View mConvertView;

    private int mDefaultImageId;

    private RequestManager mGlid;
    private CarEasyReImageSelector.SelectFinishListener mSelectFinishListener;

    private OnDeleteClickListener onDeleteClickListener;
    /**
     * @param context         Context
     * @param imagesArrayList 选择了的图片信息
     * @param maxSelectCount  图片的最大选择数量，小于等于0时不限数量，为1是表示单选。
     * @param defaultImageId  默认显示的资源图片。
     */
    public ImageSelectedAdapter(@NonNull Activity context, @NonNull ArrayList<SingleImage> imagesArrayList, int maxSelectCount, int defaultImageId) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mImagesArrayList = imagesArrayList;
        mMaxSelectCount = maxSelectCount;
        mDefaultImageId = defaultImageId;
        mGlid = Glide.with(mContext);
    }

    /**
     * 记录是否在最后一项添加了默认的显示内容
     */
    public boolean ismHaveDefaultItem() {
        return mHaveDefaultItem;
    }

    /**
     * 设置是否在最后一项添加了默认的显示内容
     *
     * @param mHaveDefaultItem true添加过了，false没有添加
     */
    public void setmHaveDefaultItem(boolean mHaveDefaultItem) {
        this.mHaveDefaultItem = mHaveDefaultItem;
    }

    @Override
    public int getCount() {
        return mImagesArrayList.size();
    }

    @Override
    public SingleImage getItem(int position) {
        return mImagesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectFinishListener(CarEasyReImageSelector.SelectFinishListener selectFinishListener) {
        mSelectFinishListener = selectFinishListener;
    }

    /**
     * 对进度条的管理放在CarEasyReImageSelector中，
     * 进度条管理，之所以这么写，为不是再adapter中写，
     * 主要是解决NoScrollGridView高度不确定，
     * 多次调用getView测量高度，导致第一个item永远与保存的TextView不一致的问题。
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() != null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.car_easy_mutil_image_select_adapter, parent, false);
            viewHolder.picItem = (SquareImageView) convertView.findViewById(R.id.pic_item);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete);
            viewHolder.progressbar = (SquareTextView) convertView.findViewById(R.id.progressbar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mConvertView = convertView;
        if (mImagesArrayList.get(position).isShowDefault()) {
            //默认添加点击重选的图片
//            viewHolder.picItem.setImageResource(R.mipmap.car_easy_add_parcar_icon);
            viewHolder.picItem.setImageResource(mDefaultImageId);
            viewHolder.delete.setVisibility(View.GONE);
            viewHolder.delete.setOnClickListener(null);
            viewHolder.progressbar.setVisibility(View.GONE);
            viewHolder.picItem.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            if (mImagesArrayList.get(position).isModify()) {
                //兼容修改

                mGlid.load(mImagesArrayList.get(position).getUploadedPath().replace("https", "http"))
                        .placeholder(R.mipmap.car_easy_add_loading_icon)
                        .error(R.mipmap.car_easy_add_loading_icon)
                        .into(viewHolder.picItem);

            } else {
                mGlid.load(mImagesArrayList.get(position).getPath())
                        .placeholder(R.mipmap.car_easy_add_loading_icon)
                        .error(R.mipmap.car_easy_add_loading_icon)
                        .into(viewHolder.picItem);
            }
            viewHolder.picItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (mImagesArrayList.get(position).isDelete()) {
                viewHolder.delete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.delete.setVisibility(View.GONE);
            }
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(mImagesArrayList.get(position));
                    mImagesArrayList.get(position).setProgressListener(null);
                    mImagesArrayList.get(position).setProgressTextView(null);
                    mImagesArrayList.remove(mImagesArrayList.get(position));
                    //如果图片信息小于最大上传的图片限制，则添加默认的
                    if ((mMaxSelectCount == 0 || mImagesArrayList.size() < mMaxSelectCount) && !ismHaveDefaultItem()) {
                        SingleImage imageSelectedBean = new SingleImage("", true);
                        mImagesArrayList.add(imageSelectedBean);
                        setmHaveDefaultItem(true);
                    }
                    notifyDataSetChanged();
                    Log.i(TAG, "onClick: SelfFinishInputUI");
                    if (mSelectFinishListener != null){
                        mSelectFinishListener.selectFinish();
                    }
                }
            });
            if (TextUtils.isEmpty(mImagesArrayList.get(position).getUploadedPath())) {
                viewHolder.progressbar.setVisibility(View.VISIBLE);
            } else {
                viewHolder.progressbar.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        SquareImageView picItem;
        ImageView delete;
        SquareTextView progressbar;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(SingleImage singleImage);
    }
}
