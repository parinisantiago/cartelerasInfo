package rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

import modelo.Anuncio;
import modelo.Comentario;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.ComentarioDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class ComentarioREST {
	@Autowired
	private ComentarioDAO daoComentario;
	@Autowired
	private AnuncioDAO daoAnuncio;
	@Autowired
	private UsuarioDAO daoUsuario;
	
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityById(@PathVariable("id") Long id) {
    	Comentario entity = daoComentario.getById(id);
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
    	List<Comentario>  entity = daoComentario.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Comentario>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Comentario>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityCreate(@RequestBody String jsonString) {
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
    	System.out.println("JSONREQ: "+json);
    	
    	if( json != null ){
    		Comentario nuevo = new Comentario();
    		nuevo.setTexto(json.getTexto());
    		nuevo.setFecha(json.getFecha());
    		nuevo.setAnuncio(daoAnuncio.getById(json.getAnuncio_id()));
    		nuevo.setCreador(daoUsuario.getById(json.getCreador_id()));
    		nuevo.setHabilitado(true);
    		
    		daoComentario.persist(nuevo);
    		return new ResponseEntity<Comentario>(nuevo, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    private static class EntityJson{
    	private Long anuncio_id; 
    	private String texto;
    	private Date fecha;
    	private Long creador_id;
    	
    	public EntityJson(){}
    	
		public Long getAnuncio_id() {
			return anuncio_id;
		}
		public void setAnuncio_id(Long anuncio_id) {
			this.anuncio_id = anuncio_id;
		}
		public String getTexto() {
			return texto;
		}
		public void setTexto(String texto) {
			this.texto = texto;
		}
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		public Long getCreador_id() {
			return creador_id;
		}
		public void setCreador_id(Long creador_id) {
			this.creador_id = creador_id;
		}
		@Override
		public String toString() {
			return "EntityCreateJson [anuncio_id=" + anuncio_id + ", texto=" 
					+ texto + ", fecha=" + fecha + ", creador_id=" + creador_id + "]";
		}
    	
    }
    
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Comentario> entityRemove(@PathVariable("id") Long id) {
    	Comentario entity = daoComentario.getById(id);
    	if( entity != null ){
    		daoComentario.remove(entity);
    		return new ResponseEntity<Comentario>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
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
    	System.out.println("JSONREQ: "+json);
    	Comentario entity = daoComentario.getById(id);
    	if (json != null && entity!=null){
    		if(json.getFecha() != null){
    			entity.setFecha(json.getFecha());
    		}
    		if(json.getTexto() != null){
    			entity.setTexto(json.getTexto());
    		}
    		if(json.getAnuncio_id() != null){
    			Anuncio an = daoAnuncio.getById(json.getAnuncio_id());
    			if(an!=null){
    				entity.setAnuncio(an);
    			}
    		}
    		if(json.getCreador_id() != null){
    			Usuario us = daoUsuario.getById(json.getCreador_id());
    			if(us!=null){
    				entity.setCreador(us);
    			}
    		}
    		System.out.println("ENTITY_UPD: "+entity);
    		daoComentario.update(entity);
    		
    		return new ResponseEntity<Comentario>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
