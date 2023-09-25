package priv.cqq.app.simple;

import jdk.nashorn.internal.runtime.logging.Logger;

import java.util.Arrays;

public class Executor {

    @Logger
    public String execute(String... args) {
        System.out.println("execute -> args: " + Arrays.toString(args));
        // int i = 1/ 0;
        return "success";
    }

}