package com.example.griftblog.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  private  final JavaMailSender mailSender;
  public void  sendMail(String to, String subject, String body){
      try {
          SimpleMailMessage message = new SimpleMailMessage();
          message.setTo(to);
          message.setSubject(subject);
          message.setText(body);
          mailSender.send(message);
      }
      catch (MatchException e){
          throw new IllegalStateException("Failed to send confirmation email.");
      }
  }
}
