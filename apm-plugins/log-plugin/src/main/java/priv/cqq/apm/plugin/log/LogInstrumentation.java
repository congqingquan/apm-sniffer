package priv.cqq.apm.plugin.log;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import priv.cqq.apm.core.plugin.ClassEnhancePluginDefinition;
import priv.cqq.apm.core.plugin.interceptor.InstanceMethodInterceptPoint;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class LogInstrumentation extends ClassEnhancePluginDefinition {

    @Override
    public ElementMatcher.Junction<? super TypeDescription> classMatcher() {
        return any();
    }

    @Override
    public InstanceMethodInterceptPoint[] instanceMethodInterceptPoints() {
        return new InstanceMethodInterceptPoint[] {
                new InstanceMethodInterceptPoint() {
                    @Override
                    public ElementMatcher<? super MethodDescription> instanceMethodMatcher() {
                        return isAnnotatedWith(named("jdk.nashorn.internal.runtime.logging.Logger"));
                    }

                    @Override
                    public String interceptorClassName() {
                        return "priv.cqq.apm.plugin.log.LogInterceptor";
                    }
                }
        };
    }
}