package priv.cqq.apm.core.plugin.interceptor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public abstract class InstanceMethodInterceptPoint extends InterceptPoint {

    public abstract ElementMatcher<? super MethodDescription> instanceMethodMatcher();

}