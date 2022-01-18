package com.palmmob3.pangle.view;


import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.app.Activity;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.facebook.react.bridge.ReactContext;
import com.palmmob3.R;
import com.palmmob3.Utils;

import java.util.List;


public class BannerView extends RelativeLayout implements  TTNativeExpressAd.AdInteractionListener {

  private static final String TAG = "BannerAd";

  private ReactContext reactContext;
  private String _posId = "";
  private TTAdNative _ttAdNative;
  private TTNativeExpressAd mTTAd;

  final protected RelativeLayout mContainer;

  public BannerView(ReactContext context) {
    super(context);

    reactContext = context;
    inflate(context, R.layout.full_view, this);
    mContainer = findViewById(R.id.full_container);
    Utils.setupLayoutHack(this);

    if(_ttAdNative == null){
      _ttAdNative = TTAdSdk.getAdManager().createAdNative(reactContext);
    }
  }

  public void setPosId(String posId) {
    this._posId = posId;
  }

  public void showAd() {
    Log.d(TAG, "showAd: posId=" + _posId);
    loadAd();
  }

  private void loadAd() {

    AdSlot adSlot = new AdSlot.Builder()
            .setCodeId(this._posId) //广告位id
            .setSupportDeepLink(true)
            .setAdCount(3) //请求广告数量为1
            .setExpressViewAcceptedSize(300,75)
            .setAdLoadType(TTAdLoadType.LOAD)
            .build();


    _ttAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {

      @Override
      public void onError(int i, String s) {
        Log.d(TAG,"onError:" + i + " - " + s);
      }

      @Override
      public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
        mTTAd = list.get(0);
        mTTAd.setSlideIntervalTime(5 * 1000);
        mTTAd.setExpressInteractionListener(BannerView.this);
        mTTAd.render();
      }

    });

  }

  @Override
  public void onAdDismiss() {
    Log.d(TAG,"onAdDismiss");
  }

  @Override
  public void onAdClicked(View view, int i) {
    Log.d(TAG,"onAdClicked");
  }

  @Override
  public void onAdShow(View view, int i) {
    Log.d(TAG,"onAdShow");
  }

  @Override
  public void onRenderFail(View view, String s, int i) {
    Log.d(TAG,"onRenderFail");
  }

  @Override
  public void onRenderSuccess(View view, float v, float v1) {
    mContainer.removeAllViews();
    mContainer.addView(view);
  }
}
