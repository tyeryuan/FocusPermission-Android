package com.focus.focuspermission.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import com.focus.focuspermission.R;

public class Utils {
    public static boolean isEmulator() {
        //获取手机的Serial码
        printDeviceInfo();
        String serial = Build.SERIAL;
        if (serial.equalsIgnoreCase("unknown") || serial.equalsIgnoreCase("android")) {
            return true;
        }
        return false;
    }

    private static void printDeviceInfo(){
        StringBuilder sb = new StringBuilder();

        //获取手机的Serial码
        String serial = Build.SERIAL;
        sb.append("Serial码：").append(serial).append("\n");
        sb.append("brand:").append(Build.BOARD);

        Log.i("deviceInfo",sb.toString());
    }

    public static void changeShapeRoundButtonColor(Button button, String colorString){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable rippleDrawable = (RippleDrawable) button.getBackground();
            GradientDrawable gradientDrawable = (GradientDrawable) rippleDrawable.getDrawable(1);
            gradientDrawable.setColor(Color.parseColor(colorString));
        }

    }

}
