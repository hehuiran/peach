package me.jessyan.peach.shop.vlayout;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.vlayout.callback.OnItemChildClickListener;
import me.jessyan.peach.shop.vlayout.callback.OnItemChildLongClickListener;
import me.jessyan.peach.shop.vlayout.callback.OnItemClickListener;
import me.jessyan.peach.shop.vlayout.callback.OnItemLongClickListener;

/**
 * author: Created by HuiRan on 2018/2/9 14:23
 * E-Mail: 15260828327@163.com
 * description:
 */

public abstract class VirtualItemAdapter<K extends VirtualItemViewHolder> extends DelegateAdapter.Adapter<K> {

    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    protected final String TAG = this.getClass().getSimpleName();

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    public OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }

    public VirtualItemAdapter(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        K holder = onCreateDefViewHolder(parent, viewType);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public int getItemCount() {
        return getDefItemCount();
    }

    public abstract int getDefItemCount();

    @Override
    public int getItemViewType(int position) {
        return getDefItemViewType(position);
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(K holder, int position) {

    }

    @Override
    protected void onBindViewHolderWithOffset(K holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        bindViewClickListener(holder, position);
        convert(holder, position, offsetTotal);
    }

    protected abstract void convert(K holder, int position, int absolutePosition);

    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createVirtualViewHolder(parent, mLayoutResId);
    }

    protected K createVirtualViewHolder(ViewGroup parent, int layoutResId) {
        return createVirtualViewHolder(getItemView(layoutResId, parent));
    }


    /**
     * if you want to use subclass of ViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    @SuppressWarnings("unchecked")
    protected K createVirtualViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (K) new VirtualItemViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (K) new VirtualItemViewHolder(view);
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void bindViewClickListener(final VirtualItemViewHolder viewHolder, final int position) {
        if (viewHolder == null) {
            return;
        }
        final View view = viewHolder.itemView;
        if (view == null) {
            return;
        }
        if (mOnItemClickListener != null) {
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View v) {
                    mOnItemClickListener.onItemClick(VirtualItemAdapter.this, v, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onItemLongClick(VirtualItemAdapter.this, v, position);
                }
            });
        }
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an itemchild in View has
     * been  clicked
     *
     * @param listener The callback that will run
     */
    public VirtualItemAdapter setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
        return this;
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been long clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an itemchild  in this View has
     * been long clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        mOnItemChildLongClickListener = listener;
    }
}
