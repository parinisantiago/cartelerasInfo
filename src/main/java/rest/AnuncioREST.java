package rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Notificacion;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.Dao;
import modeloDAO.NotificacionDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class AnuncioREST extends GenericREST<Anuncio, EntityJsonAnuncio> {

	@Autowired
	private AnuncioDAO daoAnuncio;
	
	@Autowired
	private NotificacionDAO daoNotificacion;
	
	@Autowired 
	private CarteleraDAO daoCartelera;
	
	@Autowired 
	private UsuarioDAO daoUsuario;
	
	@Override	
	protected Dao<Anuncio> getEntityDao() {
		return daoAnuncio;
	}

	@Override
	protected Anuncio createEntity(EntityJsonAnuncio jsonEntity) {
		Anuncio entity = new Anuncio();
		entity.setTitulo(jsonEntity.getTitulo());
		entity.setCuerpo( (jsonEntity.getCuerpo() != null?jsonEntity.getCuerpo():"") );
		entity.setComentarioHabilitado(true);
		entity.setCartelera(daoCartelera.getById(jsonEntity.getCartelera_id()));
		entity.setCreador(daoUsuario.getById(jsonEntity.getCreador_id()));
		entity.setHabilitado(jsonEntity.isComentarioHabilitado());
		return entity;
	}

	@Override
	protected Anuncio updateEntity(Anuncio entity, EntityJsonAnuncio jsonEntity) {
		if(jsonEntity.getTitulo() != null){
			entity.setTitulo(jsonEntity.getTitulo());
		}
		if(jsonEntity.getCuerpo() != null){
			entity.setCuerpo(jsonEntity.getCuerpo());
		}
		
		//entity.setComentarioHabilitado(jsonEntity.isComentarioHabilitado());
		
		if(jsonEntity.getCartelera_id() != null){
			Cartelera aux = daoCartelera.getById(jsonEntity.getCartelera_id());
			if(aux!=null){
				entity.setCartelera(aux);
			}
		}
		if(jsonEntity.getCreador_id() != null){
			Usuario aux = daoUsuario.getById(jsonEntity.getCreador_id());
			if(aux != null){
				entity.setCreador(aux);
			}
		}
		
		return entity;
	}

	@Override
	protected Class<EntityJsonAnuncio> getEntityJsonClass() {
		return EntityJsonAnuncio.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonAnuncio jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getTitulo() != null && 
				jsonEntity.getCreador_id() != null &&
				jsonEntity.getCartelera_id() != null &&
				daoCartelera.getById(jsonEntity.getCartelera_id()) != null &&
				daoUsuario.getById(jsonEntity.getCreador_id()) != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonAnuncio jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/anuncio/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Anuncio.class)
	public ResponseEntity<Anuncio> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}
	
	@GetMapping(value="/anuncio/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Anuncio.class)
	public ResponseEntity<List<Anuncio>> entityByUser(@PathVariable("id") Long id) {
		Usuario user = daoUsuario.getById(id);
		if(user != null){
			List<Anuncio>  entity = user.getMisAnuncios();
	    	if( entity != null){
	    		return new ResponseEntity<List<Anuncio>>(entity, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<List<Anuncio>>(HttpStatus.NO_CONTENT);
	    	}
		}
		else{
			return new ResponseEntity<List<Anuncio>>(HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@GetMapping(value="/anuncio", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Anuncio.class)
	public ResponseEntity<List<Anuncio>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/anuncio", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Anuncio.class)
	public ResponseEntity<Anuncio> entityCreate(@RequestBody String jsonString) {
		Anuncio entity = null;
    	EntityJsonAnuncio parsedEntity = mapFromJson(jsonString);
    	if(isValidJsonEntityToCreate(parsedEntity)){
    		entity = createEntity(parsedEntity);
    		getEntityDao().persist(entity);
    		
    		//agrego notificacion para cada usuario interesado
    		for (Usuario usuarioCartelera : entity.getCartelera().getInteresados()) {
    			Notificacion notificacion = new Notificacion("Se ha publicado un nuevo anuncio en la cartelera " + entity.getCartelera().getTitulo() + ": " + entity.getTitulo());
    			Usuario usuario = daoUsuario.getById(usuarioCartelera.getId());
    			notificacion.setUsuario(usuario);
    			daoNotificacion.persist(notificacion);
    		}
    		
    		return new ResponseEntity<Anuncio>(entity, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Anuncio>(HttpStatus.NOT_FOUND);
    	}
    	
    	//return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/anuncio/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Anuncio.class)
	public ResponseEntity<Anuncio> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/anuncio/{id}")
	public ResponseEntity<Anuncio> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
	
}

final class EntityJsonAnuncio extends EntityJsonAbstract{
	private String titulo;
	private String cuerpo;
	private boolean comentarioHabilitado;
	private Long creador_id;
	private Long cartelera_id;
	
	public EntityJsonAnuncio() {
		super();
	}
	
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
		return "EntityJsonAnuncio [titulo=" + titulo + ", cuerpo=" + cuerpo + ", comentarioHabilitado="
				 + ", creador_id=" + creador_id + ", cartelera_id="
				+ cartelera_id + "]";
	}
	
}
