package roomescape.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    public static <T> Logger logger(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}

