package com.w2a.APITestingFramework.Utilities;

import java.io.File;
import java.util.Properties;

import com.w2a.APITestingFramework.SetUp.BaseTest;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailUtils {

	public static void sendEmail(String reportPath) {

		String host = BaseTest.config.getProperty("email.host");
		String port = BaseTest.config.getProperty("email.port");
		String username = BaseTest.config.getProperty("email.username");
		String password = BaseTest.config.getProperty("email.password");
		String recipient = BaseTest.config.getProperty("email.to");

		File reportFile = new File(reportPath);

		if (!reportFile.exists()) {
			System.out.println("Extent Report not found: " + reportPath);
			return;
		}

		Properties properties = new Properties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

			message.setSubject("Rest Assured Test Execution Report");

			// Email body
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(
					"<html>" + "<body>" + "<p>Hello,</p>" + "<p>Rest Assured test execution has completed.</p>"
							+ "<p>Please find the Extent Report attached to this email.</p>"
							+ "<p>Regards,<br>API Automation Framework</p>" + "</body>" + "</html>",
					"text/html; charset=utf-8");

			// Extent Report attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();

			attachmentPart.attachFile(reportFile);

			// Combine email body + attachment
			MimeMultipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			// Send email
			Transport.send(message);

			System.out.println("Email sent successfully.");
			System.out.println("Extent Report attached: " + reportFile.getName());

		} catch (Exception e) {

			System.out.println("Failed to send email.");
			e.printStackTrace();
		}
	}
}