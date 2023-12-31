package priv.cqq.apm.core.resolver;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public enum PluginConfig {

    INSTANCE;

    @Getter
    private final List<PluginDefinition> configs = new ArrayList<>();

    public void load(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String definitionLine;
            while ((definitionLine = bufferedReader.readLine()) != null) {
                if (definitionLine.trim().length() == 0 || definitionLine.startsWith("#")) {
                    continue;
                }
                configs.add(PluginDefinition.create(definitionLine));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}