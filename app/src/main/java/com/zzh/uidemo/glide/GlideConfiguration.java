package com.zzh.uidemo.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;


//@GlideModule
@GlideModule(glideName="Glide")
public class GlideConfiguration extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置缓存到外部存储器
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context));
    }
}
