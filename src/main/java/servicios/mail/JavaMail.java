package servicios.mail;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import config.Config;


public class JavaMail {

  private final Session session;
  private String emisor;
  private String password;

  public JavaMail() {

    credenciales();
  
    Properties props = new Properties();

    // Nombre del host de correo, es smtp.gmail.com
    props.setProperty("mail.smtp.host", "smtp.gmail.com");

    // TLS si está disponible
    props.setProperty("mail.smtp.starttls.enable", "true");

    // Puerto de gmail para envio de correos
    props.setProperty("mail.smtp.port","587");

    // Nombre del usuario
    props.setProperty("mail.smtp.user", emisor);

    // Si requiere o no usuario y password para conectarse.
    props.setProperty("mail.smtp.auth", "true");

    session = Session.getDefaultInstance(props);
    session.setDebug(true);
  
  }

  private void credenciales(){
    Config config = new Config();
    emisor = config.getConfig("mail.sender");
    password = config.getConfig("mail.password");
  }


  public void enviarMail(Mailer mailer){
    MimeMessage message = new MimeMessage(session);
    Mail mail = mailer.generarMail();
    try {
      // Quien envia el correo
      message.setFrom(new InternetAddress(emisor));

      // A quien va dirigido

      message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getDestinatario()));
      message.setSubject(mail.getAsunto());
      //message.setText(mail.getMensaje());
      message.setContent(mail.getMensaje(), "text/html");

      Transport transport = session.getTransport("smtp");
      transport.connect(emisor, password);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();
    } catch (MessagingException e) {
      throw new EmailException();
    }
    
  }
  
}
