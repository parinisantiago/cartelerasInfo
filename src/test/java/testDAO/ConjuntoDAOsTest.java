package testDAO;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.Persistence;
import org.junit.Test;

import modelo.*;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.ComentarioDAO;
import modeloDAO.NotificacionDAO;
import modeloDAO.RolDAO;
import modeloDAOJPA.DAOFactory;
import modeloDAOJPA.UsuarioJpaDAO;

public class ConjuntoDAOsTest {
	private Rol rolA;
	private Rol rolB;
	private Comentario comentarioA;
	private Comentario comentarioB;
	private Usuario usuarioA;
	private Usuario usuarioB;
	private Anuncio anuncioA;
	private Anuncio anuncioB;
	private Cartelera carteleraA;
	private Cartelera carteleraB;
	private Notificacion notificacionA;
	private Notificacion notificacionB;
	private RolDAO rolDAO = DAOFactory.getRolDao();
	private UsuarioJpaDAO usuarioDAO = DAOFactory.getUsuarioDao();
	private AnuncioDAO anuncioDAO = DAOFactory.getAnuncioDao();
	private CarteleraDAO carteleraDAO = DAOFactory.getCarteleraDao();
	private ComentarioDAO comentarioDAO = DAOFactory.getComentarioDao();
	private NotificacionDAO notificacionDAO = DAOFactory.getNotificacionDao();
	private List<Rol> roles;
	private List<Usuario> usuarios;
	private List<Comentario> comentarios;
	private List<Cartelera> carteleras;
	private List<Anuncio> anuncios;
	private List<Notificacion> notificaciones;

	
	@Test
	public void todo(){
		Persistence.generateSchema("cartelerasInfo", null);
		//roles
		this.rolA = new Rol("Administrador");
		this.rolB = new Rol("Profesor");
		
		assertTrue(rolDAO.persist(this.rolA));
		assertTrue(rolDAO.persist(this.rolB));
		
		roles = rolDAO.selectAll();
		assertTrue(roles.size() == 2);

		this.rolA = rolDAO.getById(new Long(1));
		
		assertTrue(this.rolA.getNombre().equals("Administrador"));
		
		this.rolA.setNombre("Alumno");
		
		assertTrue(rolDAO.update(this.rolA));
		
		this.rolA = rolDAO.getById(new Long(1));
		assertTrue(this.rolA.getNombre().equals("Alumno"));

		assertTrue(rolDAO.remove(this.rolA));
		roles = rolDAO.selectAll();
		assertTrue(roles.size() == 1);
		
		this.rolA = new Rol("Administrador");
		rolDAO.persist(this.rolA);
		this.rolA = rolDAO.getById(new Long(3));
		this.rolB = rolDAO.getById(new Long(2));
		
		//usuario
		this.usuarioA = new Usuario("Santiago", "Santiago", true, this.rolA);
		this.usuarioB = new Usuario("Agustin", "Agustin", true, this.rolB);
		List<Usuario> usuarios;
		
				
		assertTrue(usuarioDAO.persist(this.usuarioA));
		assertTrue(usuarioDAO.persist(this.usuarioB));
		
		usuarios = usuarioDAO.selectAll();
		assertTrue(usuarios.size() == 2);

		this.usuarioA = usuarioDAO.getById(new Long(4));
		
		assertTrue(this.usuarioA.getUser().equals("Santiago"));
		
		this.usuarioA.setUser("Paula");
		
		assertTrue(usuarioDAO.update(this.usuarioA));
		
		this.usuarioA = usuarioDAO.getById(new Long(4));
		assertTrue(this.usuarioA.getUser().equals("Paula"));
		assertTrue(this.usuarioA.getRol().getNombre().equals("Administrador"));
		assertTrue(this.usuarioB.getRol().getNombre().equals("Profesor"));
		assertTrue(usuarioDAO.remove(this.usuarioA));
		
		usuarios = usuarioDAO.selectAll();
		assertTrue(usuarios.size() == 1);
		this.usuarioA = new Usuario("Santiago", "Santiago", true, this.rolA);
		assertTrue(usuarioDAO.persist(this.usuarioA));
		//cartelera
		this.carteleraA = new Cartelera("taller de java 2016");
		this.carteleraB = new Cartelera("Cartelera Random");
		this.usuarioA = usuarioDAO.getById(new Long(5));
		this.usuarioB = usuarioDAO.getById(new Long(6));
		this.carteleraA.addInteresado(this.usuarioA);
		this.carteleraA.addInteresado(this.usuarioB);
		this.carteleraA.addUsuarioEliminar(this.usuarioA);
		this.carteleraB.addUsuarioEliminar(this.usuarioA);
		this.carteleraB.addUsuarioEliminar(this.usuarioB);
		this.carteleraA.addUsuarioPublicar(this.usuarioA);
		assertTrue(carteleraDAO.persist(this.carteleraA));
		assertTrue(carteleraDAO.persist(this.carteleraB));
		
		this.carteleraA = carteleraDAO.getById(new Long(7));
		
		assertTrue(this.carteleraA.getTitulo().equals("taller de java 2016"));

		this.carteleras = carteleraDAO.selectAll();
		assertTrue(this.carteleras.size() == 2);
		assertTrue(this.carteleraA.getInteresados().size() == 2);
		
		this.carteleraA.getInteresados().remove(this.usuarioB);
		
		assertTrue(carteleraDAO.update(this.carteleraA));

		this.carteleraA = carteleraDAO.getById(new Long(7));
		assertTrue(this.carteleraA.getInteresados().size() == 1);
		
		this.usuarioB = usuarioDAO.getById(new Long(5));
		assertTrue(this.usuarioB.getIntereses().size() == 1);
		

		
		//anuncio
		this.anuncioA = new Anuncio("este es el titulo","el cuerpo",true, this.usuarioA, new Date());
		this.anuncioB = new Anuncio("titulo 2","el cuerpo",true, this.usuarioA,new Date());
		
		assertTrue(anuncioDAO.persist(this.anuncioA));
		assertTrue(anuncioDAO.persist(this.anuncioB));
		
		anuncios = anuncioDAO.selectAll();
		
		assertTrue(anuncios.size() == 2);
		
		this.anuncioA = anuncioDAO.getById(new Long(9));
		this.anuncioA.setCuerpo("vamos a cambiar el texto");
		assertTrue(anuncioDAO.update(this.anuncioA));
		this.anuncioA = anuncioDAO.getById(new Long(9));
		assertTrue(this.anuncioA.getCuerpo().equals("vamos a cambiar el texto"));
		
		this.anuncioA = anuncioDAO.getById(new Long(9));
		assertTrue(anuncioDAO.update(this.anuncioA));
		
		assertTrue(anuncioDAO.remove(this.anuncioA));
		
		anuncios = anuncioDAO.selectAll();
		
		assertTrue(anuncios.size() == 1);
		
		this.anuncioA = anuncioDAO.getById(new Long(10));
		this.carteleraA = carteleraDAO.getById(new Long(8));
		this.anuncioA.setCartelera(this.carteleraA);
		assertTrue(anuncioDAO.update(this.anuncioA));
		
		this.carteleraA = carteleraDAO.getById(new Long(8));
		assertTrue(this.carteleraA.getAnuncios().size() == 1);
		
		this.carteleraA = carteleraDAO.getById(new Long(8));
		
		//comentario
		this.usuarioA = usuarioDAO.getById(new Long(5));
		
		this.comentarioA = new Comentario("esto es un comentario", new Date(), this.usuarioA);
		this.comentarioB = new Comentario("esto es un comentario", new Date(), this.usuarioA);
		
		assertTrue(comentarioDAO.persist(this.comentarioA));
		assertTrue(comentarioDAO.persist(this.comentarioB));
		
		comentarios = comentarioDAO.selectAll();
		
		assertTrue(comentarios.size() == 2);
		
		this.comentarioA = comentarioDAO.getById(new Long(11));
		this.comentarioA.setTexto("vamos a cambiar el texto");
		assertTrue(comentarioDAO.update(this.comentarioA));
		this.comentarioA = comentarioDAO.getById(new Long(11));
		assertTrue(this.comentarioA.getTexto().equals("vamos a cambiar el texto"));
		
		this.comentarioA = comentarioDAO.getById(new Long(11));
		assertTrue(comentarioDAO.update(this.comentarioA));
		
		assertTrue(comentarioDAO.remove(this.comentarioA));
		
		comentarios = comentarioDAO.selectAll();
		
		assertTrue(comentarios.size() == 1);
		
		this.comentarioA = comentarioDAO.getById(new Long(12));
		this.anuncioA = anuncioDAO.getById(new Long(10));
		this.comentarioA.setAnuncio(this.anuncioA);
		this.anuncioA.addComentario(comentarioA);
		anuncioDAO.update(anuncioA);
		assertTrue(comentarioDAO.update(this.comentarioA));
		
		this.anuncioA = anuncioDAO.getById(new Long(10));
		assertTrue(this.anuncioA.getComentarios().size() == 1);
		
		this.carteleraA = carteleraDAO.getById(new Long(8));
		carteleraDAO.remove(this.carteleraA);
		
		//notifiacion
		this.usuarioA = usuarioDAO.getById(new Long(5));
		this.notificacionA = new Notificacion("descripcion A");
		this.notificacionB = new Notificacion("descripcion B");
		
		assertTrue(notificacionDAO.persist(this.notificacionA));
		assertTrue(notificacionDAO.persist(this.notificacionB));
		
		notificaciones = notificacionDAO.selectAll();
		
		assertTrue(notificaciones.size() == 2);
		
		this.notificacionA = notificacionDAO.getById(new Long(13));
		this.notificacionA.setDescripcion("descripcionA2");
		assertTrue(notificacionDAO.update(this.notificacionA));
		this.notificacionA = notificacionDAO.getById(new Long(13));
		assertTrue(this.notificacionA.getDescripcion().equals("descripcionA2"));
		
		assertTrue(notificacionDAO.remove(this.notificacionB));
		
		notificaciones = notificacionDAO.selectAll();
		
		assertTrue(notificaciones.size() == 1);
		
		this.notificacionA = notificacionDAO.getById(new Long(13));
		this.notificacionA.setUsuario(usuarioA);
		this.usuarioA.addNotificacion(notificacionA);
		usuarioDAO.update(usuarioA);
		assertTrue(notificacionDAO.update(this.notificacionA));
		
		this.usuarioA = usuarioDAO.getById(new Long(5));
		assertTrue(this.usuarioA.getNotificaciones().size() == 1);

		
	}
	
