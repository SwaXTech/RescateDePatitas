package dominio.exceptions;

public class CommonPasswordException extends RuntimeException{
  public CommonPasswordException(){
    super("Contraseña vulnerable, elegir otra, por favor.");
  }
}
