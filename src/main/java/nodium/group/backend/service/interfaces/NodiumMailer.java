package nodium.group.backend.service.interfaces;

import jakarta.mail.MessagingException;

public interface NodiumMailer {
    void sendOTP(String reciepient) throws MessagingException;
}
