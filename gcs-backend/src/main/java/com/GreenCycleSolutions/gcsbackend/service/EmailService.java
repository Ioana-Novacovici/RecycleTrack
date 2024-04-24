package com.GreenCycleSolutions.gcsbackend.service;

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

    public EmailService(AmazonSimpleEmailService amazonSESClient) {
        this.amazonSESClient = amazonSESClient;
    }

    public void sendEmail(String to) {
        String emailContent =
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="utf-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1">
                            <title>Example HTML Email</title>
                        </head>
                        <body style="background: whitesmoke; padding: 30px; height: 100%">
                        <h5 style="font-size: 18px; margin-bottom: 6px">Dear example,</h5>
                        <p style="font-size: 16px; font-weight: 500">Greetings from TutorialsBuddy</p>
                        <p>This is a simple html based email.</p>
                        </body>
                        </html>""";

        String emailSubject = "Test Email";

        try {
            SendEmailRequest sendEmailRequest = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(to))
                    .withMessage(new Message()
                            .withBody(
                                    new Body().withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
                            .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
                    .withSource("ioananovacovici@gmail.com");
            amazonSESClient.sendEmail(sendEmailRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}