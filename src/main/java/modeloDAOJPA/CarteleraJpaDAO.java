package modeloDAOJPA;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Anuncio;
import modelo.Cartelera;
import modeloDAO.CarteleraDAO;
@Repository
public class CarteleraJpaDAO extends JpaDao<Cartelera> implements CarteleraDAO {
	
	public CarteleraJpaDAO() {
		super(Cartelera.class);
	}

	@Override
	public List<Cartelera> selectAll() {
		List<Cartelera> all = super.selectAll();
		if(all != null){
			for (Cartelera cartelera : all) {
				for (Anuncio anuncio : cartelera.getAnuncios()) {
					anuncio.getImagenes().size();
				}
			}
		}
		return all;
	}

	@Override
	public Cartelera getById(Long id) {
		Cartelera cartelera = super.getById(id);
		if(cartelera != null){
			for (Anuncio anuncio : cartelera.getAnuncios()) {
				anuncio.getImagenes().size();
			}
		}
		return cartelera;
	}

	@Override
	public Cartelera getByTitulo(String titulo) {

		Cartelera resultado = null;
		try {
			Query consulta = this.getEntityManager()
					.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.titulo = :titulo");
			consulta.setParameter("titulo", titulo);
			resultado = (Cartelera) consulta.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return resultado;
	}

}
