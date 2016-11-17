package modeloDAOJPA;

import java.util.List;

import modelo.Comentario;
import modeloDAO.ComentarioDAO;

public class ComentarioJpaDAO extends JpaDao<Comentario> implements ComentarioDAO {

	public ComentarioJpaDAO() {
		super(Comentario.class);
		// TODO Auto-generated constructor stub
	}

	
}
