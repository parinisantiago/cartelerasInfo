package modeloDAOJPA;


import java.util.ArrayList;
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
	
	
	public JpaDao(Class<T> eClass)
	{
		entityManager = EMF.getEMF().createEntityManager();
		entityClass = eClass;
	}
	
	public boolean persist(T entity)
	{
		entityTransaction = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(entity);
			entityManager.flush();
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			//entityManager.close();
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
			entityManager.flush();
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			//entityManager.close();
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
			entityManager.flush();
			entityTransaction.commit();
		} 
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		finally
		{
			//entityManager.close();
		}
		return true;
	}
	
	@Override
	public List<T> selectAll() {
		entityTransaction = null;
		List<T> resultado = new ArrayList<T>();
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");
			resultado = (List<T>) consulta.getResultList();
			entityTransaction.rollback();
		}
		catch (RuntimeException e)
		{
			if ( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.rollback();
			throw e;
		}
		return resultado;
	}

	@Override
	public T getById(Long id) {		
		entityTransaction = null;
		T resultado = null;
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
			consulta.setParameter("id", id);
			resultado = (T) consulta.getSingleResult();

		return resultado;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
}
