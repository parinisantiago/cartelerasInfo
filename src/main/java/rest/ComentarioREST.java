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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Comentario;
import modelo.Usuario;
import modeloDAO.ComentarioDAO;

@RestController
public class ComentarioREST {
	@Autowired
	private ComentarioDAO dao;
	
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityById(@PathVariable("id") Long id) {
    	Comentario entity = dao.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Comentario>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<List<Comentario>> entityAll() {
    	List<Comentario>  entity = dao.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Comentario>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Comentario>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityCreate(@RequestBody Comentario json) {
    	System.out.println("JSONREQ: "+json);
		
    	if( json != null && json.getId() == null ){
    		dao.persist(json);
    		return new ResponseEntity<Comentario>(json, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Comentario> entityRemove(@PathVariable("id") Long id) {
    	Comentario entity = dao.getById(id);
    	if( entity != null ){
    		dao.remove(entity);
    		return new ResponseEntity<Comentario>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityUpdate(@RequestBody Comentario json) {
    	System.out.println("JSONREQ: "+json);
    	
    	if (json != null && json.getId() != null){
    		Comentario entity = dao.getById(json.getId());
    		if(json.getFecha() != null){
    			entity.setFecha(json.getFecha());
    		}
    		if(json.getTexto() != null){
    			entity.setTexto(json.getTexto());
    		}
    		if(json.getAnuncio() != null){
    			entity.setAnuncio(json.getAnuncio());
    		}
    		System.out.println("ENTITY_UPD: "+entity);
    		dao.update(entity);
    		
    		return new ResponseEntity<Comentario>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
