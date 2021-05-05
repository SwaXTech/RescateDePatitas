package dominio.mascota;

import java.util.HashMap;
import java.util.Map;

import dominio.util.Lista;

public class Mascota {
  private Clase clase;
  private String nombre;
  private String apodo;
  private int edad;
  private Sexo sexo;
  private String descripcionFisica;
  private Lista<String> fotos;
  private Map<String, String> caracteristicas;

  public Mascota(Clase clase, String nombre, String apodo, int edad, Sexo sexo) {
    this.clase = clase;
    this.nombre = nombre;
    this.apodo = apodo;
    this.edad = edad;
    this.sexo = sexo;
    caracteristicas = new HashMap<String, String>();
    this.fotos = new Lista<String>();
  }

  public void setDescripcionFisica(String descripcionFisica) {
    this.descripcionFisica = descripcionFisica;
  }

  public void agregarUnaFoto(String url) {
    this.fotos.add(url);
  }

  public void agregarUnaCaracteristica(String caracteristica, String valor) {
    this.caracteristicas.put(caracteristica, valor);
  }

  public String obtenerCaracteristica(String caracteristica){
    if(!caracteristicas.containsKey(caracteristica)){
      return null;
    }

    return this.caracteristicas.get(caracteristica);
  }

  public Clase getClase() {
    return clase;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApodo() {
    return apodo;
  }

  public int getEdad() {
    return edad;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public String getDescripcionFisica() {
    return descripcionFisica;
  }
}
