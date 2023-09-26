package priv.cqq.apm.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Slf4j
public class YamlUtils {

    // Config map: loadInClasspath("config.yml")
    public static Map<String, Object> loadInClasspath(String filename) {
        try {
            return new Yaml().load(ClassLoader.getSystemClassLoader().getResourceAsStream(filename));
        } catch (Exception e) {
            log.error("Cannot find the file [{}]", filename);
            return null;
        }
    }
}