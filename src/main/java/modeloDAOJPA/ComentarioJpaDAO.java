package modeloDAOJPA;

import modelo.Comentario;
import modeloDAO.ComentarioDAO;

public class ComentarioJpaDAO extends JpaDao<Comentario> implements ComentarioDAO {

	public ComentarioJpaDAO() {
		super(Comentario.class);
	}

	
}
