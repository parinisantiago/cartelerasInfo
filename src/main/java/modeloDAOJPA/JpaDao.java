package modeloDAOJPA;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import modeloDAO.Dao;

public abstract class JpaDao<T> implements Dao<T> 
{
	@PersistenceContext
	protected EntityManager entityManager;
	protected EntityTransaction entityTransaction;
	protected Query query;
	protected Class<T> entityClass;
	
	public JpaDao()
	{
		entityManager = EMF.getEMF().createEntityManager();
	}
	
	public boolean persist(T entity)
	{
		entityTransaction = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(entity);
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			entityManager.close();
		}
		return true;
	}
	
	public boolean remove (T entity)
	{
		entityTransaction = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.remove(entity);
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			entityManager.close();
		}
		return true;
	}

	public boolean update (T entity)
	{
		entityTransaction = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(entity);
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			entityManager.close();
		}
		return true;
	}
	
	@Override
	public List<T> selectAll(T entity) {
		Query consulta = EMF.getEMF().createEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");
		List<T> resultado = (List<T>) consulta.getResultList();
		return resultado;
	}
	
}
