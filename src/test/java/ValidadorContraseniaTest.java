import static org.junit.jupiter.api.Assertions.*;

import dominio.usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dominio.usuarios.PasswordValidator;
import org.mindrot.jbcrypt.BCrypt;

class ValidadorContraseniaTest {
	PasswordValidator validadorContrasenia;

	@BeforeEach
	void setup() {
		validadorContrasenia = new PasswordValidator();
	}

	@Test
	void noEsUnaClaveSeguraSoloPonerNumeros() {
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("123456"));
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("123456789"));
	}

	@Test
	void noEsUnaClaveSeguraUsarNombresDeComics() {
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("batman"));
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("iceman"));
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("superman"));
	}

	@Test
	void noEsUnaClaveSeguraUsarNombreDeColores() {
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("orange"));
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("black"));
	}

	@Test
	void noEsUnaClaveSeguraUsarNombreDePersonas() {
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("andrea"));
		assertThrows(RuntimeException.class, () -> PasswordValidator.validate("thomas"));
	}

	@Test
	void esUnaClaveSeguraUsarFrases() {
		assertDoesNotThrow(() -> PasswordValidator.validate("Cuestionesdelavida2"));
		assertDoesNotThrow(() -> PasswordValidator.validate("Unaensaladafria3"));
		assertDoesNotThrow(() -> PasswordValidator.validate("Undiadeveranoquiendiria8"));
	}

	@Test
	void esSeguraUnaClaveAlfanumericaConSimbolos() {
		assertDoesNotThrow(() -> PasswordValidator.validate("123Asd123.0?"));

	}

	@Test
	void testHashPassword(){
		Usuario usuario = new Usuario("JorgeLanata", "ensaladA10");
		assertTrue (BCrypt.checkpw("ensaladA10",usuario.getPassword()));
	}

}
