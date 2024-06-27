package priv.cqq.apm.core.loader;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.apm.core.APMConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class InterceptorLoader {
    
    private static final ConcurrentHashMap<String, Object> INTERCEPTOR_CACHE = new ConcurrentHashMap<String, Object>();
    
    // key: enhanced type class loader / value: interceptor apm class loader
    private static final Map<ClassLoader, ClassLoader> ENHANCED_TYPE_WITH_INTERCEPTOR_CLASSLOADER_MAP = new HashMap<>();
    
    @SuppressWarnings({"unchecked"})
    public static <T> T load(String interceptorClassName, ClassLoader enhancedTypeCLassLoader) {
        String interceptorKey =
                interceptorClassName + "_OF_" + enhancedTypeCLassLoader.getClass().getName() + "@" + Integer.toHexString(enhancedTypeCLassLoader.hashCode());
        Object interceptor = INTERCEPTOR_CACHE.get(interceptorKey);
        if (interceptor != null) {
            return (T) interceptor;
        }

        // 将 APMClassLoader 继承于增强类的类加载器，使得插件类可以访问到增强类
        // 对于多个相同类加载器的增强类，只会创建一个对应的 APMClassLoader
        ClassLoader interceptorAPMClassLoader;
        synchronized (InterceptorLoader.class) {
            interceptorAPMClassLoader = ENHANCED_TYPE_WITH_INTERCEPTOR_CLASSLOADER_MAP.get(enhancedTypeCLassLoader);
            if (interceptorAPMClassLoader == null) {
                interceptorAPMClassLoader = new APMClassLoader(enhancedTypeCLassLoader, APMConstants.PLUGIN_FOLDER_ABSOLUTE_PATH);
                ENHANCED_TYPE_WITH_INTERCEPTOR_CLASSLOADER_MAP.put(enhancedTypeCLassLoader, interceptorAPMClassLoader);
            }
        }

        Object interceptorInstance;
        try {
            interceptorInstance = Class.forName(interceptorClassName, true, interceptorAPMClassLoader).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error("Cannot load interceptor class for new instance. Interceptor class: {}", interceptorClassName, e);
            throw new RuntimeException("Cannot load interceptor class for new instance. Interceptor class: " + interceptorClassName, e);
        }
        INTERCEPTOR_CACHE.put(interceptorKey, interceptorInstance);
        return (T) interceptorInstance;
    }
}
