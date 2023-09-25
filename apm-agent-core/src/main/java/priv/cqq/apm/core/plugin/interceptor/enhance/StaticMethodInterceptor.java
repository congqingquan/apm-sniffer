package priv.cqq.apm.core.plugin.interceptor.enhance;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import priv.cqq.apm.core.loader.InterceptorLoader;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class StaticMethodInterceptor {

    private final StaticMethodsAroundInterceptor interceptor;

    public StaticMethodInterceptor(String interceptorClassName, ClassLoader classLoader) {
        interceptor = InterceptorLoader.load(interceptorClassName, classLoader);
    }

    @RuntimeType
    public Object intercept(
            @Origin Class<?> clazz,
            @Origin Method method,
            @AllArguments Object[] allArguments,
            @SuperCall Callable<?> callable) throws Exception {

        try {
            // before
            interceptor.beforeMethod(clazz, method, allArguments, method.getParameterTypes());
        } catch (Throwable throwable) {
            log.error("Class [{}] before static method [{}] interceptor failure", clazz, method.getName(), throwable);
        }

        Object ret = null;
        try {
            ret = callable.call();
        } catch (Throwable throwable) {
            try {
                // call failure
                interceptor.handleMethodException(clazz, method, allArguments, method.getParameterTypes(), throwable);
            } catch (Throwable t) {
                log.error("Class [{}] handle static method [{}] exception failure", clazz, method.getName(), t);
            }
            throw throwable;
        } finally {
            try {
                // after
                ret = interceptor.afterMethod(clazz, method, allArguments, method.getParameterTypes(), ret);
            } catch (Throwable throwable) {
                log.error("Class [{}] after static method [{}] interceptor failure", clazz, method.getName(), throwable);
            }
        }
        return ret;
    }
}