package rest;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;
import tokenJWT.LoginService;
import tokenJWT.TokenJWT;
import tokenJWT.TokenJWTManager;

@RestController
public class LoginRest {
	
	// necesario para pruebas de login
	@Autowired
	private LoginService loginService;

	@Autowired
	private TokenJWTManager tokenManagerSecurity;

	@Autowired
	private UsuarioDAO usuarioDao;

	private static class EntityJsonLogin{
    	private String user;
    	private String password;
    	
    	public EntityJsonLogin() {}
    	
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		@Override
		public String toString() {
			return "EntityJsonLogin [user=" + user + ", password=" + password + "]";
		}
	}
		    	
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody String jsonString) {
		try {
			//parseo lo enviado en el post
			EntityJsonLogin userPost = new ObjectMapper().readValue(jsonString, EntityJsonLogin.class);
			
			//chequeo el login (login service se encarga de buscar el usuario por nombre y comparar la contraseña
			Usuario user = loginService.login(userPost.getUser(), userPost.getPassword());
			
			//si todo salio bien, se crea el token y se envia
			TokenJWT token = new TokenJWT(tokenManagerSecurity.createJWT(user));
			
			return ResponseEntity.ok("{\"status\":\"ok\", \"token\":\""+token.toString()+"\"}");
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("status",e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping(value="/login/refresh/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> refresh(@PathVariable("id") Long id) {
		try {
			
			//busco usuario
			Usuario user = usuarioDao.getById(id);
			
			//si todo salio bien, se crea el token y se envia
			TokenJWT token = new TokenJWT(tokenManagerSecurity.createJWT(user));
			
			return ResponseEntity.ok("{\"status\":\"ok\", \"token\":\""+token.toString()+"\"}");
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("status",e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}
