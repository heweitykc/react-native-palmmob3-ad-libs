package com.palmmob3.gdt.view;


import android.util.Log;
import android.widget.RelativeLayout;
import android.app.Activity;

import com.facebook.react.bridge.ReactContext;
import com.palmmob3.R;
import com.palmmob3.Utils;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADZoomOutListener;
import com.qq.e.comm.util.AdError;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class SplashView extends RelativeLayout implements SplashADZoomOutListener {

  private static final String TAG = "SplashAd";

  private ReactContext reactContext;
  private Activity mContext;
  private SplashAD _splashAD;
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
    if(_splashAD == null){
      _splashAD = new SplashAD(reactContext.getCurrentActivity(), _posId, this);
    }
    mContainer.removeAllViews();
    _splashAD.fetchFullScreenAndShowIn(mContainer);
  }

  @Override
  public void onZoomOut() {
    Log.d(TAG,"onZoomOut");
  }

  @Override
  public void onZoomOutPlayFinish() {
    Log.d(TAG,"onZoomOutPlayFinish");
  }

  @Override
  public boolean isSupportZoomOut() {
    return true;
  }

  @Override
  public void onADDismissed() {
    Log.d(TAG,"onADDismissed");
    Utils.sendEvent(reactContext, getId(), Utils.AD_CLOSE, "ad dismissed");
  }

  @Override
  public void onNoAD(AdError error) {
    Log.d(TAG,"onNoAD:" + error.getErrorMsg());
    Utils.sendEvent(reactContext, getId(), Utils.AD_ERROR, "no ad");
  }

  @Override
  public void onADPresent() {
    Log.d(TAG,"onADPresent");
//    Utils.sendEvent(reactContext, getId(), Utils.AD_SHOW, "ad present");
  }

  @Override
  public void onADClicked() {
    Log.d(TAG,"onADClicked");
    Utils.sendEvent(reactContext, getId(), Utils.AD_CLICK, "ad clicked");
  }

  @Override
  public void onADTick(long millisUntilFinished) {
    Log.d(TAG,"onADTick:" + millisUntilFinished);
  }

  @Override
  public void onADExposure() {
    Utils.sendEvent(reactContext, getId(), Utils.AD_SHOW, "ad exposure");
  }

  @Override
  public void onADLoaded(long expireTimestamp) {
    Log.d(TAG,"onADLoaded");
    Utils.sendEvent(reactContext, getId(), Utils.AD_LOAD, "ad load");
  }
}
