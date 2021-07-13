package com.palmmob3;

import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

public class Utils {

    static public final String AD_ERROR  = "onAdError";
    static public final String AD_CLICK  = "onAdClick";
    static public final String AD_CLOSE  = "onAdClose";
    static public final String AD_LOAD   = "onAdLoad";
    static public final String AD_SHOW   = "onAdShow";

    // for fix addView not showing ====
    public static void setupLayoutHack(final ViewGroup view) {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            // 这个事件很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren(view);
                view.getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    // 这个函数很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
    public static void manuallyLayoutChildren(ViewGroup view) {
        int w,h;
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            child.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
            w = child.getMeasuredWidth();
            h = child.getMeasuredHeight();
            Log.d("layout", "w=" + w + ", h=" + h);
            child.layout(0, 0, w, h);
        }
    }

    public static void sendEvent(ReactContext reactContext, int viewid, String evt, String message) {
        WritableMap event = Arguments.createMap();
        event.putString("message", message);
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(viewid, evt, event);
    }

    public static Map getEventType() {
        final String phasedRegistrationNames = "phasedRegistrationNames";
        final String bubbled = "bubbled";
        return MapBuilder.builder()
                .put(Utils.AD_CLICK,
                        MapBuilder.of(
                                phasedRegistrationNames,
                                MapBuilder.of(bubbled, Utils.AD_CLICK)))
                .put(Utils.AD_ERROR,
                        MapBuilder.of(
                                phasedRegistrationNames,
                                MapBuilder.of(bubbled, Utils.AD_ERROR)))
                .put(Utils.AD_CLOSE,
                        MapBuilder.of(
                                phasedRegistrationNames,
                                MapBuilder.of(bubbled, Utils.AD_CLOSE)))
                .put(Utils.AD_SHOW,
                        MapBuilder.of(
                                phasedRegistrationNames,
                                MapBuilder.of(bubbled, Utils.AD_SHOW)))
                .put(Utils.AD_LOAD,
                        MapBuilder.of(
                                phasedRegistrationNames,
                                MapBuilder.of(bubbled, Utils.AD_LOAD)))
                .build();
    }
}
