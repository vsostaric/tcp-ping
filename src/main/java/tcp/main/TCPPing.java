package tcp.main;

import tcp.domain.communicators.Communicator;
import tcp.domain.validation.Status;
import tcp.domain.validation.ValidationResult;
import tcp.factory.Args;
import tcp.factory.ArgsValidator;
import tcp.factory.CommunicatorFactory;

import java.io.IOException;

public class TCPPing {

    public static void main(String[] args) {

        Args parsedArgs = new Args(args);

        ArgsValidator validator = ArgsValidator.getInstance();
        ValidationResult validation = validator.validate(parsedArgs);

        if (Status.OK.equals(validation.getStatus())) {

            Communicator communicator = CommunicatorFactory
                    .getInstance()
                    .getCommunicator(parsedArgs);

            try {
                communicator.communicate();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("Error!");
            for (String err : validation.getValidationMessages()) {
                System.out.println(err);
            }

        }

    }

}
