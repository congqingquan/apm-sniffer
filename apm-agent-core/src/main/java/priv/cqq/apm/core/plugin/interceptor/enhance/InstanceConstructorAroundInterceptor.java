package priv.cqq.apm.core.plugin.interceptor.enhance;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import priv.cqq.apm.core.loader.InterceptorLoader;

import java.lang.reflect.Constructor;

@Slf4j
public class InstanceConstructorAroundInterceptor {

    /**
         .intercept(
             SuperMethodCall.INSTANCE
             .andThen(
             MethodDelegation.withDefaultConfiguration().filter(named("afterConstruct")).to(ConstructorDelegateTarget.class)
             )
         )
     */

    private final InstanceConstructorInterceptor interceptor;

    public InstanceConstructorAroundInterceptor(String interceptorClassName, ClassLoader classLoader) {
        interceptor = InterceptorLoader.load(interceptorClassName, classLoader);
    }

    @RuntimeType
    public void intercept(@Origin Constructor<?> constructor, @This Object target, @AllArguments Object[] allArguments) {
        try {
            // EnhancedInstance targetObject = (EnhancedInstance) obj;
            interceptor.onConstruct(target, allArguments);
        } catch (Throwable throwable) {
            log.error("Class [{}] after constructor [{}] interceptor failure", target.getClass(), constructor.getName(), throwable);
        }

    }
}