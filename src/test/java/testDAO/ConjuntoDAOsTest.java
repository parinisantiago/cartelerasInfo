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

public class ConjuntoDAOsTest {
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	@Test
	public void testGeneral() {
		try {
			this.crearBD();

			//crear datos
			
			Date hoy = new Date(); 
			Rol rol = new Rol("docente");
			List<Rol> roles = new ArrayList<Rol>();
			roles.add(rol);
			
			Notificacion notificacion = new Notificacion("sea usted notificado");
			
			Usuario usuario = new Usuario("juan", "1234", true, roles);
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
			assertEquals("lectura Rol", resultado.getAnuncios().get(0).getComentarios().get(0).getCreador().getRoles().get(0).getNombre(), "docente");
			
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

}
