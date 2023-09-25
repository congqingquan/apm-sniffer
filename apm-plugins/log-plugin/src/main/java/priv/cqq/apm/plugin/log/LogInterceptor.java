package priv.cqq.apm.plugin.log;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class LogInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable {
        log.info("Log interceptor: before method. method name [{}], allArguments [{}]", method.getName(), Arrays.toString(allArguments));
    }

    @Override
    public Object afterMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        log.info("Log interceptor: after method. method name [{}], ret [{}]", method.getName(), ret);
        return ret;
    }

    @Override
    public void handleMethodException(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {
        log.info("Log interceptor: handel method exception. method name [{}]", method.getName(), t);
    }
}