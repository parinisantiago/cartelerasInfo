package modeloDAOJPA;

import javax.persistence.Query;

import modelo.Rol;
import modeloDAO.RolDAO;

public class RolJpaDAO extends JpaDao<Rol> implements RolDAO {

	public RolJpaDAO() {
		super(Rol.class);
	}
	
	@Override
	public Rol getById(Long id) {		
		entityTransaction = null;
		Rol resultado = null;
		
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
			consulta.setParameter("id", id);
			resultado = (Rol) consulta.getSingleResult();
			entityTransaction.rollback();
		}
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
			
		}
		return resultado;
	}
	
}
