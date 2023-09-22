package priv.cqq.apm.core.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public abstract class ClassEnhancePluginDefinition {

    public abstract ElementMatcher.Junction<? super TypeDescription> classMatcher();

    public abstract InstanceMethodInterceptPoint[] instanceMethodInterceptPoints();
}