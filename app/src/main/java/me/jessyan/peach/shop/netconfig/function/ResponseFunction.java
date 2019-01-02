package me.jessyan.peach.shop.netconfig.function;

import android.support.annotation.NonNull;

import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.BasicResponse;

/**
 * author: Create by HuiRan on 2018/12/9 下午2:19
 * email: 15260828327@163.com
 * description:
 */
public class ResponseFunction<T> implements Function<BasicResponse<T>, T> {

    private static final String TAG = "ResponseFunction";
    private Class<T> tClass;

    public ResponseFunction(@NonNull Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T apply(BasicResponse<T> tBasicResponse) throws Exception {
        T t = tBasicResponse.getData();
        if (t == null) {
            t = tClass.newInstance();
        }
        return t;
    }
    /**
     * 根据泛型实例化对象
     */
    /*@SuppressWarnings({"unchecked", "SameParameterValue"})
    private T getInstance() {
        Class<? extends ResponseFunction> aClass = getClass();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType){
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            try {
                return ((Class<T>) arguments[0]).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        try {
            return ((Class<T>) ((ParameterizedType) (getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0])
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
            Timber.tag(TAG).e("generate response error from reflection....");
        }
        return null;
    }*/
}
