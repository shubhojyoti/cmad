package com.kartshub.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	private Properties prop = new Properties();
	private Session session;
	private Properties props;
	private InputStream input = null;
	private String smtpServer;
	private String smtpPort;
	private String username;
	private String passwd;

	public SendMail() {
		try {
			input = SendMail.class.getClassLoader().getResourceAsStream(
					"config.properties");
			prop.load(input);
			this.smtpServer = prop.getProperty("smtpServer");
			this.smtpPort = prop.getProperty("smtpPort");
			this.username = prop.getProperty("smtpuname");
			this.passwd = prop.getProperty("smtppwd");
			input.close();

			props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", this.smtpServer);
			props.put("mail.smtp.port", this.smtpPort);
			
			this.session = Session.getInstance(this.props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, passwd);
						}
					  });

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public void SendTheMail(List<String> addrs, String subject, String body) {
		try {
			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("shubhatt@cisco.com"));
			for (String addr: addrs) {
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(addr));
			}
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Mail Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	//public static void main(String[] args) {
	//	SendMail sendmail = new SendMail();
	//	List<String> addrs = new ArrayList<String>();
	//	addrs.add("shubhatt22@gmail.com");
	//	addrs.add("boomboomgof@gmail.com");
	//	sendmail.SendTheMail(addrs, "Sending to multiple people", "Hi People,\n\nThis is a new message for you.");
	//}

}
