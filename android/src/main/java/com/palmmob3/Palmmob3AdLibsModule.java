// Palmmob3AdLibsModule.java

package com.palmmob3;

// import com.bytedance.sdk.openadsdk.TTAdConfig;
// import com.bytedance.sdk.openadsdk.TTAdConstant;
// import com.bytedance.sdk.openadsdk.TTAdSdk;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;

import com.palmmob3.gdt.view.RewardAd;
import com.qq.e.comm.managers.GDTADManager;
import com.qq.e.comm.managers.setting.GlobalSetting;

public class Palmmob3AdLibsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private  com.palmmob3.gdt.view.RewardAd gdtReward;
//    private  com.palmmob3.pangle.view.RewardAd pangleReward;

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
        gdtReward = new RewardAd(posId, this.reactContext);
        gdtReward.loadAd();
        promise.resolve(null);
    }

    @ReactMethod
    public void showRewardVideo(Promise promise) {
        if(gdtReward == null){
            return;
        }
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gdtReward.showAd();
            }
        });
        promise.resolve(null);
    }

    // @ReactMethod
    // public void initPangle(String appID, String appName, boolean debug,  Promise promise) {
    //     TTAdConfig config = new TTAdConfig.Builder()
    //             .appId(appID)
    //             .useTextureView(true)
    //             .appName(appName)
    //             .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
    //             .allowShowNotify(true)
    //             .debug(debug)
    //             .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI)
    //             .supportMultiProcess(false)
    //             .build();

    //     UiThreadUtil.runOnUiThread(new Runnable() {
    //         @Override
    //         public void run() {
    //             TTAdSdk.init(reactContext, config, new TTAdSdk.InitCallback() {
    //                 @Override
    //                 public void success() {
    //                     promise.resolve(true);
    //                 }

    //                 @Override
    //                 public void fail(int code, String msg) {
    //                     promise.resolve(false);
    //                 }
    //             });
    //         }
    //     });
    // }

    // @ReactMethod
    // public void loadPangleRewardVideo(String posId, Promise promise) {
    //     pangleReward = new com.palmmob3.pangle.view.RewardAd(posId, this.reactContext);

    //     UiThreadUtil.runOnUiThread(new Runnable() {
    //         @Override
    //         public void run() {
    //             pangleReward.loadAd();
    //             promise.resolve(null);
    //         }
    //     });
    // }

    // @ReactMethod
    // public void showPangleRewardVideo(Promise promise) {
    //     if(pangleReward == null){
    //         return;
    //     }

    //     UiThreadUtil.runOnUiThread(new Runnable() {
    //         @Override
    //         public void run() {
    //             pangleReward.showAd();
    //             promise.resolve(null);
    //         }
    //     });
    // }
}
