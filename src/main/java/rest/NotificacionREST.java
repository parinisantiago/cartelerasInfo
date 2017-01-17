package rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Notificacion;
import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.NotificacionDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class NotificacionREST {
	@Autowired
	private NotificacionDAO daoNotificacion;
	@Autowired
	private UsuarioDAO daoUsuario;
	@Autowired
	private CarteleraDAO daoCartelera;

	
    @RequestMapping(value="/notificacion/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Notificacion.class)
    public ResponseEntity<Notificacion> entityById(@PathVariable("id") Long id) {
    	Notificacion entity = daoNotificacion.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Notificacion>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Notificacion>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/notificacion", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Notificacion.class)
    public ResponseEntity<List<Notificacion>> entityAll() {
    	List<Notificacion>  entity = daoNotificacion.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Notificacion>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Notificacion>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/notificacion", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Notificacion.class)
    public ResponseEntity<Notificacion> entityCreate(@RequestBody String jsonString) {
    	EntityJson json = null;
		try {
			json = new ObjectMapper().readValue(jsonString, EntityJson.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if( json != null ){
    		Notificacion nuevo = new Notificacion();
    		nuevo.setDescripcion(json.getDescripcion());
    		nuevo.setUsuario(daoUsuario.getById(json.getUsuario_id()));
    		
    		daoNotificacion.persist(nuevo);
    		return new ResponseEntity<Notificacion>(nuevo, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Notificacion>(HttpStatus.NOT_FOUND);
    	}
    }
    
    private static class EntityJson{
    	private String descripcion;
    	private Long usuario_id;
    	
		public EntityJson(){}
    	
    	@Override
		public String toString() {
			return "EntityJson [descripcion=" + descripcion + ", usuario_id=" + usuario_id + "]";
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Long getUsuario_id() {
			return usuario_id;
		}

		public void setUsuario_id(Long usuario_id) {
			this.usuario_id = usuario_id;
		}

    }
    
    @RequestMapping(value="/notificacion/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Notificacion> entityRemove(@PathVariable("id") Long id) {
    	Notificacion entity = daoNotificacion.getById(id);
    	if( entity != null ){
    		daoNotificacion.remove(entity);
    		return new ResponseEntity<Notificacion>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Notificacion>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/notificacion/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Notificacion.class)
    public ResponseEntity<Notificacion> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
    	EntityJson json = null;
		try {
			json = new ObjectMapper().readValue(jsonString, EntityJson.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	Notificacion entity = daoNotificacion.getById(id);
    	if (json != null && entity!=null){
    		if(json.getDescripcion() != null){
    			entity.setDescripcion(json.getDescripcion());
    		}
    		if(json.getUsuario_id() != null){
    			Usuario us = daoUsuario.getById(json.getUsuario_id());
    			if(us!=null){
    				entity.setUsuario(us);
    			}
    		}
    		daoNotificacion.update(entity);
    		
    		return new ResponseEntity<Notificacion>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Notificacion>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
