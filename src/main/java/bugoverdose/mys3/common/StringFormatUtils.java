package bugoverdose.mys3.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringFormatUtils {

    private StringFormatUtils() {
    }

    public static String toCombinedPath(String... uris) {
        return Arrays.stream(uris)
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.joining("/"));
    }

    public static String removeFileExtension(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return fileName;
    }
}
