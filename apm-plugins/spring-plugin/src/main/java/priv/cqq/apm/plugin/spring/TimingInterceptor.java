package priv.cqq.apm.plugin.spring;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;

import java.lang.reflect.Method;

@Slf4j
public class TimingInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable {

    }

    @Override
    public Object afterMethod(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        log.info("after method. method name [{}], ret [{}]", method.getName(), ret);
        return ret;
    }

    @Override
    public void handleMethodException(Object target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {
        log.info("Timing interceptor: handel method exception. method name [{}]", method.getName(), t);
    }
}