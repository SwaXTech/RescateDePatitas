import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.adopcion.DarEnAdopcion;
import dominio.rescate.RescateConChapita;
import dominio.rescate.RescateSinChapita;
import dominio.usuarios.Duenio;
import servicios.mail.Mail;
import servicios.mail.MailAdopcion;
import servicios.mail.MailRecomendacion;
import servicios.mail.MailRescateConChapita;
import servicios.mail.MailRescateSinChapita;


class MailerTest{
  private Fixture fixture = new Fixture();

  private MailRescateConChapita mailRescateConChapita;
  private MailRescateSinChapita mailRescateSinChapita;
  private MailAdopcion mailAdopcion;
  private MailRecomendacion mailRecomendacion;

  private RescateConChapita rescateConChapita;
  private RescateSinChapita publicacionMascotaUTN;
  
  private Duenio sabato;  
  
  private DarEnAdopcion publicacionSabatoDaEnAdopcionAPupi;
  private List<DarEnAdopcion> publicaciones;
  
  @BeforeEach
  void setup() {
    PerThreadEntityManagers.getEntityManager().getTransaction().begin();
    publicaciones = new ArrayList<>();
    rescateConChapita = fixture.rescatarAPupi();
    publicacionMascotaUTN = fixture.publicacionMascotaUTN();
    mailRescateConChapita = new MailRescateConChapita(rescateConChapita);
    mailRescateSinChapita = new MailRescateSinChapita(publicacionMascotaUTN);    
    publicacionSabatoDaEnAdopcionAPupi = fixture.publicacionSabatoDaEnAdopcionAPupi();
  
    sabato = fixture.crearASabato();
    
    publicaciones.add(publicacionSabatoDaEnAdopcionAPupi);
  }

  @AfterEach
  void tearDown(){
    PerThreadEntityManagers.getEntityManager().getTransaction().rollback();
  }

  @Test
  void rescatistaDePupiMensajeValido() {
    String mensaje = "Hola! Roberto\nEstamos muy contentos de anunciarte que encontramos al dueño de una mascota que encontraste\n\nRoberto Gimenez se contactará con vos para continuar con el proceso";
    mailRescateSinChapita = new MailRescateSinChapita(publicacionMascotaUTN);
    assertEquals(mensaje, mailRescateSinChapita.generarMail().getMensaje());   
  }

  @Test
  void rescatistaDePupiTieneAsuntoValido() {
    String asunto = "Buenas noticias!. Identificamos al dueño de una mascota que encontraste";
    mailRescateSinChapita = new MailRescateSinChapita(publicacionMascotaUTN);
    assertEquals(asunto, mailRescateSinChapita.generarMail().getAsunto());
  }

  @Test
  void rescatistaDePupiTieneDestinatarioValido() {
    mailRescateSinChapita = new MailRescateSinChapita(publicacionMascotaUTN);
    String destinatario = "robertito@gmail.com";
    Mail mail = mailRescateSinChapita.generarMail();
    assertEquals(destinatario, mail.getDestinatario());    
  }

  @Test
  void mailRescatePupiMensajeValido() {
    String mensaje = "Hola! Federico\nEstamos muy contentos de anunciarte que encontramos tu mascota!\nPupi fue encontrada por Roberto\n\nRescatista: Roberto Gimenez\nTeléfono: 1180700543\nEmail robertito@gmail.com\nDescripción en la que se encontró la mascota: parece ser un gato siames";
    Mail mail = mailRescateConChapita.generarMail();
    assertEquals(mensaje, mail.getMensaje());
  }

  @Test
  void mailRescatePupiAsuntoValido() {
    String asunto = "Tenemos buenas noticias!. Encontramos a Pupi";
    assertEquals(asunto, mailRescateConChapita.generarMail().getAsunto());
  }

  @Test
  void mailRescatePupiDestinatariolValido() {
    String destinatario = "robertito@gmail.com";
    assertEquals(destinatario, mailRescateConChapita.generarMail().getDestinatario());
  }

  @Test 
  void mailRecomendacionMensajeValido() {
    String mensaje ="Hola! Federico\nEstamos muy contentos de anunciarte que encontramos tu mascota!\nPupi fue encontrada por Roberto\n\nRescatista: Roberto Gimenez\nTeléfono: 1180700543\nEmail robertito@gmail.com\nDescripción en la que se encontró la mascota: parece ser un gato siames";
    mailRecomendacion = new MailRecomendacion(sabato, publicaciones);
    assertEquals(mensaje, mailRescateConChapita.generarMail().getMensaje());        
  }

  @Test
  void mailRecomendacionAsuntoValido() {
    String asunto ="¡Hey!. ¡Tenemos algunas recomendaciones para vos!";
    mailRecomendacion = new MailRecomendacion(sabato, publicaciones);
    assertEquals(asunto, mailRecomendacion.generarMail().getAsunto());        
  }

  @Test
  void mailRecomendacionDestinatarioValido() {
    String destinatario = "fedebal@gmail.com";
    mailRecomendacion = new MailRecomendacion(sabato, publicaciones);
    assertEquals(destinatario, mailRecomendacion.generarMail().getDestinatario());        
  }

  @Test
  void mailDeAdopcionMensajeValido() {
    String mensaje ="Hemos encontrado un adpotante para tu mascota\nSabato desea adoptar a tu mascotaPuedes contactarte mediante los siguientes medios: \nTelefono: 1180700542\nEmail: fedebal@gmail.com\n";
    mailAdopcion = new MailAdopcion(publicacionSabatoDaEnAdopcionAPupi, sabato);
    assertEquals(mensaje, mailAdopcion.generarMail().getMensaje());
  }

  @Test
  void mailDeAdopcionAsuntoValido() {
    String asunto ="¡Tenemos noticias!. Conseguimos adoptante para: feli";
    mailAdopcion = new MailAdopcion(publicacionSabatoDaEnAdopcionAPupi, sabato);
    assertEquals(asunto, mailAdopcion.generarMail().getAsunto());
  }
  
  @Test
  void mailDeAdopcionDestinatarioValido() {
    String destinatario ="fedebal@gmail.com";
    mailAdopcion = new MailAdopcion(publicacionSabatoDaEnAdopcionAPupi, sabato);
    assertEquals(destinatario, mailAdopcion.generarMail().getDestinatario());
  }  

}
