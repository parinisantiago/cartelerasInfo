package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
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
	public List<Anuncio> selectAll() {
		List<Anuncio> all = super.selectAll();
		if(all!=null){
			for (Anuncio anuncio : all) {
				anuncio.getImagenes().size();
			}
		}
		return all;
	}

	@Override
	public Anuncio getById(Long id) {
		Anuncio anuncio = super.getById(id);
		if(anuncio!=null){
			anuncio.getImagenes().size();
		}
		return anuncio;
	}
	
	@Override
	public List<Anuncio> getByUsuario(Long id) {
		List<Anuncio> resultado = null;
		try {
			Query consulta = this.getEntityManager()
					.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.creador.id = :usuario_id");
			consulta.setParameter("usuario_id", id);
			resultado = (List<Anuncio>) consulta.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		if(resultado!=null){
			for (Anuncio anuncio : resultado) {
				anuncio.getImagenes().size();
			}
		}
		return resultado;
	}

	@Override
	public List<Anuncio> getAllOrderByNewer() {
		List<Anuncio> resultado = new ArrayList<Anuncio>();
		try {
			Query consulta = this.getEntityManager()
					.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e.fecha DESC");
			resultado = (List<Anuncio>) consulta.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		if(resultado!=null){
			for (Anuncio anuncio : resultado) {
				anuncio.getImagenes().size();
			}
		}
		return resultado;
	}

}
