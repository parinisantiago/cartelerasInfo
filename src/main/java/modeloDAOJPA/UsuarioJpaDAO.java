package modeloDAOJPA;

import java.util.List;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;

public class UsuarioJpaDAO extends JpaDao<Usuario> implements UsuarioDAO {

	public UsuarioJpaDAO() {
		super(Usuario.class);
		// TODO Auto-generated constructor stub
	}

}
