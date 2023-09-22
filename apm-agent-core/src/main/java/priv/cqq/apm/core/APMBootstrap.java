package priv.cqq.apm.core;

import priv.cqq.apm.core.classloader.APMClassLoader;
import priv.cqq.apm.core.holder.APMClassLoaderHolder;
import priv.cqq.apm.core.resolver.PluginFinder;

import java.lang.invoke.MethodHandles;

public class APMBootstrap {

    public static void boot() {
        // 1. create apm class loader
        APMClassLoader apmClassLoader = new APMClassLoader(MethodHandles.lookup().lookupClass().getClassLoader(), APMConstants.PLUGIN_FOLDER_ABSOLUTE_PATH);
        APMClassLoaderHolder.set(apmClassLoader);
        // 2. load all plugins
        PluginFinder.loadPlugins(apmClassLoader);
        APMClassLoaderHolder.clear();
    }
}