package com.palmmob3;

import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.palmmob3.pangle.view.BannerView;

import java.util.Map;

public class BannerViewManager extends ViewGroupManager<BannerView> {

    public static final String TAG = "PangleBannerView";
    private ReactContext mContext;
    private int propWidth;
    private int propHeight;

    public BannerViewManager(ReactApplicationContext context) {
        mContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    @NonNull
    @Override
    protected BannerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new BannerView(reactContext);
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return false;
    }

    @ReactProp(name = "PosId")
    public void setPosId(BannerView view, @Nullable String posid) {
        view.setPosId(posid);
    }

    @Override
    protected void onAfterUpdateTransaction(BannerView view) {
        super.onAfterUpdateTransaction(view);
        view.showAd(propWidth, propHeight);
    }

    @ReactPropGroup(names = {"width", "height"}, customType = "Style")
    public void setStyle(BannerView view, int index, Integer value) {
        if (index == 0) {
            propWidth = value;
        }

        if (index == 1) {
            propHeight = value;
        }
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return Utils.getEventType();
    }

}
