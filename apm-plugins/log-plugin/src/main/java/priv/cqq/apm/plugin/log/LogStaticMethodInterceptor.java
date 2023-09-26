package priv.cqq.apm.plugin.log;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.StaticMethodsAroundInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class LogStaticMethodInterceptor implements StaticMethodsAroundInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] argumentsTypes) {
        log.info("Log static method interceptor: before method. class [{}] method name [{}], allArguments [{}]",
                clazz, method.getName(), Arrays.toString(allArguments));
    }

    @Override
    public Object afterMethod(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) {
        log.info("Log static method interceptor: after method. method name [{}], ret [{}]", method.getName(), ret);
        return ret;
    }

    @Override
    public void handleMethodException(Class<?> clazz, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable throwable) {
        log.info("Log static method interceptor: handel method exception. method name [{}]", method.getName(), throwable);
    }
}