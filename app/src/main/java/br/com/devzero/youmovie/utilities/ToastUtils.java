package br.com.devzero.youmovie.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class to handle toast messages
 */
public final class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, int messageResourceId, int length) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, messageResourceId, length);
        mToast.show();
    }
}
