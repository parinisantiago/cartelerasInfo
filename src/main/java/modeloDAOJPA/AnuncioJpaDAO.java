package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import modelo.Anuncio;
import modeloDAO.AnuncioDAO;

public class AnuncioJpaDAO extends JpaDao<Anuncio> implements AnuncioDAO {

	public AnuncioJpaDAO() {
		super(Anuncio.class);
	}

	@Override
	public List<Anuncio> getAllOrderByNewer() {
		entityTransaction = null;
		List<Anuncio> resultado = new ArrayList<Anuncio>();
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e.fecha DESC");
			resultado = (List<Anuncio>) consulta.getResultList();
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
