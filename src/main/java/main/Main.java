package main;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import dominio.mascota.Caracteristica;
import dominio.passwords.CommonPassword;
import dominio.passwords.LowerChar;
import dominio.passwords.UpperChar;
import dominio.personas.Contacto;
import dominio.personas.DatosPersona;
import dominio.personas.Documento;
import dominio.personas.TipoDeDocumento;
import dominio.repositorio.RepositorioCaracteristicas;
import dominio.repositorio.RepositorioValidaciones;
import dominio.tareas.Recomendador;
import dominio.usuarios.Duenio;
import router.Router;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import servicios.mail.JavaMail;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class Main {

  public static void main(String[] args) {


    System.out.println("**********************");
    System.out.println("**Iniciando Servidor**");
    System.out.println("**********************");

    Spark.port(9000);
    new Router().setup();


    // Spark.get("/test", (req, res) -> "Hello World!");

    // Spark.staticFiles.location("public");
    // Map<String, Object> model = new HashMap<>();

    // Spark.get("/", (request, response) -> new ModelAndView(model, "index.hbs") ,
    //   new HandlebarsTemplateEngine());

    if (args.length > 0 && args[0].equals("run_recomendaciones")) {

      System.out.println("****************************");
      System.out.println("**Enviando Recomendaciones**");
      System.out.println("****************************");

      runRecomendaciones();
      System.exit(0);
    }
    runServer();

    // Log only sql hibernate/jpa
    Logger logger = Logger.getLogger("org.hibernate");
    logger.setUseParentHandlers(false);

    EntityManager entityManager;

    try{
      entityManager = PerThreadEntityManagers.getEntityManager();
    }catch (Exception e){
      System.out.println("*************************************************************************************************");
      System.out.println("**Ocurrió un error al conectar con la base de datos. Verifique que el motor se encuentra activo**");
      System.out.println("*************************************************************************************************");
      return;
    }

    System.out.println("**************************************************");
    System.out.println("**Se estableció la conexión con la Base de Datos**");
    System.out.println("**************************************************");



    //RepositorioCaracteristicas.getINSTANCE().registrar(new Caracteristica("Ladra mucho"), new Caracteristica("Es tranquilo"));



  //  Contacto contacto = new Contacto("Ian", "Crespi",12 ,"crespi.ian@gmail.com");
  //  Documento documento = new Documento(TipoDeDocumento.DNI, "42255284");
  //  DatosPersona datosPersona = new DatosPersona("Herasimiuk","Alexis", documento,contacto, LocalDate.now());
  //  Duenio duenio = new Duenio("iancrespiok", "kirchneristadecorazon", datosPersona);

  //  entityManager = PerThreadEntityManagers.getEntityManager();
  //  EntityTransaction transaction = entityManager.getTransaction();

  //  transaction.begin();
  //  entityManager.persist(duenio);
  //  transaction.commit();
  //  entityManager.close();

    // System.out.println(RepositorioDuenios.getInstance().todos());
  }

  private static void runRecomendaciones() {
    System.out.println("Recomenaciones running");
    try {
      Logger logger = Logger.getLogger("name");
      FileHandler fh = new FileHandler("<PATH_TO_LOG_FILE>");
      logger.addHandler(fh);
      logger.info("asd");
    } catch (Exception e) {

    }
    (new Recomendador()).enviarRecomendaciones(new JavaMail());
  }

  private static void runServer() {

  }
}
