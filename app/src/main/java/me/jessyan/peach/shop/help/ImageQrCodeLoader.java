package me.jessyan.peach.shop.help;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import me.jessyan.peach.shop.R;

/**
 * author: Created by HuiRan on 2018/5/18 10:40
 * E-Mail: 15260828327@163.com
 * description:
 */

public class ImageQrCodeLoader {

    private static final String TAG = "ImageQrCodeLoader";
    private static final int MESSAGE_POST_RESULT = 1;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = R.id.image_loader_uri;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageQrCodeLoader#" + mCount.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), sThreadFactory);

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            View view = result.view;
            String tag = (String) view.getTag(TAG_KEY_URI);
            if (tag.equals(result.resId)) {
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageBitmap(result.bitmap);
                } else {
                    view.setBackground(ImageUtils.bitmap2Drawable(result.bitmap));
                }
            }
        }
    };

    private LruCache<String, Bitmap> mMemoryCache;

    private ImageQrCodeLoader() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    private static volatile ImageQrCodeLoader defaultInstance;

    public static ImageQrCodeLoader getDefault() {
        if (defaultInstance == null) {
            synchronized (ImageQrCodeLoader.class) {
                if (defaultInstance == null) {
                    defaultInstance = new ImageQrCodeLoader();
                }
            }
        }
        return defaultInstance;
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void bindQRCodBitmap(final String uri, final View view, final int reqWidth, final int reqHeight) {
        view.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else {
                view.setBackground(ImageUtils.bitmap2Drawable(bitmap));
            }
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap loadBitmap = loadQRCodeBitmap(uri, reqWidth, reqHeight);
                if (loadBitmap != null) {
                    LoaderResult result = new LoaderResult(view, uri, loadBitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);


    }

    public Bitmap loadQRCodeBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = generateQRCode(uri, reqWidth, reqHeight);
        if(bitmap==null){
            return loadBitmapFromMemCache(uri);
        }

        return bitmap;
    }

    private Bitmap generateQRCode(String uri, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            LogUtils.w(TAG, "load bitmap from UI thread,it is not recommended");
        }
        Bitmap bitmap = null;
        try{
            bitmap = CodeUtils.createImage(uri, reqWidth, reqHeight, null);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromMemCache(String uri) {
        String key = EncryptUtils.encryptMD5ToString(uri);
        return getBitmapFromMemCache(key);
    }

    private static class LoaderResult {
        public View view;
        public String resId;
        public Bitmap bitmap;

        public LoaderResult(View view, String resId, Bitmap bitmap) {
            this.view = view;
            this.resId = resId;
            this.bitmap = bitmap;
        }
    }
}
