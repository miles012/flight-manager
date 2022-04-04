package pl.miles.flightmanager.configuration;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class ResourceFilesUtil {

    private ResourceFilesUtil() {
    }

    public static String readFile(String name) {
        InputStream is = ResourceFilesUtil.class.getClassLoader().getResourceAsStream(name);
        if (is == null) {
            throw new RuntimeException("There is no such file in resources: " + name);
        }

        try {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load demo data", e);
        }
    }
}
