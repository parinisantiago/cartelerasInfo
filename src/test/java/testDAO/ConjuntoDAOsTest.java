package testDAO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


import org.junit.Test;

import modelo.*;
import modeloDAOJPA.CarteleraJpaDAO;
import modeloDAOJPA.DAOFactory;
import modeloDAOJPA.RolJpaDAO;
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
	
	@Test
	public void testRol(){
		RolJpaDAO rolDAO = DAOFactory.getRolDao();
		this.rolA = new Rol("Administrador");
		this.rolB = new Rol("Profesor");
		List<Rol> roles;
		
		
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
	}
	
	@Test
	public void testUsuario(){
		UsuarioJpaDAO usuarioDAO = DAOFactory.getUsuarioDao();
		RolJpaDAO rolDAO = DAOFactory.getRolDao();
		this.rolA = new Rol("Administrador");
		rolDAO.persist(this.rolA);
		this.rolA = rolDAO.getById(new Long(3));
		this.rolB = rolDAO.getById(new Long(2));
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
	}
	
	
	/*
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
