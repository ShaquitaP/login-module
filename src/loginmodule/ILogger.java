package loginmodule;

public interface ILogger {
    public void logEvent(String eventType, String username, boolean success, String details);
    public void logEvent(String eventType, String username, boolean success);
}
