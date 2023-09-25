package priv.cqq.apm.core.plugin.interceptor.enhance;

import java.lang.reflect.Method;

public interface InstanceMethodsAroundInterceptor {

    void beforeMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable;

    Object afterMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable;

    void handleMethodException(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t);
}
