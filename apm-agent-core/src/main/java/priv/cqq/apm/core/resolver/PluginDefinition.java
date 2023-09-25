package priv.cqq.apm.core.resolver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginDefinition {

    private final String name;

    private final String className;

    public static PluginDefinition create(String definitionLine) {
        String[] definePart = definitionLine.split("=");
        if (definePart.length != 2) {
            throw new RuntimeException("Invalid plugin definition line: " + definitionLine);
        }
        return new PluginDefinition(definePart[0], definePart[1]);
    }
}