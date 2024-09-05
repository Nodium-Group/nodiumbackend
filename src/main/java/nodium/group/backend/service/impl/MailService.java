package nodium.group.backend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import nodium.group.backend.service.interfaces.NodiumMailer;
import nodium.group.backend.config.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService implements NodiumMailer {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("$(spring.mail.username)")
    private String mailSender;
    @Override
    public void sendOTP(String reciepient) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom("NODIUM");
        mimeMessageHelper.setFrom(mailSender);
        mimeMessageHelper.setTo(reciepient);
        mimeMessageHelper.setSubject("OTP for Password Update.");
        String html =
                """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Verification Code</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            text-align: center;
                            background-color: #f9f9f9;
                        }
                        .container {
                            max-width: 400px;
                            margin: 0 auto;
                            padding: 20px;
                            background-color: #ffffff;
                            border-radius: 8px;
                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        }
                        .verification-code {
                            font-size: 24px;
                            font-weight: bold;
                            margin-bottom: 20px;
                        }
                        .notice {
                            font-style: italic;
                            color: #888;
                        }
                        .ban-button {
                            background-color: #007bff;
                            color: #ffffff;
                            border: none;
                            border-radius: 4px;
                            padding: 10px 20px;
                            cursor: pointer;
                            text-decoration: none;
                        }
                    </style>
                </head>
                <body  style="">
                    <div class="container">
                        <h1>Verification Code</h1>
                        <p>The verification code is:</p>
                        <div class="verification-code">${pin}</div>
                        <p>The verification code is valid for 5 minutes.</p>
                        <p>For the security of your account, please do not disclose the following verification code to anyone, including Bybit staff.</p>
                        <p>If you need further assistance, please contact our live customer support or email us at <a href="mailto:support@nodium.com">support@nodium.com</a>.</p>
                    </div>
                </body>
                </html>
                """;
        html.replace("${pin}",new OTPGenerator().generatePin(reciepient));
        mimeMessageHelper.setText(html,true);
        javaMailSender.send(message);
    }

}
