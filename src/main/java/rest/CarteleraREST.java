package rest;

import java.io.IOException;
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

import modelo.Cartelera;
import modeloDAO.CarteleraDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class CarteleraREST {
	@Autowired
	private CarteleraDAO daoCartelera;

	
    @RequestMapping(value="/cartelera/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Cartelera.class)
    public ResponseEntity<Cartelera> entityById(@PathVariable("id") Long id) {
    	Cartelera entity = daoCartelera.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Cartelera>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/cartelera", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Cartelera.class)
    public ResponseEntity<List<Cartelera>> entityAll() {
    	List<Cartelera>  entity = daoCartelera.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Cartelera>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Cartelera>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/cartelera", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Cartelera.class)
    public ResponseEntity<Cartelera> entityCreate(@RequestBody String jsonString) {
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
    		Cartelera nuevo = new Cartelera();
    		nuevo.setTitulo(json.getTitulo());
    		nuevo.setHabilitado(true);
    		
    		daoCartelera.persist(nuevo);
    		return new ResponseEntity<Cartelera>(nuevo, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
    }
    
    private static class EntityJson{
    	private String titulo;
    	    	
    	public EntityJson(){}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		@Override
		public String toString() {
			return "EntityJson [titulo=" + titulo + "]";
		}
    	
    }
    
    @RequestMapping(value="/cartelera/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Cartelera> entityRemove(@PathVariable("id") Long id) {
    	Cartelera entity = daoCartelera.getById(id);
    	if( entity != null ){
    		daoCartelera.remove(entity);
    		return new ResponseEntity<Cartelera>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/cartelera/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Cartelera.class)
    public ResponseEntity<Cartelera> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
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
		
    	Cartelera entity = daoCartelera.getById(id);
    	if (json != null && entity!=null){
    		if(json.getTitulo() != null){
    			entity.setTitulo(json.getTitulo());
    		}
    		daoCartelera.update(entity);
    		
    		return new ResponseEntity<Cartelera>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
