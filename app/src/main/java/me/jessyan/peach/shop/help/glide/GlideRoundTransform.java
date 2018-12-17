package me.jessyan.peach.shop.help.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * author: Create by HuiRan on 2018/12/16 下午5:51
 * email: 15260828327@163.com
 * description:
 */
public class GlideRoundTransform extends BitmapTransformation {
    private static final String ID = "me.jessyan.peach.shop.help.glide.GlideRoundTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int roundingRadius;

    public GlideRoundTransform() {
        this(SizeUtils.dp2px(5));
    }

    public GlideRoundTransform(int roundingRadius) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");
        this.roundingRadius = roundingRadius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform,
                               int outWidth, int outHeight) {
        //变换的时候裁切
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GlideRoundTransform) {
            GlideRoundTransform other = (GlideRoundTransform) o;
            return roundingRadius == other.roundingRadius;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(),
                Util.hashCode(roundingRadius));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(roundingRadius).array();
        messageDigest.update(radiusData);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        result.setHasAlpha(true);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        int right = source.getWidth();
        int bottom = source.getHeight();

        canvas.drawRoundRect(new RectF(0, 0, right, bottom), roundingRadius, roundingRadius,
                paint);
        return result;
    }
}
