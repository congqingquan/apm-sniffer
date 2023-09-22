package priv.cqq.apm.core.resolver;

public class PluginDefinition {

    private final String name;

    private final String className;

    public PluginDefinition(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public static PluginDefinition create(String definitionLine) {
        String[] definePart = definitionLine.split("=");
        if (definePart.length != 2) {
            throw new RuntimeException("Invalid plugin definition line: " + definitionLine);
        }
        return new PluginDefinition(definePart[0], definePart[1]);
    }
}