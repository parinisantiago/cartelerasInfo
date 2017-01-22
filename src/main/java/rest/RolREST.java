package rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Rol;
import modeloDAO.RolDAO;

@RestController
public class RolREST {
	@Autowired
	private RolDAO daoRol;

    @RequestMapping(value="/rol/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Rol.class)
    public ResponseEntity<Rol> entityById(@PathVariable("id") Long id) {
    	Rol entity = daoRol.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Rol>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Rol>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/rol", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Rol.class)
    public ResponseEntity<List<Rol>> entityAll() {
    	List<Rol>  entity = daoRol.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Rol>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Rol>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/rol", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Rol.class)
    public ResponseEntity<Rol> entityCreate(@RequestBody String jsonString) {
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
    		Rol nuevo = new Rol();
    		nuevo.setNombre(json.getNombre());
    		
    		daoRol.persist(nuevo);
    		return new ResponseEntity<Rol>(nuevo, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Rol>(HttpStatus.NOT_FOUND);
    	}
    }
    
    private static class EntityJson{
    	private String nombre;
    	
		public EntityJson(){}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			return "EntityJson [nombre=" + nombre + "]";
		}

    }
    
    @RequestMapping(value="/rol/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Rol> entityRemove(@PathVariable("id") Long id) {
    	Rol entity = daoRol.getById(id);
    	if( entity != null ){
    		daoRol.remove(entity);
    		return new ResponseEntity<Rol>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Rol>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/rol/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Rol.class)
    public ResponseEntity<Rol> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
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
		
    	Rol entity = daoRol.getById(id);
    	if (json != null && entity!=null){
    		if(json.getNombre() != null){
    			entity.setNombre(json.getNombre());
    		}
    		
    		daoRol.update(entity);
    		
    		return new ResponseEntity<Rol>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Rol>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
