package com.dareTo.services.impl;

import com.dareTo.data.models.EmailMessage;
import com.dareTo.dto.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTypeServices {

    @Autowired
    private EmailPublisher emailPublisher;

    public void sendWelcomeEmail(UserRequest request) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(request.getEmail());
        emailMessage.setSubject("Welcome to DareTo");
        emailMessage.setBody(
                "Dear " + request.getUsername()+"," +
                        """ 
                             \nWe’re thrilled to have you on board. With DareTo, you can:
                     
                             - Organize your tasks efficiently ✅
                             - Set reminders so nothing slips through ⏰
                             - Track your progress and stay productive 📈
                        """
        );

        emailPublisher.publishEmail(emailMessage);
    }

    public void sendForgotPasswordEmail(String email, String token) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("Reset your password");
        emailMessage.setBody("Tap the link below to reset your password\n" + "http://127.0.0.1:5501/resetPassword/resetPassword.html?token=" + token);
        emailPublisher.publishEmail(emailMessage);
    }
}
