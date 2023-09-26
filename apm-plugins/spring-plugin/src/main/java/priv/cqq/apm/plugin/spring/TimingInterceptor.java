package priv.cqq.apm.plugin.spring;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.EnhancedInstance;
import priv.cqq.apm.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;

import java.lang.reflect.Method;

@Slf4j
public class TimingInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes) {
        target.setContextAttr(System.currentTimeMillis());
        log.info("TimingInterceptor: before method. method name [{}], allArguments [{}]", method.getName(), allArguments);
    }

    @Override
    public Object afterMethod(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        Long startTime = (Long) target.getContextAttr();
        log.info("TimingInterceptor: after method. method name [{}], ret [{}], cost time [{}] ms",
                method.getName(), ret, System.currentTimeMillis() - startTime);
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance target, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable throwable) {
        log.info("Timing interceptor: handel method exception. method name [{}]", method.getName(), throwable);
    }
}