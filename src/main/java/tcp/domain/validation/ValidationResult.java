package tcp.domain.validation;

import java.util.HashSet;
import java.util.Set;

public class ValidationResult {

    private Status status;

    private Set<String> validationMessages;

    public ValidationResult(Status status) {
        this.status = status;
        this.validationMessages = new HashSet<>();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(Set<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

}
