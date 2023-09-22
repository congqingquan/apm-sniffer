package priv.cqq.apm.plugin.spring;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Slf4j
public class TimingInterceptor {

    @RuntimeType
    public Object timing(
            @Origin Class<?> targetClass,
            @Origin String sourceMethodInstanceToString,
            @This Object target,
            @AllArguments Object[] allArgs,
            @SuperCall Callable<Object> controller) throws Exception {

        long start = System.currentTimeMillis();
        try {
            return controller.call();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Timing {} millis of {}-{}", end - start, sourceMethodInstanceToString, Arrays.toString(allArgs));
        }
    }
}