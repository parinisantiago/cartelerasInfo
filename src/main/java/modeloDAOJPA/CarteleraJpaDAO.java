package modeloDAOJPA;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;

public class CarteleraJpaDAO extends JpaDao<Cartelera> implements CarteleraDAO {
	
	public CarteleraJpaDAO() {
		super(Cartelera.class);
	}

	@Override
	public Cartelera getByTitulo(String titulo) {
		entityTransaction = null;
		Cartelera resultado = null;
		try
		{
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			Query consulta = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.titulo = :titulo");
			consulta.setParameter("titulo", titulo);
			resultado = (Cartelera) consulta.getSingleResult();
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
