package me.jessyan.peach.shop.utils;

import android.os.Environment;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.Random;

import me.jessyan.peach.shop.R;


/**
 * author: Created by HuiRan on 2017/12/12 15:57
 * E-Mail: 15260828327@163.com
 * description:
 */

public final class CameraUtils {

    private CameraUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getCameraFilePath() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //相机拍摄保存目录
            String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            int index = new Random().nextInt(1000);
            String nowDate = TimeUtils.getNowString();
            String outFilePath = "peach_" + nowDate + "_" + index + ".jpg";
            File file = new File(outFileFolder, outFilePath);
            return file.getAbsolutePath();
        } else {
            ToastUtils.showShort(R.string.there_is_no_sdcard);
        }
        return null;
    }
}
