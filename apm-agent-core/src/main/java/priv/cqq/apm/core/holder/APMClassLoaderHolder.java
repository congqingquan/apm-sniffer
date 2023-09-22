package priv.cqq.apm.core.holder;

import priv.cqq.apm.core.classloader.APMClassLoader;

public class APMClassLoaderHolder {

    private static final ThreadLocal<APMClassLoader> holder = new ThreadLocal<>();

    public static void set(APMClassLoader apmClassLoader) {
        holder.set(apmClassLoader);
    }

    public static APMClassLoader get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}