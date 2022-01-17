package com.palmmob3.pangle.view;


import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.facebook.react.bridge.ReactContext;
import com.palmmob3.Utils;

public class RewardAd {

    private static final String TAG = "RewardAd";

    private ReactContext _reactContext;
    private String _posId = "";
    private TTAdNative _ttAdNative;
    private TTRewardVideoAd _ttRewardVideoAd;
    private boolean _isLoadSuccess;

    public RewardAd(String posId, ReactContext reactContext) {
        _posId = posId;
        _reactContext = reactContext;
        if(_ttAdNative == null){
            _ttAdNative = TTAdSdk.getAdManager().createAdNative(_reactContext);
        }
    }

    public void loadAd() {
        _isLoadSuccess = false;
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(_posId)
                .setExpressViewAcceptedSize(500,500)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();

        _ttAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String msg) {
                Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_ERROR, "ad err :" + msg);
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                _isLoadSuccess = true;
                _ttRewardVideoAd = ttRewardVideoAd;
                _ttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_SHOW, "ad show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CLICK, "ad click");
                    }

                    @Override
                    public void onAdClose() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CLOSE, "ad close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_COMPLETE, "ad complete");
                    }

                    @Override
                    public void onVideoError() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_ERROR, "ad err :");
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int errorCode, String errorMsg) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName + " errorCode:" + errorCode + " errorMsg:" + errorMsg;
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_REWARD, logString);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CLOSE, "ad close");
                    }
                });

                Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_LOAD, "ad load");
            }

            @Override
            public void onRewardVideoCached() {

            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ad) {
                Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CACHED, "ad video cacheed");
            }

        });
    }

    public void showAd() {
        _ttRewardVideoAd.showRewardVideoAd(_reactContext.getCurrentActivity(), TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
    }


}