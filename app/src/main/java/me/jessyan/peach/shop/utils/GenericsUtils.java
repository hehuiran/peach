package me.jessyan.peach.shop.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author: Create by HuiRan on 2018/12/30 下午8:26
 * email: 15260828327@163.com
 * description:
 */
public final class GenericsUtils {

    private GenericsUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenericManager<Book>
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenericType(Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    /**通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenericType(Class clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

}
