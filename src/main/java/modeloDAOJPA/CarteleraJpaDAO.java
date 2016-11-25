package modeloDAOJPA;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Cartelera;
import modeloDAO.CarteleraDAO;
@Repository
public class CarteleraJpaDAO extends JpaDao<Cartelera> implements CarteleraDAO {
	
	public CarteleraJpaDAO() {
		super(Cartelera.class);
	}

	@Override
	public Cartelera getByTitulo(String titulo) {
	
		Cartelera resultado = null;
	
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.titulo = :titulo");
			consulta.setParameter("titulo", titulo);
			resultado = (Cartelera) consulta.getSingleResult();
		
		return resultado;
	}

}
