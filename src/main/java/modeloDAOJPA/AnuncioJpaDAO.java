package modeloDAOJPA;

import java.util.List;

import modelo.Anuncio;
import modeloDAO.AnuncioDAO;

public class AnuncioJpaDAO extends JpaDao<Anuncio> implements AnuncioDAO {

	public AnuncioJpaDAO() {
		super(Anuncio.class);
	}

}
