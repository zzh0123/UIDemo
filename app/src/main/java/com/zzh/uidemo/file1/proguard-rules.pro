# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\AndroidTool\adt-bundle-windows-x86\adt-bundle-windows-x86\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarnings

-keep class packagename.** {*;}


-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

#支付相关
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn com.pingplusplus.**
-keep class com.pingplusplus.** {*;}

-dontwarn com.baidu.**
-keep class com.baidu.** {*;}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn javax.servlet.**
-dontwarn org.joda.time.**
-dontwarn org.w3c.dom.**

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#百度地图
-keep class com.baidu.** {*;}
#-keep class vi.com.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep class * implements java.io.Serializable

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep public class com.hmfl.careasy.baselib.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.apply.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.common.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.user.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.oil.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.weibaobean.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.order.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.scheduledbus.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.diaodu.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.diaodu.common.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.diaodu.order.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.diaodu.start.*{*;}
-keep public class com.hmfl.careasy.baselib.bean.enums.*{*;}
-keep public class com.hmfl.careasy.baselib.library.imageselector.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.diaodu.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.diaodu.bean.common.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.diaodu.bean.order.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.diaodu.bean.start.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.drivertask.twosteps.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.jiaochecheck.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.myorder.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.usecarcheck.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.emergency.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.performance.bean.*{*;}
-keep public class com.hmfl.careasy.onekey.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.refueling.executetask.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.refueling.main.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.bus.bean.*{*;}
-keep public class com.hmfl.careasy.onekeyenforcelaw.gongwu.bean.*{*;}
-keep public class com.hmfl.careasy.patrolcar.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.maintab.bean.*{*;}
-keep public class com.hmfl.careasy.sendcar.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.holidaytravel.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.bean.DTO.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.bean.common.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.bean.order.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.bean.start.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.drivertask.twosteps.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.drivertask.threeorfoursteps.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.jiaochecheck.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.usecarcheck.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.performance.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.refueling.executetask.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.refueling.main.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.serverpoint.bean.*{*;}
-keep public class com.hmfl.careasy.onekeyenforcelaw.rent.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.onkey.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.maintab.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.weibao.bean.*{*;}
-keep public class com.hmfl.careasy.attendance.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.carregistration.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.earlywarning.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.onlinecarplatform.onlineDTO.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.myorder.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.insurance.bean.*{*;}
-keep public class com.hmfl.careasy.personaltravel.electronicinvoice.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.travel.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.message.gongwu.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.message.rent.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.login.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.addcompany.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.weizhang.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.trainfly.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.useinstruction.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.baseadapter.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.actualtime.bean.*{*;}
-keep public class com.hmfl.careasy.bus.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.mymessage.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.travelhelp.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.feedback.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.helpcenter.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.mysetting.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.servericecenter.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.siwuperson.onlinecarplatform.apply.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.rentplatform.diaodu.diaobo.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.chatui.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.clusterutil.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.gongwu.gongwuplatform.establishmanage.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.maintab.driver.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.address.bean.*{*;}
-keep public class com.hmfl.careasy.livepigmodule.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.livepigmodule.drivertask.bean.*{*;}
-keep public class com.hmfl.careasy.livepigmodule.myorder.bean.*{*;}
-keep public class com.hmfl.careasy.scheduledbus.bus.bean.*{*;}
-keep public class com.hmfl.careasy.scheduledbus.busnew.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.baseadapter.bean.order.*{*;}
-keep public class com.hmfl.careasy.baselib.base.newonekeyguide.bean.*{*;}
-keep public class com.hmfl.careasy.weibao.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.maintab.common.bean.*{*;}
-keep public class com.hmfl.careasy.reimbursement.bean.*{*;}
-keep public class com.hmfl.careasy.onekey.bean.*{*;}
-keep public class com.hmfl.careasy.onekeyenforcelaw.rent.bean.*{*;}
-keep public class com.hmfl.careasy.onekeyenforcelaw.gongwu.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.applycaruser.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.enforcelawapply.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.order.gw.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.patrolcar.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.drivermission.gongwu.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.jiaoche.gongwuplatform.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.worktab.beans.*{*;}
-keep public class com.hmfl.careasy.newonekey.travel.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.dispatching.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.driverstatus.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.carstatus.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.maintab.beans.*{*;}
-keep public class com.hmfl.careasy.sendcar.bean.*{*;}
-keep public class com.hmfl.careasy.patrolcar.bean.*{*;}
-keep public class com.hmfl.careasy.holidaytravel.bean.*{*;}
-keep public class com.hmfl.careasy.travelhelp.bean.*{*;}
-keep public class com.hmfl.careasy.check.bean.*{*;}
-keep public class com.hmfl.careasy.gongwu.bean.*{*;}
-keep public class com.example.carstatusmodule.bean.*{*;}
-keep public class com.hmlf.careasy.servicepointmodule.bean.*{*;}
-keep public class com.hmfl.careasy.drivermissionmodule.rent.twosteps.bean.*{*;}

