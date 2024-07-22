package com.example.comeonBusan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.comeonBusan.entity.Newsletter;
import com.example.comeonBusan.repository.NewsletterRepository;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    @Autowired
    private NewsletterRepository repository;

    @PostMapping
    public Newsletter subscribe(@RequestBody Newsletter newsletter) {
        return repository.save(newsletter);
    }

    @GetMapping
    public List<Newsletter> getAllSubscribers() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteSubscriber(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
    
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("subject") String subject,
                            @RequestParam("body") String body,
                            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
                            @RequestParam("emails") List<String> emails) {
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setTo(emails.toArray(new String[0]));

            if (attachment != null && !attachment.isEmpty()) {
                helper.addAttachment(attachment.getOriginalFilename(), attachment);
            }

            mailSender.send(message);
            return "Emails sent successfully";
        } catch (jakarta.mail.MessagingException e) {
            return "Failed to send emails: " + e.getMessage();
        }
    }
}