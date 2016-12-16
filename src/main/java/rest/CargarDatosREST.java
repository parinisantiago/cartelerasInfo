package rest;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Comentario;
import modelo.Rol;
import modelo.Usuario;
import modeloDAO.ComentarioDAO;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class CargarDatosREST {
	@Autowired
	private ComentarioDAO dao;
	@Autowired
	private UsuarioDAO daoUser;
	@Autowired
	private RolDAO daoRol;
	
	@RequestMapping(value="/cargardatos/comentarios", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.Publico.class)
    public ResponseEntity<List<Comentario>> entityById() {
		
		Rol rol = null;
		rol = daoRol.getByNombre("TestRol");
		if( rol == null){
			rol = new Rol();
			rol.setNombre("TestRol");
			daoRol.persist(rol);
			rol = daoRol.getByNombre("TestRol");
		}
		
		Usuario  user = null;
		user = daoUser.getByUser("TestUser");
		if(user == null){
			user = new Usuario("TestUser", rol);
			user.setPassword("pasabaporaqui");
			user.setHabilitado(true);
			daoUser.persist(user);
			user = daoUser.getByUser("TestUser");
		}
		
    	List<Comentario> lista = new ArrayList<>();
    	
    	String[] textos = {"Habia una vez truz.", "Y candela? y la moto?", "GOOOOOOOOL","Mañana es navidad!", "Todos pueden comentar", "Cualquiera puede cantar", "Me olvide la billetera", "Ya es de noche", "La banda del lechuga", "Se hizo de dia!"};
    	
    	for (int i = 0; i < 5; i++) {
    		Comentario com = new Comentario();
    		com.setTexto(textos[(int)Math.random()*10]);
    		com.setFecha(new Date());
    		com.setCreador(user);
    		com.setHabilitado(true);
    		lista.add(com);
		}
    	
		for (Comentario comentario : lista) {
			dao.persist(comentario);
		}
		
		return new ResponseEntity<List<Comentario>>(dao.selectAll(), HttpStatus.OK);
    }
	
}
