package me.jessyan.peach.shop.help;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.blankj.utilcode.util.Utils;

import java.util.List;

/**
 * author Created by He on 2017/8/15.
 * description 图片扫描,通知手机相册更新
 */

public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private ScannerListener mScannerListener;
    private MediaScannerConnection mMediaScanConn;
    private String[] filePaths;
    private int scanCount = 0;

    public MediaScanner() {
        this(null);
    }

    public MediaScanner(ScannerListener scannerListener) {
        this.mMediaScanConn = new MediaScannerConnection(Utils.getApp(), this);
        this.mScannerListener = scannerListener;
    }

    public boolean isRunning() {
        return mMediaScanConn.isConnected();
    }

    public void scan(String filePath) {
        scan(new String[]{filePath});
    }

    public void scan(List<String> filePaths) {
        scan(filePaths.toArray(new String[filePaths.size()]));
    }

    public void scan(String[] filePaths) {
        if (isRunning()) throw new RuntimeException("The scanner is running.");
        this.filePaths = filePaths;
        mMediaScanConn.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        if (filePaths != null && filePaths.length > 0) for (String filePath : filePaths) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            mMediaScanConn.scanFile(filePath, mimeType);
        }
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        if (mScannerListener != null) mScannerListener.oneComplete(path, uri);
        scanCount++;
        if (scanCount == filePaths.length) {
            mMediaScanConn.disconnect();
            scanCount = 0;
            if (mScannerListener != null) mScannerListener.allComplete(filePaths);
        }
    }

    public interface ScannerListener {

        void oneComplete(String path, Uri uri);

        void allComplete(String[] filePaths);

    }
}
