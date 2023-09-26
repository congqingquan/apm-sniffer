package priv.cqq.apm.plugin.log;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.plugin.interceptor.enhance.EnhancedInstance;
import priv.cqq.apm.core.plugin.interceptor.enhance.InstanceConstructorInterceptor;

import java.util.Arrays;

@Slf4j
public class LogInstanceConstructorInterceptor implements InstanceConstructorInterceptor {

    @Override
    public void onConstruct(EnhancedInstance target, Object[] allArguments) throws Throwable {
        log.info("Log instance constructor interceptor: onConstruct method. target [{}], allArguments [{}]", target, Arrays.toString(allArguments));
    }
}