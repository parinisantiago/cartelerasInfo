package rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Comentario;
import modelo.Usuario;
import modeloDAO.ComentarioDAO;

@RestController
public class ComentarioREST {
	@Autowired
	private ComentarioDAO dao;
	
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityById(@PathVariable("id") Long id) {
    	Comentario entity = dao.getById(id);
    	if( entity != null){
    		return new ResponseEntity<Comentario>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<List<Comentario>> entityAll() {
    	List<Comentario>  entity = dao.selectAll();
    	if( entity != null){
    		return new ResponseEntity<List<Comentario>>(entity, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<List<Comentario>>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityCreate(@RequestBody String checkJson) {
    	System.out.println("JSONREQ: "+checkJson);
		//EntityCheck check = new EntityCheck();
    	Comentario check = null;
		try {
			check = new ObjectMapper().readValue(checkJson, Comentario.class);
			System.out.println(check);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	if( check != null ){
    		dao.persist(check);
    		return new ResponseEntity<Comentario>(check, HttpStatus.CREATED);
    	}
    	else{
    		return new ResponseEntity<Comentario>(check, HttpStatus.BAD_REQUEST);
    	}
    }
    
    private static class EntityCheck{
    	private Long id;
    	private String password;
    	
    	public EntityCheck() {
		}
    	
		public EntityCheck(Long id, String password) {
			super();
			this.id = id;
			this.password = password;
		}

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "AlumnoCheck {'id':"+id.toString()+", 'password':"+password+"}";
		}
    	
    }
    
    @RequestMapping(value="/comentario/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Comentario> entityRemove(@PathVariable("id") Long id) {
    	Comentario entity = dao.getById(id);
    	if( entity != null ){
    		dao.remove(entity);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value="/comentario", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<Comentario> entityUpdate(@RequestBody String checkJson) {
    	System.out.println("JSONREQ: "+checkJson);
		//EntityCheck check = new EntityCheck();
    	Comentario check = null;
		try {
			check = new ObjectMapper().readValue(checkJson, Comentario.class);
			System.out.println(check);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	if( check != null ){
    		dao.update(check);
    		return new ResponseEntity<Comentario>(check, HttpStatus.OK);
    	}
    	else{
    		return new ResponseEntity<Comentario>(check, HttpStatus.BAD_REQUEST);
    	}
    }
    
    
}