-keep public class com.hmfl.careasy.carregistration.rent.bean.*{*;}
-keep public class com.hmfl.careasy.jiaoche.rent.bean.*{*;}

-keep public class com.hmfl.careasy.drivertask.tongqin.bean.*{*;}
-keep public class com.hmfl.careasy.personaltravel.electronicinvoice.bean.*{*;}
-keep public class com.hmfl.careasy.personaltravel.personapply.bean.*{*;}
-keep public class com.hmfl.careasy.allocation.bean.*{*;}
-keep public class com.hmfl.careasy.allocation.rent.bean.*{*;}
-keep public class com.hmfl.careasy.drivermissionmodule.rent.diaobodrivertask.bean.*{*;}
-keep public class com.hmfl.careasy.monitor.bean.*{*;}


-keep public class com.hmfl.careasy.refueling.gongwuplatform.executetask.bean.*{*;}
-keep public class com.hmfl.careasy.refueling.gongwuplatform.main.bean.*{*;}
-keep public class com.hmfl.careasy.refueling.rentplatform.executetask.bean.*{*;}
-keep public class com.hmfl.careasy.refueling.rentplatform.main.bean.*{*;}
-keep public class com.hmfl.careasy.vehiclestatistics.bean.*{*;}
-keep public class com.hmfl.careasy.organaffairs.beans.*{*;}
-keep public class com.hmfl.careasy.order.gw.bean.*{*;}

-keep public class com.hmfl.careasy.personalcar.bean.*{*;}
-keep public class com.hmfl.careasy.dispatchingmodule.gongwuplatform.bean.*{*;}
-keep public class com.hmfl.careasy.dispatchingmodule.rentplatform.bean.*{*;}
-keep public class com.hmfl.careasy.dispatchingmodule.servicecenter.bean.*{*;}
-keep public class com.hmfl.careasy.scheduledbus.realtime.bean.*{*;}
-keep public class com.hmfl.careasy.scheduledbus.management.bean.*{*;}
-keep public class com.hmfl.careasy.dispatchingmodule.servicecenter.bean.*{*;}
-keep public class com.hmfl.careasy.emergency.bean.*{*;}
-keep public class com.hmfl.careasy.performance.gw.bean.*{*;}
-keep public class com.hmfl.careasy.performance.servicecenter.bean.*{*;}
-keep public class com.hmfl.careasy.complaint.bean.*{*;}
-keep public class com.hmfl.careasy.earlywarning.rentplatform.bean.*{*;}
-keep public class com.hmfl.careasy.jiaoche.gongwuplatform.bean.*{*;}
-keep public class com.hmfl.careasy.jiaoche.servicecenter.bean.*{*;}
-keep public class com.hmfl.careasy.establishmanage.bean.*{*;}
-keep public class com.hmfl.careasy.carregistration.servicecenter.bean.*{*;}
-keep public class com.hmfl.careasy.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.drivermissionmodule.gongwu.bean.*{*;}
-keep public class com.hmfl.careasy.drivermissionmodule.rent.cityinter.bean.*{*;}
-keep public class com.hmfl.careasy.personaltravel.order.bean.*{*;}
-keep public class com.hmfl.careasy.personaltravel.personapply.bean.*{*;}
-keep public class com.hmfl.careasy.baselib.base.messageboard.bean.*{*;}
-keep public class com.hmfl.careasy.earlywarning.servicecenterplatform.bean.*{*;}
-keep public class com.hmfl.careasy.earlywarning.gongwuplatform.bean.*{*;}
-keep public class com.hmfl.careasy.attendance.bean.*{*;}
-keep public class com.hmfl.careasy.cartrack.bean.*{*;}
-keep public class com.hmfl.careasy.custominfo.bean.*{*;}
-keep public class com.hmfl.careasy.customorder.bean.*{*;}
-keep public class com.hmfl.careasy.keycabinet.bean.*{*;}
-keep public class com.hmfl.careasy.settlement.bean.*{*;}
-keep public class com.hmfl.careasy.invoicemanagement.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.check.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.common.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.applycar.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.emergency.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.cartrack.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.earlywarning.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.carregistration.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.patrolcar.bean.*{*;}
-keep public class com.hmfl.careasy.newonekey.newpatrolcar.bean.*{*;}


