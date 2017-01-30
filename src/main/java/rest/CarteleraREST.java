package rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Cartelera;
import modeloDAO.CarteleraDAO;
import modeloDAO.Dao;

@RestController
public class CarteleraREST extends GenericREST<Cartelera, EntityJsonCartelera> {

	@Autowired
	private CarteleraDAO daoCartelera;
	
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
	public ResponseEntity<Cartelera> entityById(Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/cartelera", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.CarteleraCompleta.class)
	public ResponseEntity<List<Cartelera>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/cartelera/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Cartelera.class)
	public ResponseEntity<Cartelera> entityCreate(String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/cartelera/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Cartelera.class)
	public ResponseEntity<Cartelera> entityUpdate(Long id, String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/cartelera/{id}")
	public ResponseEntity<Cartelera> entityRemove(Long id) {
		return super.entityRemove(id);
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
