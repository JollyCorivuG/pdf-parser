package bupt.edu.jhc.pdf.parser.core.extraction.grobid.exceptions;

public class GrobidTimeoutException extends RuntimeException {

    public GrobidTimeoutException() {
        super();
    }

    public GrobidTimeoutException(String message) {
        super(message);
    }

    public GrobidTimeoutException(Throwable cause) {
        super(cause);
    }

    public GrobidTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
