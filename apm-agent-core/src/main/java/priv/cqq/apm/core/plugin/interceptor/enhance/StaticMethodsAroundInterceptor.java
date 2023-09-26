package priv.cqq.apm.core.plugin.interceptor.enhance;

import java.lang.reflect.Method;

public interface StaticMethodsAroundInterceptor {

    void beforeMethod(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes) throws Throwable;

    Object afterMethod(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object ret) throws Throwable;

    void handleMethodException(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes, Throwable throwable) throws Throwable;
}