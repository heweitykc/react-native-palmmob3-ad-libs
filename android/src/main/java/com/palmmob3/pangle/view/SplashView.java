package com.palmmob3.pangle.view;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.MainThread;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.facebook.react.bridge.ReactContext;
import com.palmmob3.R;
import com.palmmob3.Utils;


public class SplashView extends RelativeLayout {

  private static final String TAG = "SplashAd";

  private ReactContext reactContext;
  private Activity mContext;
  private TTAdNative _ttAdNative;
  private String _posId = "";
  final protected RelativeLayout mContainer;

  public SplashView(ReactContext context) {
    super(context);

    mContext = context.getCurrentActivity();
    reactContext = context;
    inflate(context, R.layout.full_view, this);
    mContainer = (RelativeLayout)findViewById(R.id.full_container);
    Utils.setupLayoutHack(this);
  }

  public void setPosId(String posId) {
    this._posId = posId;
  }

  public void showAd() {
    Log.d(TAG, "showAd: posId=" + _posId);
    loadAd();
  }

  private void loadAd() {
    if(_ttAdNative == null){
      _ttAdNative = TTAdSdk.getAdManager().createAdNative(this.mContext);
    }
    mContainer.removeAllViews();
    AdSlot adSlot = new AdSlot.Builder()
            .setCodeId(_posId)
            .setImageAcceptedSize(1080, 1920)
            .build();

    _ttAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
      //请求广告失败
      @Override
      @MainThread
      public void onError(int code, String message) {
        Utils.sendEvent(reactContext, getId(), Utils.AD_ERROR, message);
      }

      //请求广告超时
      @Override
      @MainThread
      public void onTimeout() {
        Utils.sendEvent(reactContext, getId(), Utils.AD_ERROR, "no ad timeout");
      }

      //请求广告成功
      @Override
      @MainThread
      public void onSplashAdLoad(TTSplashAd ad) {
        if (ad == null) {
          return;
        }
        View view = ad.getSplashView();
        if (view != null && mContainer != null) {
          mContainer.removeAllViews();
          mContainer.addView(view);
          Utils.sendEvent(reactContext, getId(), Utils.AD_LOAD, "ad load");
        }else {
          //开发者处理跳转到APP主页面逻辑
        }
      }
    }, 3000);
  }
}
