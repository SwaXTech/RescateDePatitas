import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dominio.asociacion.Asociacion;
import dominio.hogares.Hogar;
import dominio.mascota.ClaseMascota;
import dominio.mascota.Mascota;
import dominio.mascota.Sexo;
import dominio.mascota.Tamanio;
import dominio.personas.Contacto;
import dominio.personas.DatosPersona;
import dominio.personas.Documento;
import dominio.personas.TipoDeDocumento;
import dominio.rescate.DatosRescate;
import dominio.rescate.Publicacion;
import dominio.rescate.Rescate;
import dominio.rescate.Rescatista;
import dominio.ubicacion.Coordenadas;
import dominio.usuarios.Duenio;
import dominio.util.Lista;

public class Fixture {

  private final Coordenadas parqueChacabuco   = new Coordenadas(-34.63481134002147, -58.442202384019055);
  private final Coordenadas parqueAvellaneda  = new Coordenadas(-34.64388667313111, -58.47976161190845);

  public Mascota getPupi() {
    return crearAPupi();
  }

  public Mascota getFelix() {
    return crearAFelix();
  }

  public Mascota getVladi() {
    return crearAVladi();
  }

  public Duenio getCarlos() {
    return crearACarlos();
  }

  public Duenio getSamuel() {
    return crearASamuel();
  }

  public Rescatista getPedro() {
    return crearAPedro();
  }

  public Rescate getRescatePupi() {
    return rescatarAPupi();
  }

  public Rescate getRescateFelix() {
    return rescatarAFelix();
  }

  public Asociacion getColaDeGato() {
    return asociacionColaDeGato();
  }

  public Asociacion getPatitasSucias() {
    return asociacionPatitasSucias();
  }

  public Publicacion getPublicacionUTN() {
    return publicacionMascotaUTN();
  }

  public Hogar getHogarCarinio() {
  	return crearHogarCarinioso();
  }

  public Hogar getElHiltonParaGatos() {
  	return crearHiltonParaGatos();
  }
  public Hogar getElHiltonPerruno() {
  	return crearPequenioHogarPerruno();
  }

  public Hogar getElPequenioHogarPerruno() {
  	return crearHiltonPerruno();
  }

  public Hogar getHogarAbandonado() {
  	return crearHogarAbandonado();
  }

  public Coordenadas getUTN() {
    return buildUTN();
  }

  private Duenio crearACarlos() {
    Documento documento = new Documento(TipoDeDocumento.DNI, "21789654");
    DatosPersona datosPersona = new DatosPersona("Perez", "Carlos", documento, unContacto(),
        stringAFecha("01/01/2002"));

    return new Duenio("carlosKpo123", "Pupitoteamo1", datosPersona);
  }

  private Duenio crearASamuel() {
    Documento documento = new Documento(TipoDeDocumento.DNI, "21789651");
    DatosPersona datosPersona = new DatosPersona("Perez", "Samuel", documento, unContacto(),
        stringAFecha("01/01/2001"));

    return new Duenio("samuKpo123", "Vladiteamo1", datosPersona);
  }

  private Rescatista crearAPedro() {
    Documento documento = new Documento(TipoDeDocumento.DNI, "21789654");
    DatosPersona datosPersona = new DatosPersona("Perez", "Pedro", documento, unContacto(), stringAFecha("02/02/1996"));

    return new Rescatista(datosPersona, "Calle Falsa 123");
  }

  private Mascota crearAPupi() {
    Mascota pupi = new Mascota(ClaseMascota.GATO, "Pupi", "Pupi", 3, Sexo.MACHO, Tamanio.CHICO);
    pupi.setDescripcionFisica("Un gato siamés, marrón con manchas blancas");
    return pupi;
  }

  private Mascota crearAFelix() {
    return new Mascota(ClaseMascota.PERRO, "felix", "feli", 5, Sexo.MACHO, Tamanio.GRANDE);
  }

  private Mascota crearAVladi() {
    return new Mascota(ClaseMascota.PERRO, "vladi", "vla", 5, Sexo.MACHO, Tamanio.MEDIANO);
  }

  private LocalDate stringAFecha(String fecha) {
    return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
  }

  private Contacto unContacto() {
    return new Contacto("Federico", "Bal", 1180700542, "fedebal@gmail.com");
  }

  private Rescate rescatarAFelix() {
    DatosRescate datosRescate = new DatosRescate(crearAPedro(), new Lista<>(), LocalDate.now().plusDays(-15), "perro negro con mancha blanca en la panza", new Coordenadas(-55., -55.));
    Rescate rescateFelix = new Rescate(datosRescate, crearAFelix());
    return rescateFelix;
  }

  private Rescate rescatarAPupi() {
    DatosRescate datosRescate = new DatosRescate(crearAPedro(), new Lista<>(), LocalDate.now(), "parece ser un gato siames", buildUTN());
    Rescate rescatePupi = new Rescate(datosRescate, crearAPupi());
    return rescatePupi;
  }

  private Publicacion publicacionMascotaUTN(){
    DatosRescate datosRescate = new DatosRescate(crearAPedro(), new Lista<>(), LocalDate.now().minusDays(1), "parece ser un gato siames", buildUTN());
    return new Publicacion(datosRescate, Tamanio.CHICO, ClaseMascota.GATO);
  }

  private Asociacion asociacionPatitasSucias(){
    return new Asociacion("Patitas Sucias", parqueAvellaneda);
  }

  private Asociacion asociacionColaDeGato(){
    return new Asociacion("Cola de Gato", parqueChacabuco);
  }

  private Hogar crearHogarAbandonado() {  	
		return new Hogar("HogarAbandonado", "0800-999-111", null, false, null, buildUTN(), false);  	
  }
  
  private Hogar crearHogarCarinioso() {  	
  	Lista<ClaseMascota> prefierenCualquierMascota = new Lista<ClaseMascota>(ClaseMascota.PERRO, ClaseMascota.GATO);
  	  	
		return new Hogar("somosHogarCarinioso", "0800-999-111", prefierenCualquierMascota, false, new Lista<String>(), buildUTN(), true);
  }

  private Hogar crearHiltonPerruno() {  	  	
		return new Hogar("elHiltonPerruno", "0800-999-112", new Lista<ClaseMascota>(ClaseMascota.PERRO), true, new Lista<String>(), buildUTN(), true);
  }

  private Hogar crearHiltonParaGatos() {  	  	
		return new Hogar("elHiltonParaMascotasGatunas", "0800-999-112", new Lista<ClaseMascota>(ClaseMascota.GATO), true, new Lista<String>(), buildUTN(), true);
  }

  private Hogar crearPequenioHogarPerruno() {  	  	
		return new Hogar("elPequenioHogarParaPerritos", "0800-999-112", new Lista<ClaseMascota>(ClaseMascota.PERRO), false, new Lista<String>(), buildUTN(), false);
  }

  private Coordenadas buildUTN(){
    return new Coordenadas(-34.65858825852768, -58.46736257475716);
  }
  
}