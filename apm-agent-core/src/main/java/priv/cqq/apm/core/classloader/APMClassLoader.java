package priv.cqq.apm.core.classloader;

import priv.cqq.apm.core.APMConstants;
import priv.cqq.apm.core.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class APMClassLoader extends ClassLoader {

    private final List<String> classpath;

    private final List<JarFileWrapper> pluginJarFiles;

    public APMClassLoader() {
        this(MethodHandles.lookup().lookupClass().getClassLoader());
    }

    public APMClassLoader(ClassLoader parent) {
        this(parent, APMConstants.PLUGIN_FOLDER_ABSOLUTE_PATH);
    }

    public APMClassLoader(ClassLoader parent, String... classpath) {
        super(parent);
        this.classpath = Arrays.asList(classpath);
        pluginJarFiles = new ArrayList<>();
        for (String cp : this.classpath) {
            pluginJarFiles.addAll(FileUtils.find(cp, file -> file.getName().endsWith(".jar"), file -> {
                try {
                    return new JarFileWrapper(new JarFile(file), file);
                } catch (IOException e) {
                    throw new RuntimeException("Cannot be wrapped as a JarFile");
                }
            }));
        }
    }

    public List<String> getClasspath() {
        return classpath;
    }

    public List<JarFileWrapper> getPluginJarFiles() {
        return pluginJarFiles;
    }

    // ================================================================================

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String className = name.replaceAll("\\.", "/").concat(".class");
        for (JarFileWrapper pluginJarFile : pluginJarFiles) {
            JarFile jarFile = pluginJarFile.getJarFile();
            JarEntry jarEntry = jarFile.getJarEntry(className);
            if (jarEntry == null) {
                continue;
            }
            try (InputStream inputStream = jarFile.getInputStream(jarEntry);
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buf = new byte[1024];
                int read;
                while ((read = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, read);
                }
                byte[] classBytes = outputStream.toByteArray();
                return defineClass(name, classBytes, 0, classBytes.length);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to read byte data of loading class. Loading class name: " + name, exception);
            }
        }
        throw new ClassNotFoundException("Cannot find " + name);
    }

    @Override
    public Enumeration<URL> getResources(String name) {
        List<URL> foundFiles = new ArrayList<>();
        for (JarFileWrapper pluginJarFile : this.pluginJarFiles) {
            JarFile jarFile = pluginJarFile.getJarFile();
            if (jarFile.getEntry(name) != null) {
                try {
                    String finalURL = "jar:file:".concat(pluginJarFile.getSourceFile().getAbsolutePath()).concat("!/").concat(name);
                    foundFiles.add(new URL(finalURL));
                } catch (IOException exception) {
                    throw new RuntimeException("Fail to get resource. Resource name: " + name, exception);
                }
            }
        }
        Iterator<URL> iterator = foundFiles.iterator();
        return new Enumeration<URL>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public URL nextElement() {
                return iterator.next();
            }
        };
    }
}