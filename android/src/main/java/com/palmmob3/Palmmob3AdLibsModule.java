// Palmmob3AdLibsModule.java

package com.palmmob3;

import android.widget.CheckBox;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.palmmob3.gdt.view.RewardAd;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.comm.managers.GDTADManager;
import com.qq.e.comm.managers.setting.GlobalSetting;

public class Palmmob3AdLibsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public Palmmob3AdLibsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Palmmob3AdLibs";
    }

    @ReactMethod
    public void initGDT(String appID, Promise promise) {
        GDTADManager.getInstance().initWith(reactContext, appID);
        promise.resolve(null);
    }

    @ReactMethod
    public void setGDTChannel(int channelid, Promise promise) {
        GlobalSetting.setChannel(channelid);
        promise.resolve(null);
    }

    @ReactMethod
    public void loadRewardVideo(String posId, Promise promise) {
        RewardAd ad = new RewardAd(posId, this.reactContext);
        ad.loadAd();
        promise.resolve(null);
    }

    @ReactMethod
    public void initPangle(String appID, String appName, boolean debug,  Promise promise) {
        TTAdConfig config = new TTAdConfig.Builder()
                .appId(appID)
                .useTextureView(true)
                .appName(appName)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true)
                .debug(debug)
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI)
                .supportMultiProcess(false)
                .build();

        TTAdSdk.init(this.reactContext,config,new TTAdSdk.InitCallback() {
            @Override
            public void success() {
                promise.resolve(true);
            }

            @Override
            public void fail(int code, String msg) {
                promise.resolve(false);
            }
        });
    }
}
