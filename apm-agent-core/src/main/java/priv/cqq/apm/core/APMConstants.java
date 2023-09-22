package priv.cqq.apm.core;

import java.lang.invoke.MethodHandles;

public class APMConstants {

    // absolute path of jar file which include APMConstants class file
    public static final String CURRENT_PATH;

    // plugin folder absolute path
    public static final String PLUGIN_FOLDER_ABSOLUTE_PATH;

    static {
        CURRENT_PATH = MethodHandles.lookup().lookupClass().getProtectionDomain().getCodeSource().getLocation().getPath();

//        File parentFile = new File(CURRENT_PATH).getParentFile();
//        File pluginFolder = new File(parentFile + File.separator + "plugins");
//        if (!pluginFolder.exists()) {
//            throw new RuntimeException("Cannot to initialize plugin folder absolute path. Check the path: " + pluginFolder);
//        }
//        PLUGIN_FOLDER_ABSOLUTE_PATH = pluginFolder.getAbsolutePath();

        // TODO Fot test
         PLUGIN_FOLDER_ABSOLUTE_PATH = "D:\\development\\idea\\workspace\\personal\\apm-sinffer\\apm-agent\\target\\plugins-dist\\plugins";
    }

    // config file name of apm plugin
    public static final String APM_PLUGIN_DEF_FILENAME = "apm.plugin.def";
}