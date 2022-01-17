package com.palmmob3.gdt.view;


import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.widget.Toast;

import com.facebook.react.bridge.ReactContext;
import com.palmmob3.R;
import com.palmmob3.Utils;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADZoomOutListener;
import com.qq.e.comm.util.AdError;
import com.palmmob3.gdt.adapter.PosIdArrayAdapter;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import okhttp3.internal.Util;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RewardAd implements RewardVideoADListener {

  private static final String TAG = "RewardAd";

  private ReactContext _reactContext;
  private String _posId = "";
  private RewardVideoAD _rvad;
  private boolean _isLoadSuccess;

  public RewardAd(String posId, ReactContext reactContext) {
    _posId = posId;
    _reactContext = reactContext;
    _rvad = new RewardVideoAD(_reactContext, _posId, this, true);
  }

  public void loadAd() {
    _isLoadSuccess = false;
    _rvad.loadAD();
  }

  public void showAd() {
    _rvad.showAD(_reactContext.getCurrentActivity());
  }

  /**
   * 广告加载成功，可在此回调后进行广告展示
   **/
  @Override
  public void onADLoad() {
    Log.i(TAG, "onADLoad");
    _isLoadSuccess = true;
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_LOAD, "ad load");
  }

  /**
   * 视频素材缓存成功，可在此回调后进行广告展示
   */
  @Override
  public void onVideoCached() {
    Log.i(TAG, "onVideoCached");
    // Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_LOAD, "ad video load");
//    this.showAd();
  }

  /**
   * 激励视频广告页面展示
   */
  @Override
  public void onADShow() {
    Log.i(TAG, "onADShow");
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_SHOW, "ad show");
  }

  /**
   * 激励视频广告曝光
   */
  @Override
  public void onADExpose() {
    Log.i(TAG, "onADExpose");
  }

  /**
   * 激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
   *
   * @param map 若选择了服务端验证，可以通过 ServerSideVerificationOptions#TRANS_ID 键从 map 中获取此次交易的 id；若未选择服务端验证，则不需关注 map 参数。
   */
  @Override
  public void onReward(Map<String, Object> map) {
    Log.i(TAG, "onReward " + map.get(ServerSideVerificationOptions.TRANS_ID));  // 获取服务端验证的唯一 ID
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_REWARD, "ad reward");
  }

  /**
   * 激励视频广告被点击
   */
  @Override
  public void onADClick() {
    Log.i(TAG, "onADClick");
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CLICK, "ad click");
  }

  /**
   * 激励视频播放完毕
   */
  @Override
  public void onVideoComplete() {
    Log.i(TAG, "onVideoComplete");
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_COMPLETE, "ad complete");
  }

  /**
   * 激励视频广告被关闭
   */
  @Override
  public void onADClose() {
    Log.i(TAG, "onADClose");
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_CLOSE, "ad close");
  }

  /**
   * 广告流程出错
   */
  @Override
  public void onError(AdError adError) {
    String msg = String.format(Locale.getDefault(), "onError, error code: %d, error msg: %s",
            adError.getErrorCode(), adError.getErrorMsg());
    Log.i(TAG, "onError, adError=" + msg);
    Utils.emitEvent(_reactContext, TAG + "_" + Utils.AD_ERROR, "ad err :" + msg);
  }
}
