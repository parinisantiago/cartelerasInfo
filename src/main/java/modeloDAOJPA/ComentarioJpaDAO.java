package modeloDAOJPA;

import org.springframework.stereotype.Repository;

import modelo.Comentario;
import modeloDAO.ComentarioDAO;
@Repository
public class ComentarioJpaDAO extends JpaDao<Comentario> implements ComentarioDAO {

	public ComentarioJpaDAO() {
		super(Comentario.class);
	}

	
}
