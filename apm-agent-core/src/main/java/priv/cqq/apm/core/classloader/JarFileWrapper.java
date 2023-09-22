package priv.cqq.apm.core.classloader;

import java.io.File;
import java.util.jar.JarFile;

public class JarFileWrapper {

    private final JarFile jarFile;

    private final File sourceFile;

    public JarFileWrapper(JarFile jarFile, File sourceFile) {
        this.jarFile = jarFile;
        this.sourceFile = sourceFile;
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public File getSourceFile() {
        return sourceFile;
    }
}