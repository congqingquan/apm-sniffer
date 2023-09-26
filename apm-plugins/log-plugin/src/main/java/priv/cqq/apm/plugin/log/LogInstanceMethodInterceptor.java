package priv.cqq.apm.plugin.log;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.EnhancedInstance;
import priv.cqq.apm.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class LogInstanceMethodInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) {
        log.info("Log instance method interceptor: before method. method name [{}], allArguments [{}]", method.getName(), Arrays.toString(allArguments));
    }

    @Override
    public Object afterMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) {
        log.info("Log instance method interceptor: after method. method name [{}], ret [{}]", method.getName(), ret);
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable throwable) {
        log.info("Log instance method interceptor: handel method exception. method name [{}]", method.getName(), throwable);
    }
}