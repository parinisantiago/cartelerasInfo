package rest;

import java.io.IOException;
import java.util.Date;
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

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class AnuncioREST {
	@Autowired
	private AnuncioDAO daoAnuncio;
	@Autowired
	private UsuarioDAO daoUsuario;
	@Autowired
	private CarteleraDAO daoCartelera;

    @RequestMapping(value="/anuncio/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Anuncio.class)
    public ResponseEntity<Anuncio> entityById(@PathVariable("id") Long id) {
    	Anuncio entity = daoAnuncio.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Anuncio>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Anuncio>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/anuncio", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Anuncio.class)
    public ResponseEntity<List<Anuncio>> entityAll() {
    	List<Anuncio>  entity = daoAnuncio.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Anuncio>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Anuncio>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/anuncio", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Anuncio.class)
    public ResponseEntity<Anuncio> entityCreate(@RequestBody String jsonString) {
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
    		Anuncio nuevo = new Anuncio();
    		nuevo.setTitulo(json.getTitulo());
    		nuevo.setCuerpo(json.getCuerpo());
    		nuevo.setFecha(json.getFecha());
    		nuevo.setComentarioHabilitado(json.isComentarioHabilitado());
    		nuevo.setCreador(daoUsuario.getById(json.getCreador_id()));
    		nuevo.setCartelera(daoCartelera.getById(json.getCartelera_id()));
    		nuevo.setHabilitado(true);
    		
    		daoAnuncio.persist(nuevo);
    		return new ResponseEntity<Anuncio>(nuevo, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Anuncio>(HttpStatus.NOT_FOUND);
    	}
    }
    
    private static class EntityJson{
    	private String titulo;
    	private String cuerpo;
    	private boolean comentarioHabilitado;
    	private Date fecha;
    	private Long creador_id;
    	private Long cartelera_id;
    	
    	public EntityJson(){}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getCuerpo() {
			return cuerpo;
		}

		public void setCuerpo(String cuerpo) {
			this.cuerpo = cuerpo;
		}

		public boolean isComentarioHabilitado() {
			return comentarioHabilitado;
		}

		public void setComentarioHabilitado(boolean comentarioHabilitado) {
			this.comentarioHabilitado = comentarioHabilitado;
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

		public Long getCartelera_id() {
			return cartelera_id;
		}

		public void setCartelera_id(Long cartelera_id) {
			this.cartelera_id = cartelera_id;
		}

		@Override
		public String toString() {
			return "EntityJson [titulo=" + titulo + ", cuerpo=" + cuerpo + ", comentarioHabilitado="
					+ comentarioHabilitado + ", fecha=" + fecha + ", creador_id=" + creador_id + ", cartelera_id="
					+ cartelera_id + "]";
		}
    	
    }
    
    @RequestMapping(value="/anuncio/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Anuncio> entityRemove(@PathVariable("id") Long id) {
    	Anuncio entity = daoAnuncio.getById(id);
    	if( entity != null ){
    		daoAnuncio.remove(entity);
    		return new ResponseEntity<Anuncio>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Anuncio>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/anuncio/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Anuncio.class)
    public ResponseEntity<Anuncio> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
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
		
    	Anuncio entity = daoAnuncio.getById(id);
    	if (json != null && entity!=null){
    		if(json.getFecha() != null){
    			entity.setFecha(json.getFecha());
    		}
    		if(json.getTitulo() != null){
    			entity.setTitulo(json.getTitulo());
    		}
    		if(json.getCuerpo() != null){
    			entity.setCuerpo(json.getCuerpo());
    		}
    		
    		entity.setComentarioHabilitado(json.isComentarioHabilitado());
    		
    		if(json.getCartelera_id() != null){
    			Cartelera c = daoCartelera.getById(json.getCartelera_id());
    			if(c!=null){
    				entity.setCartelera(c);
    			}
    		}
    		if(json.getCreador_id() != null){
    			Usuario us = daoUsuario.getById(json.getCreador_id());
    			if(us!=null){
    				entity.setCreador(us);
    			}
    		}
    		daoAnuncio.update(entity);
    		
    		return new ResponseEntity<Anuncio>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Anuncio>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
