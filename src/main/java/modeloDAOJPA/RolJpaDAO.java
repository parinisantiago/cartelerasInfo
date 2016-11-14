package modeloDAOJPA;

import modelo.Rol;
import modeloDAO.RolDAO;

public class RolJpaDAO extends JpaDao<Rol> implements RolDAO {

	public RolJpaDAO() {
		super(Rol.class);
	}
	
}
