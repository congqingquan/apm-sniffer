package priv.cqq.apm.agent;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import priv.cqq.apm.core.APMBootstrap;
import priv.cqq.apm.core.resolver.PluginFinder;

import java.lang.instrument.Instrumentation;

@Slf4j
public class APMSnifferAgent {

    // D:\development\idea\workspace\personal\apm-sinffer\apm-agent\target\plugins-dist\plugins
    // D:\development\idea\workspace\personal\apm-sinffer\apm-plugins\log-plugin\target

    public static void premain(String args, Instrumentation instrumentation) {
        APMBootstrap.boot();
        new AgentBuilder.Default()
                .type(PluginFinder.buildPluginTypeJunction())
                .transform(new APMTransform())
                .installOn(instrumentation);
    }
}