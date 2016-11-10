package modeloDAOJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modelo.Rol;

public class RolDAOJPA  {
	
	public void nose(){
		
		try {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "cartelerasInfo" );
		EntityManager entitymanager = emfactory.createEntityManager();
		
		entitymanager.getTransaction().begin();

		Rol rol = new Rol();
		rol.setNombre("lelele");

		entitymanager.persist( rol );
		entitymanager.getTransaction().commit();
		
		entitymanager.close( );
		emfactory.close( );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
