package com.flight_reservation_app.utilities;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    
    @Autowired
    private JavaMailSender sender;
    
    public void sendItinerary(String toAddress, String filePath) {
        // Basic email address validation
        if (!isValidEmailAddress(toAddress)) {
            System.err.println("Invalid recipient email address: " + toAddress);
            return; // Exit early if the email address is not valid
        }
        
        System.out.println(filePath);
            
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject("Itinerary Of Flight");
            messageHelper.setText("Please find the attached");
            messageHelper.addAttachment("Itinerary", new File(filePath));
            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    // Validate email address using a simple regex pattern
    private boolean isValidEmailAddress(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}
