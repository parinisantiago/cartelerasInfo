package rest;

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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonView;

import ch.qos.logback.core.joran.action.ActionUtil.Scope;
import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;
import modeloDAO.Dao;
import modeloDAO.UsuarioDAO;

@RestController
public class CarteleraREST extends GenericREST<Cartelera, EntityJsonCartelera> {

	@Autowired
	private CarteleraDAO daoCartelera;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override	
	protected Dao<Cartelera> getEntityDao() {
		return daoCartelera;
	}

	@Override
	protected Cartelera createEntity(EntityJsonCartelera jsonEntity) {
		Cartelera entity = new Cartelera();
		entity.setTitulo(jsonEntity.getTitulo());
		entity.setHabilitado(true);
		return entity;
	}

	@Override
	protected Cartelera updateEntity(Cartelera entity, EntityJsonCartelera jsonEntity) {
		if (jsonEntity.getTitulo() != null) {
			entity.setTitulo(jsonEntity.getTitulo());

		}
		return entity;
	}

	@Override
	protected Class<EntityJsonCartelera> getEntityJsonClass() {
		return EntityJsonCartelera.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonCartelera jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getTitulo() != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonCartelera jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/cartelera/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.CarteleraCompleta.class)
	public ResponseEntity<Cartelera> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/cartelera", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.CarteleraCompleta.class)
	public ResponseEntity<List<Cartelera>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/cartelera", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Cartelera.class)
	public ResponseEntity<Cartelera> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/cartelera/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Cartelera.class)
	public ResponseEntity<Cartelera> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/cartelera/{id}")
	public ResponseEntity<Cartelera> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
	@PutMapping(value="/cartelera/{id}/interes")
	@JsonView(JView.CarteleraCompleta.class)
	public ResponseEntity<Cartelera> addInteres(@PathVariable("id") Long id) {
		Cartelera entity = getEntityDao().getById(id);
		long idUsuario = (long) RequestContextHolder.getRequestAttributes().getAttribute("userID", RequestAttributes.SCOPE_REQUEST);
    	Usuario user = usuarioDAO.getById(idUsuario);
    	if(entity!= null ){
    		entity.addInteresado(user);
    		getEntityDao().update(entity);
    		return new ResponseEntity<Cartelera>(entity,HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
	}
	
	@DeleteMapping(value="/cartelera/{id}/interes")
	@JsonView(JView.CarteleraCompleta.class)
	public ResponseEntity<Cartelera> removeInteres(@PathVariable("id") Long id) {
		Cartelera entity = getEntityDao().getById(id);
		long idUsuario = (long) RequestContextHolder.getRequestAttributes().getAttribute("userID", RequestAttributes.SCOPE_REQUEST);
    	Usuario user = usuarioDAO.getById(idUsuario);
    	if(entity!= null ){
    		entity.removeInteresado(user);
    		getEntityDao().update(entity);
    		return new ResponseEntity<Cartelera>(entity,HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Cartelera>(HttpStatus.NOT_FOUND);
    	}
	}
	
}

final class EntityJsonCartelera extends EntityJsonAbstract{
	private String titulo;
	
	public EntityJsonCartelera() {
		super();
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Override
	public String toString() {
		return "EntityJsonCartelera [titulo=" + titulo + "]";
	}
	
}
