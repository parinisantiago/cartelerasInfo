package modeloDAOJPA;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import modeloDAO.Dao;
@Transactional
public abstract class JpaDao<T> implements Dao<T> 
{
	
	protected EntityManager entityManager;
	protected Query query;
	protected Class<T> entityClass;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em){
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public JpaDao(Class<T> eClass)
	{
		//entityManager = EMF.getEMF().createEntityManager();
		entityClass = eClass;
	}
	
	public boolean persist(T entity)
	{

		this.getEntityManager().persist(entity);
		return true;
	}
	
	public boolean remove (T entity)
	{
		this.getEntityManager().remove(entity);
		return true;
	}

	public boolean update (T entity)
	{
		this.getEntityManager().merge(entity);
		return true;
	}
	
	@Override
	public List<T> selectAll() {
		List<T> resultado = new ArrayList<T>();
			
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");
			resultado = (List<T>) consulta.getResultList();
			return resultado;
	}

	@Override
	public T getById(Long id) {
		T resultado = null;
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
			consulta.setParameter("id", id);
			resultado = (T) consulta.getSingleResult();
	
		return resultado;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
}