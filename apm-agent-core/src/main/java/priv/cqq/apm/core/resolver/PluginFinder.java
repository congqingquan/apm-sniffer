package priv.cqq.apm.core.resolver;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import priv.cqq.apm.core.APMConstants;
import priv.cqq.apm.core.loader.APMClassLoader;
import priv.cqq.apm.core.plugin.ClassEnhancePluginDefinition;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PluginFinder {

    private static final List<ClassEnhancePluginDefinition> classEnhancePluginDefinitions = new ArrayList<>();

    public static List<ClassEnhancePluginDefinition> getClassEnhancePluginDefinitions() {
        return classEnhancePluginDefinitions;
    }

    public static List<ClassEnhancePluginDefinition> loadPlugins() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (!(contextClassLoader instanceof APMClassLoader)) {
            throw new RuntimeException("Cannot get the APMClassLoader from current thread context");
        }
        return loadPlugins((APMClassLoader) contextClassLoader);
    }

    public static List<ClassEnhancePluginDefinition> loadPlugins(APMClassLoader apmClassLoader) {
        List<ClassEnhancePluginDefinition> classEnhancePluginDefinitions = new ArrayList<>();
        Enumeration<URL> pluginFiles = apmClassLoader.getResources(APMConstants.APM_PLUGIN_DEF_FILENAME);
        while (pluginFiles.hasMoreElements()) {
            URL pluginFile = pluginFiles.nextElement();
            try {
                PluginConfig.INSTANCE.load(pluginFile.openStream());
            } catch (IOException e) {
                throw new RuntimeException("Cannot load plugin. URL: " + pluginFile);
            }
        }
        List<PluginDefinition> configs = PluginConfig.INSTANCE.getConfigs();
        for (PluginDefinition config : configs) {
            Object instance;
            try {
                Class<?> clazz = Class.forName(config.getClassName(), true, apmClassLoader);
                instance = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (!(instance instanceof ClassEnhancePluginDefinition)) {
                throw new IllegalArgumentException(config.getClassName() + " must be ClassEnhancePluginDefinition type");
            }
            classEnhancePluginDefinitions.add((ClassEnhancePluginDefinition) instance);
        }
        PluginFinder.classEnhancePluginDefinitions.addAll(classEnhancePluginDefinitions);
        return classEnhancePluginDefinitions;
    }

    public static ElementMatcher.Junction<? super TypeDescription> buildPluginTypeJunction() {
        return classEnhancePluginDefinitions.stream().map(ClassEnhancePluginDefinition::classMatcher).reduce(ElementMatcher.Junction::or).orElse(null);
    }
}