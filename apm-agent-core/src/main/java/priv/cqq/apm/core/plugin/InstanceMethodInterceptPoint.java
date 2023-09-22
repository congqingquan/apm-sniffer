package priv.cqq.apm.core.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public abstract class InstanceMethodInterceptPoint {

    public abstract ElementMatcher<? super MethodDescription> instanceMethodMatcher();

    public abstract String interceptorClassName();
}