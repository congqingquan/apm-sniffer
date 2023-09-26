package priv.cqq.apm.core;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class APMConstants {

    // absolute path of jar file which include APMConstants class file
    public static final String CURRENT_PATH;

    // plugin folder absolute path
    public static final String PLUGIN_FOLDER_ABSOLUTE_PATH;

    static {
        CURRENT_PATH = MethodHandles.lookup().lookupClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        File parentFile = new File(CURRENT_PATH).getParentFile();
        File pluginFolder = new File(parentFile + File.separator + "plugins");
        if (!pluginFolder.exists()) {
            throw new RuntimeException("Cannot to initialize plugin folder absolute path. Check the path: " + pluginFolder);
        }
        PLUGIN_FOLDER_ABSOLUTE_PATH = pluginFolder.getAbsolutePath();

        // For test
        // PLUGIN_FOLDER_ABSOLUTE_PATH = "D:\\development\\idea\\workspace\\personal\\apm-sniffer\\apm-agent\\target\\apm-dist\\plugins";
        // PLUGIN_FOLDER_ABSOLUTE_PATH = "/Users/congqingquan/development/code/gitee/java/apm-sniffer/apm-agent/target/apm-dist/plugins";
    }

    // config file name of apm plugin
    public static final String APM_PLUGIN_DEF_FILENAME = "apm.plugin.def";

    // default context attribute name
    public static final String DEFAULT_CONTEXT_ATTR_NAME = "_$EnhancedClassField_ContextArr";
}