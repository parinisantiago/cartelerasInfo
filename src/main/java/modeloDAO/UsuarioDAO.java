package modeloDAO;

import java.util.List;

import modelo.Rol;
import modelo.Usuario;

public interface UsuarioDAO extends Dao<Usuario> {
	Usuario getByUser( String user );
	List<Usuario> getAllWithRol( Rol rol);
}
