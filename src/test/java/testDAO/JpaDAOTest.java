package testDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;


public abstract class JpaDAOTest {
	protected boolean dbCreada = false;
	protected Class<?> clazz = null;
	
	@Before
	public void createDB() throws Exception {
		if(!dbCreada){
			Persistence.generateSchema("cartelerasInfo", null);
			dbCreada = true;
		}
	}
	
	@Before
	public void clearTable(){
		if( clazz != null ){
			EntityManager entityManager = Persistence.createEntityManagerFactory("cartelerasInfo").createEntityManager();
			EntityTransaction entityTransaction = null;
			try {
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				Query q = entityManager.createQuery("DELETE FROM " + clazz.getSimpleName());
				q.executeUpdate();
				entityTransaction.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
