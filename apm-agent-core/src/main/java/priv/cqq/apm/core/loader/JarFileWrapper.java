package priv.cqq.apm.core.loader;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.util.jar.JarFile;

@Data
@AllArgsConstructor
public class JarFileWrapper {

    private final JarFile jarFile;

    private final File sourceFile;
}