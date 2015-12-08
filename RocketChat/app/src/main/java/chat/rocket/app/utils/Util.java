package chat.rocket.app.utils;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.SeekBar;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import io.fabric.sdk.android.services.network.HttpRequest;

/**
 * Created by julio on 27/11/15.
 */
public class Util {
    public static Gson GSON = new Gson();

    public static JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
        Iterator<String> it = source.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = source.get(key);
            if (!target.has(key)) {
                // new value for "key":
                target.put(key, value);
            } else {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepMerge(valueJson, target.getJSONObject(key));
                } else {
                    target.put(key, value);
                }
            }
        }
        return target;
    }

    public static JSONObject deepRemove(JSONObject source, JSONObject target) throws JSONException {
        Iterator<String> it = source.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = source.get(key);
            if (target.has(key)) {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepRemove(valueJson, target.getJSONObject(key));
                } else {
                    target.remove(key);
                }
            }
        }
        return target;
    }


    public static String decodeFile(File file) {
        String fileStr = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf))) {
                baos.write(buf, 0, n);
            }
            fileStr = HttpRequest.Base64.encodeBytes(baos.toByteArray());

        } catch (Exception e) {
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return fileStr;
    }

    public static void setTint(SeekBar seekBar, int color) {
        ColorStateList s1 = ColorStateList.valueOf(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            Drawable progressDrawable = DrawableCompat.wrap(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(progressDrawable);
            DrawableCompat.setTintList(progressDrawable, s1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Drawable thumbDrawable = DrawableCompat.wrap(seekBar.getThumb());
                DrawableCompat.setTintList(thumbDrawable, s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null)
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (seekBar.getProgressDrawable() != null)
                seekBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

}
