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

import modelo.Rol;
import modeloDAO.RolDAO;
import modeloDAO.Dao;

@RestController
public class RolREST extends GenericREST<Rol, EntityJsonRol> {

	@Autowired
	private RolDAO daoRol;
	
	@Override	
	protected Dao<Rol> getEntityDao() {
		return daoRol;
	}

	@Override
	protected Rol createEntity(EntityJsonRol jsonEntity) {
		Rol entity = new Rol();
		entity.setNombre(jsonEntity.getNombre());
		return entity;
	}

	@Override
	protected Rol updateEntity(Rol entity, EntityJsonRol jsonEntity) {
		if(jsonEntity.getNombre() != null){
			entity.setNombre(jsonEntity.getNombre());
		}
		return entity;
	}

	@Override
	protected Class<EntityJsonRol> getEntityJsonClass() {
		return EntityJsonRol.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonRol jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getNombre() != null && 
				daoRol.getByNombre(jsonEntity.getNombre()) == null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonRol jsonEntity) {
		return (jsonEntity != null && 
				daoRol.getByNombre(jsonEntity.getNombre()) == null);
	}

	@Override
	@GetMapping(value="/rol/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Rol.class)
	public ResponseEntity<Rol> entityById(Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/rol", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Rol.class)
	public ResponseEntity<List<Rol>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/rol/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Rol.class)
	public ResponseEntity<Rol> entityCreate(String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/rol/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Rol.class)
	public ResponseEntity<Rol> entityUpdate(Long id, String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/rol/{id}")
	public ResponseEntity<Rol> entityRemove(Long id) {
		return super.entityRemove(id);
	}
	
	
}

final class EntityJsonRol extends EntityJsonAbstract{
	private String nombre;
	
	public EntityJsonRol() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "EntityJsonRol [nombre=" + nombre + "]";
	}

}
