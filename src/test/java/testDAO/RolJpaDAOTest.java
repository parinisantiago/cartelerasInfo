package testDAO;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.Rol;
import modeloDAOJPA.RolJpaDAO;

public class RolJpaDAOTest extends JpaDAOTest {
	private RolJpaDAO dao = new RolJpaDAO();
	private Rol entity = null;
			
	@Before
	public void setUp() {
		//al setear la clase, borrara la tabla cada vez que se ejecute un metodo test
		clazz = dao.getClass();
		if( entity == null ){
			entity = new Rol("PERRO");
		}
	}

	@Test
	public void testPersist() {
		assertTrue(dao.persist(entity));
	}

	@Test
	public void testUpdate() {
		dao.persist(entity);
		assertTrue(dao.update(entity));
	}
	
	@Test
	public void testRemove() {
		dao.persist(entity);
		assertTrue(dao.remove(entity));
	}



	@Test
	public void testSelectAll() {
		Rol entity1 = entity;
		Rol entity2 = new Rol("test2");
		Rol entity3 = new Rol("test3");
		
		dao.persist(entity3);
		dao.persist(entity1);
		dao.persist(entity2);
		List<Rol> result = dao.selectAll();
		
		assertTrue(result.size() == 3);
		
	}
	
	@Test
	public void testGetById() {
		Rol entity1 = entity;
		dao.persist(entity1);
		
		Rol entity2 = new Rol("test2");
		dao.persist(entity2);
		
		Rol result = dao.getById((long) 1);
		
		assertTrue(result.getNombre().equals("PERRO"));
	}

}
