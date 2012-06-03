package mdrive.business.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: andrey.osipov
 * Date: 6/1/11
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Friendly ConstraintViolationException, which prints what constraints failed
 */
public class ModelConstraintViolationException extends ConstraintViolationException {

    public ModelConstraintViolationException(ConstraintViolationException e) {
        super(e.getMessage(), e.getConstraintViolations());
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        for (ConstraintViolation<?> constraintViolation : getConstraintViolations()) {
            message += "\n" + constraintViolation.toString();
        }
        return message;
    }
}