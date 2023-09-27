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
    
    public static <T> T load(String interceptorClassName, ClassLoader enhancedTypeCLassLoader) {
        String interceptorKey =
                interceptorClassName + "_OF_" + enhancedTypeCLassLoader.getClass().getName() + "@" + Integer.toHexString(enhancedTypeCLassLoader.hashCode());
        Object interceptor = INTERCEPTOR_CACHE.get(interceptorKey);
        if (interceptor != null) {
            return (T) interceptor;
        }

        ClassLoader interceptorAPMClassLoader;
        synchronized (InterceptorLoader.class) {
            interceptorAPMClassLoader = ENHANCED_TYPE_WITH_INTERCEPTOR_CLASSLOADER_MAP.get(enhancedTypeCLassLoader);
            if (interceptorAPMClassLoader == null) {
                // Why create a new APMClassLoader?
                // Ensure that the classes enhanced are loader by the same class loader as the interceptors in the plugin jar loaded by the APMClassLoader
                
                // Special that create APMClassLoader just once for every different class loader of enhanced type
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
