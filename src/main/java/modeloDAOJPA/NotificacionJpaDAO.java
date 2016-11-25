package modeloDAOJPA;

import java.util.List;

import org.springframework.stereotype.Repository;

import modelo.Notificacion;
import modeloDAO.NotificacionDAO;
@Repository
public class NotificacionJpaDAO extends JpaDao<Notificacion> implements NotificacionDAO {

	public NotificacionJpaDAO() {
		super(Notificacion.class);
	}


}
