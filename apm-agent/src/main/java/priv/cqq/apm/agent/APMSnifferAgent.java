package priv.cqq.apm.agent;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;
import priv.cqq.apm.core.APMBootstrap;
import priv.cqq.apm.core.resolver.PluginFinder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

@Slf4j
public class APMSnifferAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        APMBootstrap.boot();
        new AgentBuilder.Default()
                .ignore(
                        nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic())
                )
                // rebase
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(PluginFinder.buildPluginTypeJunction())
                .transform(new APMTransform())
                .installOn(instrumentation);
    }
}