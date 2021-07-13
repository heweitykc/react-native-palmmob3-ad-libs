package com.palmmob3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.palmmob3.gdt.view.SplashView;

import java.util.Map;

public class SplashViewManager extends ViewGroupManager<SplashView> {

    public static final String TAG = "GDTSplashView";
    private ReactContext mContext;

    public SplashViewManager(ReactApplicationContext context) {
        mContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    @NonNull
    @Override
    protected SplashView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new SplashView(reactContext);
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return false;
    }

    @ReactProp(name = "PosId")
    public void setPosId(SplashView view, @Nullable String posid) {
        view.setPosId(posid);
    }

    @Override
    protected void onAfterUpdateTransaction(SplashView view) {
        super.onAfterUpdateTransaction(view);
        view.showAd();
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return Utils.getEventType();
    }

}
