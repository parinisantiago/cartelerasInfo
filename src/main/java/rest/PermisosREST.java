package rest;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class PermisosREST {

	@Autowired
	private UsuarioDAO daoUsuario;
	
	@Autowired
	private CarteleraDAO daoCartelera;

	@GetMapping(value="/usuario/permisos/{id_usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<Usuario> permisosUsuario(@PathVariable("id_usuario") Long id){
		Usuario entity = daoUsuario.getById(id); 
		if( entity != null){
    		return new ResponseEntity<Usuario>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    	}
	}

	@GetMapping(value="/usuario/permisos", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<List<Usuario>> permisosTodos(){
		List<Usuario> entity = daoUsuario.selectAll();
		if( entity != null){
    		return new ResponseEntity<List<Usuario>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
    	}
	}

	@PostMapping(value="/usuario/permisos/{id_usuario}/cartelera/{id_cartelera}/publicar")
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<Usuario> addPermisoPublicarEnCartelera(@PathVariable("id_usuario") Long idUsuario, @PathVariable("id_cartelera") Long idCartelera){
		Usuario usuario = daoUsuario.getById(idUsuario);
		Cartelera cartelera = daoCartelera.getById(idCartelera);
		if(usuario != null && cartelera != null){
			cartelera.getUsuarioPublicar().add(usuario);
			daoCartelera.update(cartelera);
			usuario = daoUsuario.getById(idUsuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/usuario/permisos/{id_usuario}/cartelera/{id_cartelera}/eliminar")
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<Usuario> addPermisoEliminarEnCartelera(@PathVariable("id_usuario") Long idUsuario, @PathVariable("id_cartelera") Long idCartelera){
		Usuario usuario = daoUsuario.getById(idUsuario);
		Cartelera cartelera = daoCartelera.getById(idCartelera);
		if(usuario != null && cartelera != null){
			cartelera.getUsuarioEliminar().add(usuario);
			daoCartelera.update(cartelera);
			usuario = daoUsuario.getById(idUsuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/usuario/permisos/{id_usuario}/cartelera/{id_cartelera}/publicar")
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<Usuario> removePermisoPublicarEnCartelera(@PathVariable("id_usuario") Long idUsuario, @PathVariable("id_cartelera") Long idCartelera){
		Usuario usuario = daoUsuario.getById(idUsuario);
		Cartelera cartelera = daoCartelera.getById(idCartelera);
		if(usuario != null && cartelera != null){
			cartelera.getUsuarioPublicar().remove(usuario);
			daoCartelera.update(cartelera);
			usuario = daoUsuario.getById(idUsuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/usuario/permisos/{id_usuario}/cartelera/{id_cartelera}/eliminar")
	@JsonView(JView.UsuarioPermisos.class)
	public ResponseEntity<Usuario> removePermisoEliminarEnCartelera(@PathVariable("id_usuario") Long idUsuario, @PathVariable("id_cartelera") Long idCartelera){
		Usuario usuario = daoUsuario.getById(idUsuario);
		Cartelera cartelera = daoCartelera.getById(idCartelera);
		if(usuario != null && cartelera != null){
			cartelera.getUsuarioEliminar().remove(usuario);
			daoCartelera.update(cartelera);
			usuario = daoUsuario.getById(idUsuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	
}