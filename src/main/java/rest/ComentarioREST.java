package rest;

import java.util.Date;
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

import modelo.Comentario;
import modelo.Anuncio;
import modelo.Usuario;
import modeloDAO.ComentarioDAO;
import modeloDAO.AnuncioDAO;
import modeloDAO.Dao;
import modeloDAO.UsuarioDAO;

@RestController
public class ComentarioREST extends GenericREST<Comentario, EntityJsonComentario> {

	@Autowired
	private ComentarioDAO daoComentario;
	@Autowired
	private AnuncioDAO daoAnuncio;
	@Autowired
	private UsuarioDAO daoUsuario;
	
	@Override	
	protected Dao<Comentario> getEntityDao() {
		return daoComentario;
	}

	@Override
	protected Comentario createEntity(EntityJsonComentario jsonEntity) {
		Comentario entity = new Comentario();
		entity.setTexto( (jsonEntity.getTexto() != null?jsonEntity.getTexto():"") );
		entity.setAnuncio(daoAnuncio.getById(jsonEntity.getAnuncio_id()));
		entity.setCreador(daoUsuario.getById(jsonEntity.getCreador_id()));
		entity.setFecha( (jsonEntity.getFecha() != null?jsonEntity.getFecha():new Date()) );
		entity.setHabilitado(true);
		return entity;
	}

	@Override
	protected Comentario updateEntity(Comentario entity, EntityJsonComentario jsonEntity) {
		if(jsonEntity.getFecha() != null){
			entity.setFecha(jsonEntity.getFecha());
		}
		if(jsonEntity.getTexto() != null){
			entity.setTexto(jsonEntity.getTexto());
		}
		
		if(jsonEntity.getAnuncio_id() != null){
			Anuncio aux = daoAnuncio.getById(jsonEntity.getAnuncio_id());
			if(aux!=null){
				entity.setAnuncio(aux);
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
	protected Class<EntityJsonComentario> getEntityJsonClass() {
		return EntityJsonComentario.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonComentario jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getAnuncio_id() != null &&
				jsonEntity.getCreador_id() != null &&
				daoAnuncio.getById(jsonEntity.getAnuncio_id()) != null &&
				daoUsuario.getById(jsonEntity.getCreador_id()) != null &&
				jsonEntity.getTexto() != null &&
				jsonEntity.getFecha() != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonComentario jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/comentario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Comentario.class)
	public ResponseEntity<Comentario> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/comentario", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Comentario.class)
	public ResponseEntity<List<Comentario>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/comentario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Comentario.class)
	public ResponseEntity<Comentario> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/comentario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Comentario.class)
	public ResponseEntity<Comentario> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/comentario/{id}")
	public ResponseEntity<Comentario> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
	
}

final class EntityJsonComentario extends EntityJsonAbstract{
	private String texto;
	private Date fecha;
	private Long creador_id;
	private Long anuncio_id;
	
	public EntityJsonComentario() {
		super();
	}

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
		return "EntityJsonComentario [texto=" + texto + ", fecha=" + fecha + ", creador_id=" + creador_id
				+ ", anuncio_id=" + anuncio_id + "]";
	}
	
}
