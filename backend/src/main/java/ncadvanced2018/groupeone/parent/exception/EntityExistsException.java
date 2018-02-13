package ncadvanced2018.groupeone.parent.exception;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException() {
        super();
    }

    public EntityExistsException(String message) {
        super(message);
    }
}
