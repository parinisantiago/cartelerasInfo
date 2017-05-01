package rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import modelo.Rol;
import modelo.Usuario;
import modeloDAO.Dao;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class UsuarioREST extends GenericREST<Usuario, EntityJsonUsuario> {

	@Autowired
	private UsuarioDAO daoUsuario;

	@Autowired
	private RolDAO daoRol;
	
	@Override	
	protected Dao<Usuario> getEntityDao() {
		return daoUsuario;
	}

	@Override
	protected Usuario createEntity(EntityJsonUsuario jsonEntity) {
		Usuario entity = new Usuario();
		entity.setUser(jsonEntity.getUser());
		entity.setPassword(jsonEntity.getPassword());
		entity.setRol(daoRol.getById(jsonEntity.getRol_id()));
		entity.setHabilitado(true);
		return entity;
	}

	@Override
	protected Usuario updateEntity(Usuario entity, EntityJsonUsuario jsonEntity) {
		if(jsonEntity.getUser() != null){
			entity.setUser(jsonEntity.getUser());
		}
		if(jsonEntity.getPassword() != null){
			entity.setPassword(jsonEntity.getPassword());
		}
		
		if(jsonEntity.getRol_id() != null){
			Rol aux = daoRol.getById(jsonEntity.getRol_id());
			if(aux != null){
				entity.setRol(aux);
			}
		}
		
		return entity;
	}

	@Override
	protected Class<EntityJsonUsuario> getEntityJsonClass() {
		return EntityJsonUsuario.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonUsuario jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getUser() != null && 
				jsonEntity.getPassword() != null &&
				jsonEntity.getRol_id() != null &&
				daoRol.getById(jsonEntity.getRol_id()) != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonUsuario jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<Usuario> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<List<Usuario>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<Usuario> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/usuario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<Usuario> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<Usuario> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
}

final class EntityJsonUsuario extends EntityJsonAbstract{
	private String user;
	private String password;
	private Long rol_id;
	
	public EntityJsonUsuario() {
		super();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRol_id() {
		return rol_id;
	}

	public void setRol_id(Long rol_id) {
		this.rol_id = rol_id;
	}
	
	@Override
	public String toString() {
		return "EntityJsonUsuario [user=" + user + ", password=" + password + ", rol_id=" + rol_id + "]";
	}

}

