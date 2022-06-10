package board;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Exceptions {

    public static class NoFileException extends Exception {

        public NoFileException (final String message) {
            super(message);
        }

        public NoFileException (final String message, final Throwable cause) {
            super(message, cause);
        }

    }

}
