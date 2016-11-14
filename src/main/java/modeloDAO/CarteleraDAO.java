package modeloDAO;

import java.util.List;

import modelo.Cartelera;
import modelo.Usuario;

public interface CarteleraDAO extends Dao<Cartelera> {
	List<Usuario> getInteresados();
	List<Usuario> getUsuariosEliminar();
	List<Usuario> getUsuariosPublicar();
}
