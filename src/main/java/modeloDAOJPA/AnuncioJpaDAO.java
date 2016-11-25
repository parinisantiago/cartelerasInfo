package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Anuncio;
import modeloDAO.AnuncioDAO;
@Repository
public class AnuncioJpaDAO extends JpaDao<Anuncio> implements AnuncioDAO {

	public AnuncioJpaDAO() {
		super(Anuncio.class);
	}

	@Override
	public List<Anuncio> getAllOrderByNewer() {
		
		List<Anuncio> resultado = new ArrayList<Anuncio>();

		
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e.fecha DESC");
			resultado = (List<Anuncio>) consulta.getResultList();

		return resultado;
	}

}
