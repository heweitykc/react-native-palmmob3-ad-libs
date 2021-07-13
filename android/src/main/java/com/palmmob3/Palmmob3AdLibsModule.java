// Palmmob3AdLibsModule.java

package com.palmmob3;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
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
}