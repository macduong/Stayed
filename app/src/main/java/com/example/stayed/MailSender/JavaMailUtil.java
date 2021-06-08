package com.example.stayed.MailSender;


import android.os.AsyncTask;

import com.example.stayed.Model.Dump;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaMailUtil {
    public static void sendMail(String recepient, String OTP) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.timeout", "10000");
        properties.put("mail.smtp.connectiontimeout", "10000");

        Dump dump = new Dump();
        String myAccount = Dump.getMail();
        String password = Dump.getPassword();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });
        Message message = prepareMessage(session, myAccount, recepient, OTP);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    Transport.send(message);

                } catch (Exception e) {
                    Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });

        thread.start();

    }

    private static Message prepareMessage(Session session, String myAccount, String recepient, String otp) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount, "Stayed Support Team"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Your OTP from Stayed");
            message.setText("Your OTP is " + otp + ". To verify your account, enter this code in Stayed.\nThis OTP will expire in 5 minutes, please use it as soon as possible." +
                    "\nStayed Support Team.");
            return message;
        } catch (Exception e) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


}