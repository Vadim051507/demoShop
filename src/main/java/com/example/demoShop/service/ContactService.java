package com.example.demoShop.service;

import com.example.demoShop.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
   private final JavaMailSender mailSender;

   @Value("${app.mail.to}")
    private String mailTo;

   public ContactService(JavaMailSender mailSender) {
       this.mailSender = mailSender;
   }

   public void sendContactEmail(ContactRequest request) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(mailTo);
       message.setReplyTo(request.getEmail());

       message.setSubject("New message: " + request.getSubject());

       message.setText(buildEmailText(request));

       mailSender.send(message);
   }

   private String buildEmailText(ContactRequest request) {
       return String.format("""
               New message from the Florist Boutique website
               _____________________________________________
               
               Name:      %s
               Email:     %s
               Phone:     %s
               Theme:     %s
               
               Message:
               %s
               """,
               request.getName(),
               request.getEmail(),
               request.getPhone(),
               request.getSubject(),
               request.getMessage());
   }
}
