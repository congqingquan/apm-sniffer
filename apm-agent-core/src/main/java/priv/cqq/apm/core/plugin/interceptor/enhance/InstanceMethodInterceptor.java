package priv.cqq.apm.core.plugin.interceptor.enhance;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import priv.cqq.apm.core.loader.InterceptorLoader;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class InstanceMethodInterceptor {

    private final InstanceMethodsAroundInterceptor interceptor;

    public InstanceMethodInterceptor(String interceptorClassName, ClassLoader classLoader) {
        interceptor = InterceptorLoader.load(interceptorClassName, classLoader);
    }

    @RuntimeType
    public Object intercept(
            @This EnhancedInstance target,
            @Origin Method method,
            @AllArguments Object[] allArguments,
            @SuperCall Callable<Object> callable) throws Exception {

        try {
            // before
            interceptor.beforeMethod(target, method, allArguments, method.getParameterTypes());
        } catch (Throwable throwable) {
            log.error("Class [{}] before method [{}] interceptor failure", target.getClass(), method.getName(), throwable);
        }

        Object ret = null;
        try {
            ret = callable.call();
        } catch (Throwable throwable) {
            try {
                // call failure
                interceptor.handleMethodException(target, method, allArguments, method.getParameterTypes(), throwable);
            } catch (Throwable t) {
                log.error("Class [{}] handle method [{}] exception failure", target.getClass(), method.getName(), t);
            }
            throw throwable;
        } finally {
            try {
                // after
                ret = interceptor.afterMethod(target, method, allArguments, method.getParameterTypes(), ret);
            } catch (Throwable throwable) {
                log.error("Class [{}] after method [{}] interceptor failure", target.getClass(), method.getName(), throwable);
            }
        }
        return ret;
    }
}