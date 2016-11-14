package modeloDAO;

import modelo.Rol;

public interface RolDAO extends Dao<Rol> {
	public Rol getById(Long id);
}
