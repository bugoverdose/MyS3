package bugoverdose.mys3.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringFormatUtils {

    public static String toCombinedPath(String... uris) {
        return Arrays.stream(uris)
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.joining("/"));
    }
}
