package modeloDAOJPA;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cartelerasInfo");
	
	public static final EntityManagerFactory getEMF(){
		return emf;
	}
	
	
}
