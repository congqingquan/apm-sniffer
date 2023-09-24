package priv.cqq.apm.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import priv.cqq.apm.core.loader.InterceptorLoader;
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
                
                // TODO 待解决：Enhanced class 被 APPClassLoader 加载。且增强后的字节码中依赖 APMClassLoader 加载的插件拦截器。
                //  那么 APPClassLoader 肯定找不到插件拦截器。即如何结果 APPClasLoader 中访问到 APMClassLoader 中的类？
                
                // 需要在 agent-core 中抽象出一层拦截器，并定义拦截器接口，具体的实现在 plugin jar 中。
                // 1. 因为 agent-core 中的代码最终会被打包到最终的 agent.jar 中，
                //      所以拦截器接口可以被加载应用程序类的 AppClassLoader 加载，
                //          所以应用程序运行时是可以访问到 agent.jar 中的 class 的。但是无法在编写代码时静态引入，因为编译器并不知道 agent.jar 的存在
                // 2. 在抽象出的拦截器层中，内部进一步调用具体的在 plugin jar 中被 APMClassLoader 加载的拦截器，并用接口类型来接收。
                
                // 总结下来即：不去搜索无法被 APPClassLoader 找到的 plugin jar 中的类，利用一个可以被搜索到的接口来接收无法被 APPClassLoader 找到的类！
                // InstanceMethodsInterceptor(Loaded by AppClassLoader) interceptor = Instance of interceptorImpl(Loaded by APMClassLoader)
                // 在总结一下：如何在父类加载器加载的类中，引用子类加载器加载的类？理解多态抽象出一个接口或者抽象类来接收即可。
                
                String interceptorClassName = instanceMethodInterceptPoint.interceptorClassName();
                try {
                    // 注意点：
                    // 实例化 Interceptor 时使用的 ClassLoader 的 ParentClassLoader 应该为 Enhance class 的 ClassLoader。
                    // 这样才能使得拦截器中能正常访问被增强类，虽然 APMClassLoader 加载不到 Enhance class，但会委派加载 Enhance class 的 ClassLoader 加载 Enhance class。
                    Object interceptorInstance = InterceptorLoader.load(interceptorClassName, classLoader);
                    newBuilder = newBuilder.method(elementMatcher).intercept(MethodDelegation.to(interceptorInstance));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Cannot to load the interceptor class: " + interceptorClassName);
                }
            }
        }
        return newBuilder;
    }
}