	/*
	@Test
	public void testRol(){
		RolJpaDAO rolDAO = DAOFactory.getRolDao();
		this.rolA = new Rol("Administrador");
		this.rolB = new Rol("Profesor");
		List<Rol> roles;
		
		
		
	}
	
	@Test
	public void testUsuario(){
		UsuarioJpaDAO usuarioDAO = DAOFactory.getUsuarioDao();
		RolJpaDAO rolDAO = DAOFactory.getRolDao();
		
	}
	
	@Test
	public void testCartelera(){

		CarteleraJpaDAO carteleraDAO = DAOFactory.getCarteleraDao();
		UsuarioJpaDAO usuarioDAO = DAOFactory.getUsuarioDao();
		this.carteleraA = new Cartelera("taller de java 2016");
		this.carteleraB = new Cartelera("Cartelera Random");
		this.usuarioA = usuarioDAO.getById(new Long(5));
		this.usuarioB = usuarioDAO.getById(new Long(6));
		this.carteleraA.addInteresado(this.usuarioA);
		this.carteleraA.addInteresado(this.usuarioB);
		
		assertTrue(carteleraDAO.persist(this.carteleraA));
		assertTrue(carteleraDAO.persist(this.carteleraB));
		
		this.carteleraA = carteleraDAO.getById(new Long(7));
		
		assertTrue(this.carteleraA.getTitulo().equals("taller de java 2016"));
		
		this.carteleraA.getInteresados().remove(this.usuarioB);
		
		assertTrue(carteleraDAO.update(this.carteleraA));
		
		this.usuarioA.getIntereses().remove(this.carteleraA);
		assertTrue(usuarioDAO.update(this.usuarioA));
	}
	
	@Test
	public void testGeneral() {
		try {
			this.crearBD();

			//crear datos
			
			Date hoy = new Date(); 
			Rol rol = new Rol("docente");

			
			Notificacion notificacion = new Notificacion("sea usted notificado");
			
			Usuario usuario = new Usuario("juan", "1234", true, rol);
			usuario.addNotificacion(notificacion);
			
			Comentario comentario = new Comentario("buen dia", hoy, usuario);
			
			List<Comentario> comentarios = new ArrayList<Comentario>();
			comentarios.add(comentario);
			Anuncio anuncio = new Anuncio("ttps anuncia", "lorem ipsum", true, usuario, hoy, comentarios);
			
			Cartelera cartelera = new Cartelera("java");
			cartelera.addAnuncio(anuncio);
			cartelera.addInteresado(usuario);
			cartelera.addUsuarioEliminar(usuario);
			cartelera.addUsuarioPublicar(usuario);
			cartelera.setHabilitado(true);
			
			//guardar datos
			CarteleraJpaDAO carteleraDAO = new CarteleraJpaDAO();
			boolean b = carteleraDAO.persist(cartelera);
			assertTrue("transaccion guardado", b);
			
			//leer
			Cartelera resultado = null;
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query consulta = entityManager.createQuery("SELECT e FROM " + Cartelera.class + " e WHERE e.titulo = ?");
			consulta.setParameter(1, "java");
			resultado = (Cartelera) consulta.getSingleResult();
			entityTransaction.rollback();
			
			assertEquals("lectura Cartelera", resultado.getTitulo(), "java");
			assertEquals("lectura Anuncio", resultado.getAnuncios().get(0).getFecha(), hoy);
			assertEquals("lectura Comentario", resultado.getAnuncios().get(0).getComentarios().get(0).getFecha(), hoy);
			assertEquals("lectura Usuario", resultado.getAnuncios().get(0).getComentarios().get(0).getCreador().getUser(), "juan");
			assertEquals("lectura Notificacion", resultado.getAnuncios().get(0).getComentarios().get(0).getCreador().getNotificaciones().get(0).getDescripcion(), "sea usted notificado");
			assertEquals("lectura Rol", resultado.getAnuncios().get(0).getComentarios().get(0).getCreador().getRol().getNombre(), "docente");
			
			entityManager.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	private void crearBD() {
		Persistence.generateSchema("cartelerasInfo", null);
		entityManager = Persistence.createEntityManagerFactory("cartelerasInfo").createEntityManager();
	}
*/
}
