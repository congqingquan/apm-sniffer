package priv.cqq.apm.plugin.log;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.concurrent.Callable;

@Slf4j
public class LogInterceptor {

    @RuntimeType
    public Object log(
            @Origin Class<?> targetClass,
            @Origin String sourceMethodInstanceToString,
            @This Object target,
            @AllArguments Object[] allArgs,
            @SuperCall Callable<Object> callable) throws Exception {
        log.info("apm log: before call method. target: {}, class loader: {}", target, target.getClass().getClassLoader());
        try {
            return callable.call();
        } finally {
            log.info("apm log: after call method");
        }
    }
}