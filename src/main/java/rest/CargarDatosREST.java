package rest;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Comentario;
import modelo.Notificacion;
import modelo.Rol;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.ComentarioDAO;
import modeloDAO.Dao;
import modeloDAO.NotificacionDAO;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;

@RestController
public class CargarDatosREST {
	@Autowired
	private ComentarioDAO daoComentario;
	@Autowired
	private UsuarioDAO daoUsuario;
	@Autowired
	private CarteleraDAO daoCartelera;
	@Autowired
	private AnuncioDAO daoAnuncio;
	@Autowired
	private NotificacionDAO daoNotificacion;
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
		user = daoUsuario.getByUser("TestUser");
		if(user == null){
			user = new Usuario("TestUser", rol);
			user.setPassword("pasabaporaqui");
			user.setHabilitado(true);
			daoUsuario.persist(user);
			user = daoUsuario.getByUser("TestUser");
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
			daoComentario.persist(comentario);
		}
		
		return new ResponseEntity<List<Comentario>>(daoComentario.selectAll(), HttpStatus.OK);
    }
		
	@RequestMapping(value="/cargardatos", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.Cartelera.class)
	public ResponseEntity<List<Cartelera>> cargaGenericaDeDatos() {
		try{
			
			long nowMillis = System.currentTimeMillis();
			
			//roles
			Rol rolProfesor = new Rol("Profesor");
			Rol rolEstudiante = new Rol("Estudiante");
			Rol rolEmpresa = new Rol("Empresa");
			
			daoRol.persist(rolProfesor);
			daoRol.persist(rolEstudiante);
			daoRol.persist(rolEmpresa);
			
			//usuarios
			Usuario usuarioEuge = new Usuario("Euge", "piturro", true, rolEstudiante);
			Usuario usuarioAgus = new Usuario("Agus", "agus", true, rolEstudiante);
			Usuario usuarioProfesor = new Usuario("Profe", "profe", true, rolProfesor);
			Usuario usuarioEmpresa = new Usuario("Empresa", "empresa", true, rolEmpresa);
			
			daoUsuario.persist(usuarioAgus);
			daoUsuario.persist(usuarioEuge);
			daoUsuario.persist(usuarioProfesor);
			daoUsuario.persist(usuarioEmpresa);
			
			//carteleras
			Cartelera carteleraPrimero = new Cartelera("Primer Año");
			carteleraPrimero.addUsuarioPublicar(usuarioProfesor);
			carteleraPrimero.addUsuarioEliminar(usuarioProfesor);
			Cartelera carteleraSegundo = new Cartelera("Segundo Año");
			carteleraSegundo.addUsuarioPublicar(usuarioProfesor);
			carteleraSegundo.addUsuarioEliminar(usuarioProfesor);
			Cartelera carteleraLaboral= new Cartelera("Laboral");
			carteleraLaboral.addUsuarioPublicar(usuarioEmpresa);
			carteleraLaboral.addUsuarioEliminar(usuarioEmpresa);
			
			daoCartelera.persist(carteleraPrimero);
			daoCartelera.persist(carteleraSegundo);
			daoCartelera.persist(carteleraLaboral);
			
			//anuncios
			Anuncio anuncioUno = new Anuncio("Horarios ADP", "estos son los horarios de adp de este año ...", true, usuarioProfesor, new Date(nowMillis - (1000 * 60 * 30)));
			carteleraPrimero.addAnuncio(anuncioUno);
			anuncioUno.setCartelera(carteleraPrimero);
			daoAnuncio.persist(anuncioUno);
			for (int i = 0; i < 12; i++) {			
				//matematicas
				Anuncio aux = new Anuncio("Horarios Mate " + i, "estos son los horarios de Mate " + i + "de este semestre ...", true, usuarioProfesor, new Date(nowMillis - (1000 * 60 * 60)) );
				carteleraPrimero.addAnuncio(aux);
				aux.setCartelera(carteleraPrimero);
				daoAnuncio.persist(aux);
			}
			
			Anuncio anuncioDos = new Anuncio("Horarios Base de Datos", "estos son los horarios de Base de Datos de este año ...", true, usuarioProfesor, new Date(nowMillis - (1000 * 60 * 20)) );
			carteleraSegundo.addAnuncio(anuncioDos);
			anuncioDos.setCartelera(carteleraSegundo);
			Anuncio anuncioTres = new Anuncio("Material de Base de Datos", "Se recuerda que deben traer las fotocopias", true, usuarioProfesor, new Date(nowMillis - (1000 * 60 * 10)) );
			carteleraSegundo.addAnuncio(anuncioTres);
			anuncioTres.setCartelera(carteleraSegundo);
			
			Anuncio anuncioCuatro = new Anuncio("Oferta Programador JavaEE", "Se quiere hacer una pagina web de carteleras", true, usuarioEmpresa, new Date(nowMillis - (1000 * 60 * 90)) );
			carteleraLaboral.addAnuncio(anuncioCuatro);
			anuncioCuatro.setCartelera(carteleraLaboral);
			
			daoAnuncio.persist(anuncioDos);
			daoAnuncio.persist(anuncioTres);
			daoAnuncio.persist(anuncioCuatro);
			
			
			//comentarios
			Comentario comentarioUno = new Comentario("Se puede cambiar de horario?", new Date(nowMillis - (1000 * 60)), usuarioAgus);
			anuncioUno.addComentario(comentarioUno);
			comentarioUno.setAnuncio(anuncioUno);
			usuarioAgus.addComentario(comentarioUno);
			Comentario comentarioDos = new Comentario("hola?", new Date(nowMillis), usuarioEuge);
			anuncioUno.addComentario(comentarioDos);
			comentarioDos.setAnuncio(anuncioUno);
			usuarioEuge.addComentario(comentarioDos);
			
			daoComentario.persist(comentarioUno);
			daoComentario.persist(comentarioDos);
			
			//notificaciones
			Notificacion notificacionUno = new Notificacion("has posteado un comentario");
			//notificacionUno.setUsuario(usuarioAgus);
			usuarioAgus.addNotificacion(notificacionUno);
			Notificacion notificacionDos = new Notificacion("has posteado un comentario");
			//notificacionDos.setUsuario(usuarioAgus);
			usuarioAgus.addNotificacion(notificacionDos);
			
			daoNotificacion.persist(notificacionUno);
			daoNotificacion.persist(notificacionDos);
			
			daoUsuario.update(usuarioAgus);
			daoUsuario.update(usuarioEuge);
			
			//guardar
			daoCartelera.update(carteleraPrimero);
			daoCartelera.update(carteleraSegundo);
			daoCartelera.update(carteleraLaboral);
			
			return new ResponseEntity<List<Cartelera>>(daoCartelera.selectAll(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}