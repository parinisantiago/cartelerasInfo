package modeloDAOJPA;

import org.springframework.stereotype.Repository;

import modelo.Comentario;
import modeloDAO.ComentarioDAO;
@Repository
public class ComentarioJpaDAO extends JpaDao<Comentario> implements ComentarioDAO {

	public ComentarioJpaDAO() {
		super(Comentario.class);
	}

	public boolean remove (Comentario entity)
	{
		entity.setAnuncio(null);
		entity.setCreador(null);
		this.update(entity);
		entity = entityManager.contains(entity) ? entity : entityManager.merge(entity);
		this.getEntityManager().remove(entity);
		return true;
	}
}
