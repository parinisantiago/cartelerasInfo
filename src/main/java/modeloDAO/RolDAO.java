package modeloDAO;

import modelo.Rol;

public interface RolDAO extends Dao<Rol> {
	Rol getByNombre( String nombre );
}
