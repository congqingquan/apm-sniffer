package priv.cqq.apm.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import priv.cqq.apm.core.classloader.APMClassLoader;
import priv.cqq.apm.core.holder.APMClassLoaderHolder;
import priv.cqq.apm.core.plugin.ClassEnhancePluginDefinition;
import priv.cqq.apm.core.plugin.InstanceMethodInterceptPoint;
import priv.cqq.apm.core.resolver.PluginFinder;

import java.security.ProtectionDomain;

public class APMTransform implements AgentBuilder.Transformer {

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                            TypeDescription typeDescription,
                                            ClassLoader classLoader,
                                            JavaModule module,
                                            ProtectionDomain protectionDomain) {
        // register method matcher
        DynamicType.Builder<?> newBuilder = builder;
        for (ClassEnhancePluginDefinition classEnhancePluginDefinition : PluginFinder.getClassEnhancePluginDefinitions()) {
            InstanceMethodInterceptPoint[] instanceMethodInterceptPoints = classEnhancePluginDefinition.instanceMethodInterceptPoints();
            for (InstanceMethodInterceptPoint instanceMethodInterceptPoint : instanceMethodInterceptPoints) {
                ElementMatcher<? super MethodDescription> elementMatcher = instanceMethodInterceptPoint.instanceMethodMatcher();
                String interceptorClassName = instanceMethodInterceptPoint.interceptorClassName();
                // Ensure that the classes enhanced by byte buddy are loader by the same class loader as the interceptors in the plugin jar
                // loaded by the APMClassLoader
                // TODO 待解决：Enhanced class 被 APPClassLoader 加载。且增强后的字节码中依赖 APMClassLoader 加载的插件拦截器。那么 APPClassLoader 肯定找不到插件拦截器。
                try {
                    APMClassLoader apmClassLoader = APMClassLoaderHolder.get();
                    if (apmClassLoader == null) {
                        throw new RuntimeException("Cannot get APMClassLoader from holder");
                    }
                    Class<?> interceptorClass = Class.forName(interceptorClassName, true, apmClassLoader);
                    Object interceptorInstance = interceptorClass.newInstance();
                    newBuilder = newBuilder.method(elementMatcher).intercept(MethodDelegation.to(interceptorInstance));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Cannot to load the interceptor class: " + interceptorClassName);
                }
            }
        }
        return newBuilder;
    }
}