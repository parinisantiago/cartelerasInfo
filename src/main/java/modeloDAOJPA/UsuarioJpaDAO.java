package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import modelo.Rol;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

public class UsuarioJpaDAO extends JpaDao<Usuario> implements UsuarioDAO {

	public UsuarioJpaDAO() {
		super(Usuario.class);
	}

	@Override
	public Usuario getByUser(String user) {
		entityTransaction = null;
		Usuario resultado = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.user = :user");
			consulta.setParameter("user", user);
			resultado = (Usuario) consulta.getSingleResult();
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
	public List<Usuario> getAllWithRol(Rol rol) {
		entityTransaction = null;
		List<Usuario> resultado = new ArrayList<Usuario>();
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.rol = :rol");
			consulta.setParameter("rol", rol);
			resultado = (List<Usuario>) consulta.getResultList();
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
	public Usuario getById(Long id) {
		entityTransaction = null;
		Usuario resultado = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
			consulta.setParameter("id", id);
			resultado = (Usuario) consulta.getSingleResult();
			resultado.getNotificaciones().size();
			resultado.getComentarios().size();
			resultado.getIntereses().size();
			resultado.getMisAnuncios().size();
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
