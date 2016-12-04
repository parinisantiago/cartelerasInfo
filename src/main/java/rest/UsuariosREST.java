package rest;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;
import modeloDAOJPA.UsuarioJpaDAO;

@RestController
public class UsuariosREST {
	
	private UsuarioDAO dao = new UsuarioJpaDAO();
	
    @RequestMapping(value="/usuario/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> usuarioById(@PathVariable("id") Long id) {
    	Usuario user = dao.getById(id);
    	if(Objects.nonNull(user)){
    		return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value="/usuarios", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> listAll() {
    	List<Usuario> users = dao.selectAll();
    	if(Objects.nonNull(users) && users.size() > 0){
    		return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
    }
}
