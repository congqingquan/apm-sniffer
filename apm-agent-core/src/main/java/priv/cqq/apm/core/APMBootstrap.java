package priv.cqq.apm.core;

import priv.cqq.apm.core.loader.APMClassLoader;
import priv.cqq.apm.core.resolver.PluginFinder;

import java.lang.invoke.MethodHandles;

public class APMBootstrap {

    public static void boot() {
        // 1. initialize apm class loader
        APMClassLoader.init(MethodHandles.lookup().lookupClass().getClassLoader(), APMConstants.PLUGIN_FOLDER_ABSOLUTE_PATH);
        // 2. load all plugins
        PluginFinder.loadPlugins(APMClassLoader.getInstance());
    }
}