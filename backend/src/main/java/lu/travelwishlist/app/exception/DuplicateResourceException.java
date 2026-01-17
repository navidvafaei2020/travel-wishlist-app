package lu.travelwishlist.app.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String resource, String field, String value) {
        super(String.format("%s with %s %s already exist!",resource, field, value));
    }

}
