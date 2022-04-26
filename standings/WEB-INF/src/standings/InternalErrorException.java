package standings;

public class InternalErrorException extends Exception {
  private Exception cause;

  public InternalErrorException(String message, Exception cause) {
    super(message);
    this.cause = cause;
  }

  public Exception getCause() {
    return cause;
  }
}
