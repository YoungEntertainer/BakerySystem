package za.co.bakerysystem.exception.customer;

public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
