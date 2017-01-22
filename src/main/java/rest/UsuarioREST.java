package rest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;
import tokenJWT.LoginService;
import tokenJWT.TokenJWT;
import tokenJWT.TokenJWTManager;

@RestController
public class UsuarioREST {
	@Autowired
	private UsuarioDAO daoUsuario;
	
    @RequestMapping(value="/usuario/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(JView.Usuario.class)
    public ResponseEntity<Usuario> usuarioById(@PathVariable("id") Long id) {
    	Usuario user = daoUsuario.getById(id);
    	if( user != null){
    		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/usuario", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(JView.Usuario.class)
    public ResponseEntity<List<Usuario>> listAll() {
    	List<Usuario> users = daoUsuario.selectAll();
    	System.out.println(users.toString());
    	if( users!= null && users.size() > 0){
    		return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
    	}
    }
    
//necesario para pruebas de login 
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TokenJWTManager tokenManagerSecurity;

	private static class EntityJson{
	    	private String user;
	    	private String password;
	    	
	    	public EntityJson() {}
	    	
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
				return "EntityJson [user=" + user + ", password=" + password + "]";
			}
	    	
	}

    @RequestMapping(value="/login", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(JView.Usuario.class)
	public ResponseEntity<?> login(@RequestBody String jsonString) {
		try {
			//parseo lo enviado en el post
			ObjectMapper objMap = new ObjectMapper();
			EntityJson userPost = objMap.readValue(jsonString, EntityJson.class);
			
			//chequeo el login (login service se encarga de buscar el usuario por nombre y comparar la contraseña
			Usuario user = loginService.login(userPost.getUser(), userPost.getPassword());
			
			//si todo salio bien, se crea el token y se envia
			TokenJWT token = new TokenJWT(tokenManagerSecurity.createJWT(user));
			return ResponseEntity.ok(token.toString());
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}
