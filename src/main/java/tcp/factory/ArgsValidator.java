package tcp.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import tcp.domain.validation.Status;
import tcp.domain.validation.ValidationResult;

import static tcp.main.TCPConfig.*;


public final class ArgsValidator {

    private static Integer maxMessageSize = Integer.valueOf(getProperty(MAX_MESSAGE_SIZE));

    private static Integer minMessageSize = Integer.valueOf(getProperty(MIN_MESSAGE_SIZE));

    private static ArgsValidator instance;

    public ValidationResult validate(Args args) {

        if (args == null) {
            return creationErrorResult();
        }

        if (args.isCatcher()) {
            return validateCatcher(args);
        } else if (args.isPitcher()) {
            return validatePitcher(args);
        }

        return creationErrorResult();
    }

    public static ArgsValidator getInstance() {
        if (instance == null) {
            synchronized (ArgsValidator.class) {
                if (instance == null) {
                    instance = new ArgsValidator();
                }
            }
        }
        return instance;
    }

    private ValidationResult validateCatcher(Args args) {

        ValidationResult result = new ValidationResult(Status.OK);

        if (args.getPort() == null) {
            result.getValidationMessages().add("Missing port, add -port argument");
        }


        if (StringUtils.isEmpty(args.getBind())) {
            result.getValidationMessages().add("Missing Bind, add -bind or -b argument");
        }

        if (!CollectionUtils.isEmpty(result.getValidationMessages())) {
            result.setStatus(Status.NOK);
        }

        return result;
    }

    private ValidationResult validatePitcher(Args args) {

        ValidationResult result = new ValidationResult(Status.OK);

        if (args.getPort() == null) {
            result.getValidationMessages().add("Missing port, add -port argument");
        }

        if (args.getSize() != null) {
            if (args.getSize().compareTo(maxMessageSize) > 0) {
                result.getValidationMessages().add("Max size is " + maxMessageSize);
            } else if (args.getSize().compareTo(minMessageSize) < 0) {
                result.getValidationMessages().add("MIN size is " + minMessageSize);
            }
        }

        if (args.getMps() == null) {
            result.getValidationMessages().add("Missing messages per second, add -mps argument");
        }

        if (StringUtils.isEmpty(args.getHostname())) {
            result.getValidationMessages().add("Missing hostname, add -h, -host or -hostname argument");
        }

        if (!CollectionUtils.isEmpty(result.getValidationMessages())) {
            result.setStatus(Status.NOK);
        }

        return result;

    }

    private ValidationResult creationErrorResult() {
        ValidationResult result = new ValidationResult(Status.NOK);
        result.getValidationMessages().add("Error creating communicator, are you missing -p or -c argument?");
        return new ValidationResult(Status.NOK);
    }

}
