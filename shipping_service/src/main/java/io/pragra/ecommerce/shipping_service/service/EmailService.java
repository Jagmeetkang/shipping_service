package io.pragra.ecommerce.shipping_service.service;

import io.pragra.ecommerce.shipping_service.entity.Shipment;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;

import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public void createOrderMail(Shipment shipment) throws MessagingException {

        Context context=new Context();
        context.setVariable("shipment",shipment);

        String mailContent = templateEngine.process("MailTemplate",context);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("noreplyecommercetest@gmail.com");
        helper.setTo(shipment.getMailId());
        helper.setSubject("Your order " + shipment.getOrderId() + " is being processed");
        helper.setText(mailContent, true);
        emailSender.send(message);

    }

    public void statusUpdateMail(Shipment shipment) throws MessagingException {

        Context context=new Context();
        context.setVariable("shipment",shipment);

        String mailContent = templateEngine.process("MailTemplate",context);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("noreplyecommercetest@gmail.com");
        helper.setTo(shipment.getMailId());
        helper.setSubject("Your order " + shipment.getOrderId() + " has an update");
        helper.setText(mailContent, true);
        emailSender.send(message);

    }
}
