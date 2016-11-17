package modeloDAO;

import java.util.List;

import modelo.Anuncio;

public interface AnuncioDAO extends Dao<Anuncio> {
	List<Anuncio> getAllOrderByNewer();
}
