package modeloDAOJPA;

import java.util.List;

import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;

public class CarteleraJpaDAO extends JpaDao<Cartelera> implements CarteleraDAO {

	
	@Override
	public List<Usuario> getInteresados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getUsuariosEliminar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getUsuariosPublicar() {
		// TODO Auto-generated method stub
		return null;
	}



}