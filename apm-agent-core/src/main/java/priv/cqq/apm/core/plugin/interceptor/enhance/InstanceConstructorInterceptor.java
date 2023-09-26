package priv.cqq.apm.core.plugin.interceptor.enhance;

public interface InstanceConstructorInterceptor {

    /**
     * Called after the origin constructor invocation.
     */
    void onConstruct(EnhancedInstance target, Object[] allArguments) throws Throwable;
}