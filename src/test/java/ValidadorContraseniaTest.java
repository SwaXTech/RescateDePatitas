import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

import dominio.usuarios.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dominio.exceptions.password.CommonPasswordException;
import dominio.exceptions.password.PasswordLengthException;
import dominio.exceptions.password.PasswordWithLowerCharException;
import dominio.exceptions.password.PasswordWithNumberCharException;
import dominio.exceptions.password.PasswordWithUpperCharException;
import dominio.passwords.CommonPassword;
import dominio.passwords.LowerChar;
import dominio.passwords.NumberChar;
import dominio.passwords.PasswordLength;
import dominio.passwords.UpperChar;
import dominio.passwords.Validation;
import dominio.repositorio.RepositorioValidaciones;
import dominio.usuarios.Admin;
import org.mindrot.jbcrypt.BCrypt;

@Disabled
class ValidadorContraseniaTest {
	RepositorioValidaciones repositorioValidaciones = RepositorioValidaciones.getInstance();
	static CommonPassword commonPassword;

	@BeforeAll
	static void setUp() {
		commonPassword = mock(CommonPassword.class);
		doThrow(CommonPasswordException.class).when(commonPassword).error();

		when(commonPassword.condition(anyString())).thenReturn(true);

		String[] commonPasswords = new String[]{"batman","iceman","superman","orange","black","andrea","thomas"};
		for (String password : commonPasswords) {
			when(commonPassword.condition(password)).thenReturn(false);
		}
	}


	@DisplayName("1234 es una contraseña muy corta")
	@Test
	@Disabled
	void testPasswordLength(){
		usarEstasValidaciones(new PasswordLength());
		assertThrows(PasswordLengthException.class, () -> validar("1234"));
	}

	@DisplayName("La contraseña tiene que tener al menos una mayúscula")
	@Test
	@Disabled
	void testPasswordUpperCase(){
		usarEstasValidaciones(new UpperChar());
		assertThrows(PasswordWithUpperCharException.class, () -> validar("password"));
	}

	@DisplayName("La contraseña tiene que tener al menos una minúscula")
	@Test
	@Disabled
	void testPasswordLowerCase(){
		usarEstasValidaciones(new LowerChar());
		assertThrows(PasswordWithLowerCharException.class, () -> validar("PASSWORD"));
	}

	@DisplayName("La contraseña tiene que tener al menos un número")
	@Test
	@Disabled
	void testPasswordNumber(){
		usarEstasValidaciones(new NumberChar());
		assertThrows(PasswordWithNumberCharException.class, () -> validar("PASSWORD"));
	}

	@Test
	@Disabled
	void batmanNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("batman"));
	}

	@Test
	@Disabled
	void icemannNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("iceman"));
	}

	@Test
	@Disabled
	void supermanNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("superman"));
	}

	@Test
	@Disabled
	void orangeNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("orange"));
	}

	@Test
	@Disabled
	void blackNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("black"));
	}

	@Test
	@Disabled
	void andreaNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("andrea"));
	}

	@Test
	@Disabled
	void thomasNoEsUnaClaveSegura() {
		usarEstasValidaciones(commonPassword);
		assertThrows(CommonPasswordException.class, () -> validar("thomas"));
	}

	@Test
	@Disabled
	void esSeguraUnaClaveAlfanumericaConSimbolos() {
		usarEstasValidaciones(commonPassword, new PasswordLength(), new LowerChar(), 
			new UpperChar(), new NumberChar());
		assertDoesNotThrow(() -> validar("123Asd123.0?"));
	}

	@Test
	@Disabled
	void testHashPassword(){
		Usuario usuario = new Admin("JorgeLanata", "ensaladA10");
		assertTrue (BCrypt.checkpw("ensaladA10",usuario.getPassword()));
	}

	void usarEstasValidaciones(Validation... validaciones){
		repositorioValidaciones.vaciar();
		repositorioValidaciones.registrar(validaciones);
	}

	void validar(String password){
		repositorioValidaciones.validatePassword(password);
	}
}
