package priv.cqq.apm.plugin.spring;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import priv.cqq.apm.core.plugin.ClassEnhancePluginDefinition;
import priv.cqq.apm.core.plugin.InstanceMethodInterceptPoint;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.nameEndsWith;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class RestControllerInstrumentation extends ClassEnhancePluginDefinition {

    @Override
    public ElementMatcher.Junction<? super TypeDescription> classMatcher() {
        return isAnnotatedWith(named("org.springframework.web.bind.annotation.RestController"));
    }

    @Override
    public InstanceMethodInterceptPoint[] instanceMethodInterceptPoints() {
        return new InstanceMethodInterceptPoint[] {
                new InstanceMethodInterceptPoint() {
                    @Override
                    public ElementMatcher<? super MethodDescription> instanceMethodMatcher() {
                        return isAnnotatedWith(nameStartsWith("org.springframework.web.bind.annotation").and(nameEndsWith("Mapping")));
                    }

                    @Override
                    public String interceptorClassName() {
                        return "priv.cqq.apm.core.plugin.TimingInterceptor";
                    }
                }
        };
    }
}