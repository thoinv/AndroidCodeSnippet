package musicplayer.equalizer.volumebooster.bassbooster.helper;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ThoiNguyen on 11/8/2017.
 */

public class PropertiesHelper {
    public static String getProperty(Context context, String fileName, String key) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }
}
