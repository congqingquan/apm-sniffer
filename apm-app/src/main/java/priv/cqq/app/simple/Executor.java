package priv.cqq.app.simple;

import jdk.nashorn.internal.runtime.logging.Logger;

public class Executor {

    @Logger
    public Executor(String p1, String p2) {

    }

    public String instanceMethod(String... args) {
        return "Instance method return: success";
    }

    @Logger
    public String loggerInstanceMethod(String... args) {
        // int i = 1/ 0;
        return "Logger instance method return: success";
    }

    public static String staticMethod(String... args) {
        return "Static method return: success";
    }

    @Logger
    public static String loggerStaticMethod(String... args) {
        return "Logger static method return: success";
    }
}