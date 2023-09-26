package priv.cqq.apm.core.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import priv.cqq.apm.core.plugin.interceptor.InstanceConstructorInterceptPoint;
import priv.cqq.apm.core.plugin.interceptor.InstanceMethodInterceptPoint;
import priv.cqq.apm.core.plugin.interceptor.StaticMethodInterceptPoint;

public abstract class ClassEnhancePluginDefinition {

    public ElementMatcher.Junction<? super TypeDescription> classMatcher() {
        return ElementMatchers.none();
    }

    public InstanceConstructorInterceptPoint[] instanceConstructorInterceptPoints() {
        return new InstanceConstructorInterceptPoint[] {};
    }

    public InstanceMethodInterceptPoint[] instanceMethodInterceptPoints() {
        return new InstanceMethodInterceptPoint[] {};
    }

    public StaticMethodInterceptPoint[] staticMethodInterceptPoints() {
        return new StaticMethodInterceptPoint[] {};
    }
}