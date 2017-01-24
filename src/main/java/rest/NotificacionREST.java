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

import modelo.Notificacion;
import modelo.Usuario;
import modeloDAO.NotificacionDAO;
import modeloDAO.Dao;
import modeloDAO.UsuarioDAO;

@RestController
public class NotificacionREST extends GenericREST<Notificacion, EntityJsonNotificacion> {

	@Autowired
	private NotificacionDAO daoNotificacion;
	
	@Autowired 
	private UsuarioDAO daoUsuario;
	
	@Override	
	protected Dao<Notificacion> getEntityDao() {
		return daoNotificacion;
	}

	@Override
	protected Notificacion createEntity(EntityJsonNotificacion jsonEntity) {
		Notificacion entity = new Notificacion();
		entity.setDescripcion(jsonEntity.getDescripcion());
		entity.setUsuario(daoUsuario.getById(jsonEntity.getUsuario_id()));
		return entity;
	}

	@Override
	protected Notificacion updateEntity(Notificacion entity, EntityJsonNotificacion jsonEntity) {
		if(jsonEntity.getDescripcion() != null){
			entity.setDescripcion(jsonEntity.getDescripcion());
		}
		if(jsonEntity.getUsuario_id() != null){
			Usuario aux = daoUsuario.getById(jsonEntity.getUsuario_id());
			if(aux != null){
				entity.setUsuario(aux);
			}
		}
		
		return entity;
	}

	@Override
	protected Class<EntityJsonNotificacion> getEntityJsonClass() {
		return EntityJsonNotificacion.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonNotificacion jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getDescripcion() != null && 
				jsonEntity.getUsuario_id() != null &&
				daoUsuario.getById(jsonEntity.getUsuario_id()) != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonNotificacion jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/notificacion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Notificacion.class)
	public ResponseEntity<Notificacion> entityById(Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/notificacion", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Notificacion.class)
	public ResponseEntity<List<Notificacion>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/notificacion/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Notificacion.class)
	public ResponseEntity<Notificacion> entityCreate(String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/notificacion/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Notificacion.class)
	public ResponseEntity<Notificacion> entityUpdate(Long id, String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/notificacion/{id}")
	public ResponseEntity<Notificacion> entityRemove(Long id) {
		return super.entityRemove(id);
	}
	
	
}

final class EntityJsonNotificacion extends EntityJsonAbstract{
	private String descripcion;
	private Long usuario_id;
	
	public EntityJsonNotificacion() {
		super();
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

	@Override
	public String toString() {
		return "EntityJsonNotificacion [descripcion=" + descripcion + ", usuario_id=" + usuario_id + "]";
	}
	
}
