package mdrive.business.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:15:55
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedDataAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Object entity;

    private Exception exception;

    private DataAccessOperation operation;

    public static enum DataAccessOperation {
        CREATE, READ, UPDATE, DELETE
    }

    public ExtendedDataAccessException(Exception e, Object entity, DataAccessOperation operation) {
        setException(e);
        setEntity(entity);
        setOperation(operation);
    }

    public DataAccessOperation getOperation() {
        return operation;
    }

    public void setOperation(DataAccessOperation operation) {
        this.operation = operation;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String getMessage() {
        if (exception != null) {
            return exception.getMessage();
        } else {
            return super.getMessage();
        }
    }

    @Override
    public Throwable getCause() {
        if (exception != null) {
            return exception.getCause();
        } else {
            return super.getCause();
        }
    }
}
