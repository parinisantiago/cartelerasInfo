package modeloDAO;

import modelo.Cartelera;

public interface CarteleraDAO extends Dao<Cartelera> {
	Cartelera getByTitulo( String titulo );
}
