package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.exception.AccountGenerationException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final AmazonSimpleEmailService amazonSESClient;

    private static final String SENDER_EMAIL = "ioananovacovici@gmail.com";

    public EmailService(AmazonSimpleEmailService amazonSESClient) {
        this.amazonSESClient = amazonSESClient;
    }

    public void sendSuccessEmail(UserDTO userDTO, String username, String password) {
        String emailContent =
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="utf-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1">
                            <title>Green Cycle Solutions</title>
                        </head>
                        <body style="background: whitesmoke; padding: 20px; height: 100%">
                        <h5 style="font-size: 18px; margin-bottom: 6px">Dear
                        """
                        + userDTO.getFirstName() +
                        """      
                        """ +
                        " " + userDTO.getLastName() +
                        """
                        , </h5>
                        <p style="font-size: 16px; font-weight: 500">We received your request for activating your account on Green Cycle Solutions.
                        Your account is now ready to use. Please use the credentials down below for logging into your newly created account.</p>
                        <p style="font-size: 16px; font-weight: 500">Username:
                        """
                        + username +
                        """
                        </p>
                        <p style="font-size: 16px; font-weight: 500">Password:
                        """
                        + password +
                        """
                        <p style="font-size: 16px; font-weight: 500">Best regards, from Green Cycle Solutions</p>
                        </body>
                        </html>""";

        String emailSubject = "Green Cycle Solutions - Account Activation Request Successful";

        sendEmail(userDTO, emailContent, emailSubject);
    }

    private void sendEmail(UserDTO userDTO, String emailContent, String emailSubject) {
        try {
            SendEmailRequest sendEmailRequest = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(userDTO.getEmail()))
                    .withMessage(new Message()
                            .withBody(
                                    new Body().withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
                            .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
                    .withSource(SENDER_EMAIL);
            amazonSESClient.sendEmail(sendEmailRequest);

        } catch (Exception e) {
            throw new AccountGenerationException("The email provided is not a verified account");
        }
    }
}