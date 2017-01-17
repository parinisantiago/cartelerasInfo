package rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;

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
}
