package musicplayer.equalizer.volumebooster.bassbooster.ads;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdRequest;

import java.util.Arrays;

import musicplayer.equalizer.volumebooster.bassbooster.helper.PropertiesHelper;

/**
 * Created by ThoiNguyen on 11/3/2017.
 */

public class TestHelper {
    private static final String TAG = TestHelper.class.getName();
    private static final String PROPERTIES_DEVICES_TEST = "devices.properties";
    private static final String ADMOB_KEY = "admob";
    private static final String FB_KEY = "fb";
    private static final String SEPARATOR = ";";

    public static AdRequest getAdMobRequest(Context context) {

        String adMobDevices = PropertiesHelper.getProperty(context, PROPERTIES_DEVICES_TEST, ADMOB_KEY);
        String[] devices = adMobDevices.split(SEPARATOR);
        AdRequest.Builder builder = new AdRequest.Builder();
        for (String device : devices) {
            Log.d(TAG, "getAdMobRequest: device=" + device);
            builder.addTestDevice(device);
        }
        return builder.build();
    }

    public static void loadFacebookDevicesTest(Context context) {
        String fbDevices = PropertiesHelper.getProperty(context, PROPERTIES_DEVICES_TEST, FB_KEY);
        String[] devices = fbDevices.split(SEPARATOR);
        AdSettings.addTestDevices(Arrays.asList(devices));
    }
}
