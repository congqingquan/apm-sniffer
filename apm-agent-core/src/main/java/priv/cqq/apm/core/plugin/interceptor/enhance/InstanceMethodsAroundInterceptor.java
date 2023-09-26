package priv.cqq.apm.core.plugin.interceptor.enhance;

import java.lang.reflect.Method;

public interface InstanceMethodsAroundInterceptor {

    void beforeMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable;

    Object afterMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable;

    void handleMethodException(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable throwable) throws Throwable;
}
