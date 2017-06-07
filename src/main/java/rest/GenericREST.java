package rest;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modeloDAO.Dao;

/**
 * Esta clase se puede extender para generar las CRUD de un controllador
 * 
 * Si se usa anotaciones deben extenderse los métodos
 * entityById(id), entityAll(), entityCreate(), entityUpdate(id,json), entityRemove(id)
 * solo usando super(id), y agregandole las anotaciones.
 * 
 * @author Agustin
 * @param <T> entidad principal de las CRUD (una del modelo)
 * @param <E> clase a la que parcear el json recibido 
 */
public abstract class GenericREST<T, E extends EntityJsonAbstract> {
	
	//cosas abstractas a implementar
	
	/**
	 * Retorna el dao con las operaciones de la entidad principal
	 * @return Dao<T>
	 */
	protected abstract Dao<T> getEntityDao();
	
	/**
	 * Crea la entidad a partir de un json parseado
	 * @param jsonEntity in
	 * @return entidad creada
	 */
	protected abstract T createEntity(E jsonEntity);
	
	/**
	 * Actualiza la entidad con la informacion del json parseado.
	 * Ya se verificó que la entidad no es nula y el json es valido
	 * @param entity entidad a ser modificada
	 * @param jsonEntity json parseado
	 * @return entidad modificada
	 */
	protected abstract T updateEntity(T entity, E jsonEntity);
	
	/**
	 * @return clase con la que será parseado el json de los metodos create y update
	 */
	protected abstract Class<E> getEntityJsonClass();
	
	/**
	 * Verifica si los datos de la entidad json son validos y suficientes para
	 * crear la entidad principal. Por ejemplo, si no es null.
	 * @param jsonEntity json parseado 
	 * @return boolean
	 */
	protected abstract boolean isValidJsonEntityToCreate(E jsonEntity);
	
	/**
	 * Verifica si los datos de la entidad json son validos y suficientes para
	 * modificar la entidad principal. Por ejemplo, si no es null.
	 * @param jsonEntity json parseado 
	 * @return boolean
	 */
	protected abstract boolean isValidJsonEntityToUpdate(E jsonEntity);
	
	/**
	 * Parsea el json a la clase especificada por @method getEntityJsonClass()
	 * @param jsonString String json a parsear
	 * @return json parseado
	 */
	protected E mapFromJson(String jsonString) {
		E parsedEntity = null;
		try {
			parsedEntity = new ObjectMapper().readValue(jsonString, getEntityJsonClass());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parsedEntity;
	}

	
	//codigos basicos de REST (no son abstractos pero deben extenderse para el mapeo)
	
	/**
	 * Read de una instancia específica de la entidad.
	 * @param id identificacion de la entidad
	 * @return json de la entidad si se encontro, más el codigo de estado http
	 */
    public ResponseEntity<T> entityById(@PathVariable("id") Long id) {
    	T entity = getEntityDao().getById(id);
    	if( entity != null){
    		return new ResponseEntity<T>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
	 * Read de todas las instancias de la entidad.
	 * @return json de la entidad si se encontro, más el codigo de estado http
     */
    public ResponseEntity<List<T>> entityAll() {
    	List<T>  entity = getEntityDao().selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<T>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<T>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    /**
     * Create de la entidad en base a un json
     * @param jsonString String json
     * @return json de la entidad si se pudo crear, más el codigo de estado http
     */
    public ResponseEntity<T> entityCreate(@RequestBody String jsonString) {
    	T entity = null;
    	E parsedEntity = mapFromJson(jsonString);
    	if(isValidJsonEntityToCreate(parsedEntity)){
    		entity = createEntity(parsedEntity);
    		getEntityDao().persist(entity);
    		return new ResponseEntity<T>(entity, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    	}
    }
    
	/**
	 * Update de la entidad en base a un json
	 * @param id identificacion de la entidad
	 * @param jsonString String json
	 * @return json de la entidad si se pudo modificar, más el codigo de estado http
	 */
    public ResponseEntity<T> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
    	T entity = getEntityDao().getById(id);
    	E parsedEntity = mapFromJson(jsonString);
    	if(entity!= null && isValidJsonEntityToUpdate(parsedEntity)){
    		entity = updateEntity(entity, parsedEntity);
    		getEntityDao().update(entity);
    		return new ResponseEntity<T>(entity,HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    	}
    }
    
	/**
	 * Remove de una instancia específica de la entidad.
	 * @param id identificacion de la entidad
	 * @return codigo de estado http
	 */
    public ResponseEntity<T> entityRemove(@PathVariable("id") Long id) {
    	T entity = getEntityDao().getById(id);
    	if( entity != null ){
    		getEntityDao().remove(entity);
    		return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
    	}
    	else{
    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    	}
    }
    
}
