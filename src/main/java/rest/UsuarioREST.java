package rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Cartelera;
import modelo.Rol;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;
import modeloDAO.Dao;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class UsuarioREST extends GenericREST<Usuario, EntityJsonUsuario> {

	@Autowired
	private UsuarioDAO daoUsuario;

	@Autowired
	private RolDAO daoRol;
	
	@Autowired
	private CarteleraDAO daoCartelera;
	
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
		
		if(jsonEntity.getRol_id() != null && jsonEntity.getRol_id() != entity.getRol().getId()){
			Rol aux = daoRol.getById(jsonEntity.getRol_id());
			if(aux != null){
				entity.setRol(aux);
			}
		}
		
		if(jsonEntity.getCartelerasModificar_id() != null){
			//borrar
			Set<Cartelera> cartelerasABorrar= new HashSet<>();
			for (Cartelera cartelera : entity.getCartelerasModificar()) {
				//si las carteleras viejas no estan en el nuevo arreglo, osea las que hay que borrar
				if(! jsonEntity.getCartelerasModificar_id().contains(cartelera.getId()) ){
					//la borro despues para que no tire error al estar recorriendo esta misma coleccion
					cartelerasABorrar.add(cartelera);
				}
				//saco las carteleras viejas y las borradas, dejando solo las que hay que agregar
				jsonEntity.getCartelerasModificar_id().remove(cartelera.getId());
			}
			//las borro del usuario
			entity.getCartelerasModificar().removeAll(cartelerasABorrar);
			//agregar
			for (Long cartelera_id : jsonEntity.getCartelerasModificar_id()) {
				Cartelera cartelera = daoCartelera.getById(cartelera_id);
				if(cartelera!=null){
					entity.getCartelerasModificar().add(cartelera);
				}
			}	
		}
		
		if(jsonEntity.getCartelerasEliminar_id() != null){
			//borrar
			Set<Cartelera> cartelerasABorrar= new HashSet<>();
			for (Cartelera cartelera : entity.getCartelerasEliminar()) {
				//si las carteleras viejas no estan en el nuevo arreglo, osea las que hay que borrar
				if(! jsonEntity.getCartelerasEliminar_id().contains(cartelera.getId()) ){
					//la borro despues para que no tire error al estar recorriendo esta misma coleccion
					cartelerasABorrar.add(cartelera);
					
				}
				//saco las carteleras viejas, dejando solo las que hay que agregar
				jsonEntity.getCartelerasEliminar_id().remove(cartelera.getId());
			}
			//las borro del usuario
			entity.getCartelerasEliminar().removeAll(cartelerasABorrar);
			//agregar
			for (Long cartelera_id : jsonEntity.getCartelerasEliminar_id()) {
				Cartelera cartelera = daoCartelera.getById(cartelera_id);
				if(cartelera!=null){
					entity.getCartelerasEliminar().add(cartelera);
				}
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
	
	@PostMapping(value="/usuario/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}
	
	@PutMapping(value="/usuario/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Usuario.class)
	public ResponseEntity<Usuario> cambiarPasswordUsuario(@PathVariable("id") Long id, @RequestBody String jsonString) {
		Usuario entity = daoUsuario.getById(id);
    	
		EntityJsonPassword parsedEntity = null;
		try {
			parsedEntity = new ObjectMapper().readValue(jsonString, EntityJsonPassword.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	if(entity!= null && parsedEntity!= null && parsedEntity.getPassword_old() != "" && parsedEntity.getPassword_new() != ""){
    		if( entity.getPassword().equals(parsedEntity.getPassword_old()) ){
	    		entity.setPassword(parsedEntity.getPassword_new());
	    		daoUsuario.update(entity);
	    		return new ResponseEntity<>(HttpStatus.OK);
    		}
    		else{
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    	}
    	else{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
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

/** JSON (los campos pueden dejarse en vacío):
{
"user":"agus",
"password":"password",
"rol_id":2,
"cartelerasEliminar_id":[11,10],
"cartelerasModificar_id":[10,11,12]
}
**/
final class EntityJsonUsuario extends EntityJsonAbstract{
	private String user;
	private String password;
	private Long rol_id;
	private List<Long> cartelerasModificar_id;
	private List<Long> cartelerasEliminar_id;
	
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

	public List<Long> getCartelerasModificar_id() {
		return cartelerasModificar_id;
	}

	public void setCartelerasModificar_id(List<Long> cartelerasModificar_id) {
		this.cartelerasModificar_id = cartelerasModificar_id;
	}

	public List<Long> getCartelerasEliminar_id() {
		return cartelerasEliminar_id;
	}

	public void setCartelerasEliminar_id(List<Long> cartelerasEliminar_id) {
		this.cartelerasEliminar_id = cartelerasEliminar_id;
	}
	
	@Override
	public String toString() {
		return "EntityJsonUsuario [user=" + user + ", password=" + password + ", rol_id=" + rol_id + "]";
	}

}

final class EntityJsonPassword extends EntityJsonAbstract{
	private String password_old;
	private String password_new;
	
	public EntityJsonPassword() {
		super();
	}
	
	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	@Override
	public String toString() {
		return "EntityJsonPassword [password_old=" + password_old + ", password_new=" + password_new + "]";
	}

}

