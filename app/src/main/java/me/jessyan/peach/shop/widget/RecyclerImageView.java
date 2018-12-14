package me.jessyan.peach.shop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by hurry on 2017/12/28.
 * 图片不显示时移除资源
 */

public class RecyclerImageView extends AppCompatImageView {
    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
        setImageResource(0);
        setImageBitmap(null);

    }

}
