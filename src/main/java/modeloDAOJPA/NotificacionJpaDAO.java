package modeloDAOJPA;

import java.util.List;

import modelo.Notificacion;
import modeloDAO.NotificacionDAO;

public class NotificacionJpaDAO extends JpaDao<Notificacion> implements NotificacionDAO {

	public NotificacionJpaDAO() {
		super(Notificacion.class);
	}


}
