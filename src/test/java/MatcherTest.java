import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dominio.exceptions.HayPreguntasSinResponder;
import dominio.exceptions.RespuestaInvalida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.adopcion.DarEnAdopcion;
import dominio.adopcion.DarEnAdopcionBuilder;
import dominio.adopcion.SolicitudAdopcion;
import dominio.adopcion.SolicitudAdopcionBuilder;
import dominio.asociacion.Asociacion;
import dominio.mascota.Mascota;
import dominio.preguntas.Pregunta;
import dominio.preguntas.PreguntaBinaria;
import dominio.preguntas.PreguntaCerrada;
import dominio.repositorio.RepositorioPreguntas;
import dominio.repositorio.RepositorioAdopcion;
import dominio.repositorio.RepositorioAsociaciones;
import dominio.repositorio.RepositorioDuenios;
import dominio.tareas.ObtenerPreguntas;
import dominio.usuarios.Duenio;

public class MatcherTest extends AbstractTest {

  private Fixture fixture = new Fixture();
  private Duenio carlos;
  private Duenio samuel;
  private Mascota pupi;
  private Asociacion asociacion;
  private Pregunta[] preguntas;
  private Pregunta global;

  @BeforeEach
  void setup(){

    PerThreadEntityManagers.getEntityManager().getTransaction().begin();

    carlos = fixture.crearACarlos();
    pupi = fixture.crearAPupi();
    carlos.registrarUnaMascota(pupi);
    samuel = fixture.crearASamuel();
    RepositorioDuenios.getInstance().registrar(samuel, carlos);
    asociacion = fixture.asociacionColaDeGato();
    preguntas = new Pregunta[]{
      new PreguntaBinaria("¿Necesita Patio?", "¿Tiene patio?"),
      new PreguntaCerrada("¿Que clase de mascota es?", "¿Que clase de mascota desea?", "PERRO", "GATO"),
      new Pregunta("¿Qué enfermedades tiene la mascota?", null)
    };

    for (Pregunta pregunta : preguntas) {
      asociacion.agregarPregunta(pregunta);
    }

    global = new PreguntaBinaria("¿Duerme en la cama?", "¿Puede dormir en la cama?", true);
    RepositorioPreguntas.getInstance().registrar(global);

    RepositorioAsociaciones.getInstance().registrar(asociacion);
  }

  @Test
  void noSePuedeNoResponderLasPreguntas(){
    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    assertThrows(HayPreguntasSinResponder.class, () -> builder.build());
  }

  @Test
  void noContestaTodasLasPreguntas(){
    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    builder.responderPregunta(preguntas[0], "SI");
    assertThrows(HayPreguntasSinResponder.class, () -> builder.build());
  }

  @Test
  void contestaMalLaPregunta(){
    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    assertThrows(RespuestaInvalida.class, () -> builder.responderPregunta(preguntas[0], "No se"));
  }

  @Test
  void contestaTodasLasPreguntas(){
    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    builder.responderPregunta(preguntas[0], "SI");
    builder.responderPregunta(preguntas[1], "PERRO");
    builder.responderPregunta(preguntas[2], "Tiene convulsiones");
    builder.responderPregunta(global, "SI");
    assertDoesNotThrow(() -> builder.build());
  }

  @Test
  void tieneTresPreguntas(){
    assertEquals(3, asociacion.cantidadDePreguntas());
  }

  @Test
  void hayCuatroPreguntasEnTotalParaElDuenio(){
    assertEquals(4, new ObtenerPreguntas().preguntasDuenio(asociacion).size());
  }

  @Test
  void hayTresPreguntasEnTotalParaElAdoptante(){
    assertEquals(3, new ObtenerPreguntas().preguntasAdoptante(asociacion).size());
  }

  @Test
  void samuelDeseariaAdoptar(){

    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    builder.responderPregunta(preguntas[0], "SI");
    builder.responderPregunta(preguntas[1], "PERRO");
    builder.responderPregunta(preguntas[2], "Tiene convulsiones");
    builder.responderPregunta(global, "SI");
    DarEnAdopcion publicacion = builder.build();
    RepositorioAdopcion.getInstance().registrar(publicacion);

    SolicitudAdopcionBuilder solicitudBuilder = new SolicitudAdopcionBuilder(samuel);
    solicitudBuilder.setAsociacion(asociacion);
    solicitudBuilder.responderPregunta(preguntas[0], "Si");
    solicitudBuilder.responderPregunta(preguntas[1], "PERRO");
    solicitudBuilder.responderPregunta(global, "SI");
    SolicitudAdopcion solicitud = solicitudBuilder.build();

    assertEquals(solicitud.recomendaciones().stream().map(publi -> publi.getMascota().getApodo()).findFirst().orElse(null), "Pupi");

  }

  @Test
  void samuelNoDeseariaAdoptar(){

    DarEnAdopcionBuilder builder = new DarEnAdopcionBuilder(carlos, pupi);
    builder.setAsociacion(asociacion);
    builder.responderPregunta(preguntas[0], "SI");
    builder.responderPregunta(preguntas[1], "PERRO");
    builder.responderPregunta(preguntas[2], "Tiene convulsiones");
    builder.responderPregunta(global, "SI");
    DarEnAdopcion publicacion = builder.build();
    RepositorioAdopcion.getInstance().registrar(publicacion);

    SolicitudAdopcionBuilder solicitudBuilder = new SolicitudAdopcionBuilder(samuel);
    solicitudBuilder.setAsociacion(asociacion);
    solicitudBuilder.responderPregunta(preguntas[0], "Si");
    solicitudBuilder.responderPregunta(preguntas[1], "GATO");
    solicitudBuilder.responderPregunta(global, "SI");
    SolicitudAdopcion solicitud = solicitudBuilder.build();

    assertFalse(solicitud.recomendaciones().contains(publicacion));

  }

}
