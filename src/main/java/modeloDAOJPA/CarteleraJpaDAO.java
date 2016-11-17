package modeloDAOJPA;

import java.util.List;
import java.util.Set;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Usuario;
import modeloDAO.CarteleraDAO;

public class CarteleraJpaDAO extends JpaDao<Cartelera> implements CarteleraDAO {
	
	public CarteleraJpaDAO() {
		super(Cartelera.class);
	}

}