# https://github.com/CymChad/BaseRecyclerViewAdapterHelper 混淆
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers class $ extends com.chad.library.adapter.base.BaseViewHolder {
    <init>(...);
}
-keepclassmembers class * extends com.chad.library.adapter.base.BaseViewHolder {
    <init>(...);
}



#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}


#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep class com.j256.**

-keepclassmembers class com.j256.** { *; }

-keep enum com.j256.**

-keepclassmembers enum com.j256.** { *; }

-keep interface com.j256.**

-keepclassmembers interface com.j256.** { *; }
# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# 支付宝配置的代码混淆
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#环信混淆
-keep class com.hyphenate.** {*;}
-keep class com.superrtc.** {*;}
-dontwarn  com.hyphenate.**
-keep class internal.org.** {*;}
-dontwarn internal.org.**
-keep class android.net.** {*;}
-dontwarn android.net.**
-keep class com.android.internal.http.multipart.** {*;}
-dontwarn com.android.internal.http.multipart.**
-keep class org.apache.** {*;}
-dontwarn org.apache.**

-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}
#确保openFileChooser方法不被混淆    不加混淆Android 4.X 不能正常使用
-keepclassmembers class * extends android.webkit.WebChromeClient{
 public void openFileChooser(...);
 }

#路由混淆
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# If you use the byType method to obtain Service, add the following rules to protect the interface:
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#React Native
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.proguard.annotations.DoNotStrip
-keep,allowobfuscation @interface com.facebook.proguard.annotations.KeepGettersAndSetters
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.proguard.annotations.DoNotStrip class *
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.proguard.annotations.DoNotStrip *;
    @com.facebook.common.internal.DoNotStrip *;
}

-keepclassmembers @com.facebook.proguard.annotations.KeepGettersAndSetters class * {
  void set*(***);
  *** get*();
}

-keep class * extends com.facebook.react.bridge.JavaScriptModule { *; }
-keep class * extends com.facebook.react.bridge.NativeModule { *; }
-keepclassmembers,includedescriptorclasses class * { native <methods>; }
-keepclassmembers class *  { @com.facebook.react.uimanager.UIProp <fields>; }
-keepclassmembers class *  { @com.facebook.react.uimanager.annotations.ReactProp <methods>; }
-keepclassmembers class *  { @com.facebook.react.uimanager.annotations.ReactPropGroup <methods>; }

-dontwarn com.facebook.react.**

-keep class com.facebook.react.bridge.CatalystInstanceImpl { *; }
-keep class com.facebook.react.bridge.JavaScriptExecutor { *; }
-keep class com.facebook.react.bridge.queue.NativeRunnable { *; }
-keep class com.facebook.react.bridge.ReadableType { *; }

#日历控件混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

# 天气sdk
-keep class heweather.com.weathernetsdk.bean.** { *; }
-keep class heweather.com.weathernetsdk.view.** { *; }

#udesk
-keep class udesk.** {*;}
-keep class cn.udesk.**{*; }
#七牛
-keep class okhttp3.** {*;}
-keep class okio.** {*;}
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings
#smack
-keep class org.jxmpp.** {*;}
-keep class de.measite.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.xmlpull.** {*;}
-dontwarn org.xbill.**
-keep class org.xbill.** {*;}

#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#freso
-keep class com.facebook.** {*; }
-keep class com.facebook.imagepipeline.** {*; }
-keep class com.facebook.animated.gif.** {*; }
-keep class com.facebook.drawee.** {*; }
-keep class com.facebook.drawee.backends.pipeline.** {*; }
-keep class com.facebook.imagepipeline.** {*; }
-keep class bolts.** {*; }
-keep class me.relex.photodraweeview.** {*; }

-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**


#bugly
-keep class com.tencent.bugly.** {*; }
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#agora
-keep class io.agora.**{*;}

#基线包使用，生成mapping.txt
-printmapping mapping.txt
#生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
-dontwarn com.alibaba.sdk.android.utils.**
#防止inline
-dontoptimize

-keepclassmembers class com.hmfl.careasy.BaseApplication {
    public <init>();
}

#指纹设置跳转类混淆
-keep class com.hmfl.careasy.baselib.base.mysetting.FingerLib.FingerprintUtil{ *; }

#assets
-keep class com.hmfl.assetsmodule.bean.*{*;}
-keep class com.hmfl.assetsmodule.application.*{*;}

#公务接待
-keep class com.hmfl.careasy.officialreceptions.beans.*{*;}

#------------------AndroidX start-------------
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**
#------------------AndroidX End-------------

#------------------okdownload start-------------
-keepnames class com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection
-keep class com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite {
        public com.liulishuo.okdownload.core.breakpoint.DownloadStore createRemitSelf();
        public com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite(android.content.Context);
}
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#------------------okdownload end-------------