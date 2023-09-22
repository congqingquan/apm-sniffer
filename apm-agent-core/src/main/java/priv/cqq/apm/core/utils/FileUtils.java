package priv.cqq.apm.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.jar.JarFile;

public class FileUtils {

    public static List<JarFile> findJars(String path) {
        return find(path, file -> file.getName().endsWith(".jar"), file -> {
            try {
                return new JarFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Cannot be wrapped as a JarFile");
            }
        });
    }

    public static <R> List<R> find(String path, Predicate<File> matchPredicate, Function<File, R> fileConvert) {
        File pluginFolder = new File(path);
        if (!pluginFolder.exists()) {
            return new ArrayList<>();
        }
        File[] rootFilesArray = pluginFolder.listFiles();
        if (rootFilesArray == null || rootFilesArray.length < 1) {
            return new ArrayList<>();
        }

        List<File> rootFiles = new ArrayList<>(Arrays.asList(rootFilesArray));

        List<R> matchedFiles = new ArrayList<>();
        LinkedList<File> stack = new LinkedList<>();
        for (File rootFile : rootFiles) {
            stack.add(rootFile);
            while (!stack.isEmpty()) {
                File cur = stack.pop();
                if (cur.isDirectory()) {
                    File[] files = cur.listFiles();
                    if (files != null && files.length > 0) {
                        stack.addAll(Arrays.asList(files));
                    }
                }
                else if (matchPredicate.test(cur)) {
                    matchedFiles.add(fileConvert.apply(cur));
                }
            }
        }
        return matchedFiles;
    }
}